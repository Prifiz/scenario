package org.myhomeapps.walkers.validators;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.connectivity.KosarajuStrongConnectivityInspector;
import org.jgrapht.alg.interfaces.StrongConnectivityAlgorithm;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.graph.DefaultEdge;
import org.myhomeapps.menuentities.MacrosParser;
import org.myhomeapps.menuentities.MenuFrame;
import org.myhomeapps.walkers.GraphIssue;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EndlessCyclesValidator extends PropertiesBasedGraphValidator {

    public EndlessCyclesValidator(MacrosParser macrosParser) {
        super(macrosParser);
    }

    @Override
    public Collection<GraphIssue> validate(Graph<MenuFrame, DefaultEdge> graph) {
        MenuFrame exitFrame = findExitFrame(graph);
        List<MenuFrame> simplyLockedFrames = findFramesWithoutSimplePathsToExit(graph, exitFrame);
        List<MenuFrame> lockedFramesInCycles = findCyclesFramesWithoutPathToExit(graph, exitFrame);

        Set<MenuFrame> lockedFrames = Stream.concat(
                simplyLockedFrames.stream(),
                lockedFramesInCycles.stream())
                .collect(Collectors.toSet());

        List<String> occurrences = lockedFrames.stream()
                .filter(frame -> !macrosParser.parseMacros(frame.getProperties()).isExit())
                .map(MenuFrame::getName)
                .collect(Collectors.toList());

        if(occurrences.isEmpty()) {
            return Collections.emptyList();
        } else {
            return Collections.singletonList(new GraphIssue("No Exit Detected", occurrences));
        }
    }

    private List<Set<MenuFrame>> findCycles(Graph<MenuFrame, DefaultEdge> graph) {
        StrongConnectivityAlgorithm<MenuFrame, DefaultEdge> inspector =
                new KosarajuStrongConnectivityInspector<>(graph);
        List<Set<MenuFrame>> cyclesFixed = new ArrayList<>();

        for (Set<MenuFrame> component : inspector.stronglyConnectedSets()) {
            MenuFrame frame = component.iterator().next();
            if (component.size() == 1 && graph.containsEdge(frame, frame)) {
                Set<MenuFrame> singleFrameSet = new HashSet<>();
                singleFrameSet.add(frame);
                cyclesFixed.add(singleFrameSet);
            }
            if (component.size() > 1) {
                cyclesFixed.add(component);
            }
        }
        return cyclesFixed;
    }

    private MenuFrame findExitFrame(Graph<MenuFrame, DefaultEdge> graph) {
        for(MenuFrame frame : graph.vertexSet()) {
            if(macrosParser.parseMacros(frame.getProperties()).isExit()) {
                return frame;
            }
        }
        throw new IllegalStateException("Couldn't find exit frame");
    }

    private List<MenuFrame> findFramesWithoutSimplePathsToExit(Graph<MenuFrame, DefaultEdge> graph, MenuFrame exitFrame) {
        List<MenuFrame> result = new ArrayList<>();
        AllDirectedPaths<MenuFrame, DefaultEdge> paths = new AllDirectedPaths<>(graph);
        graph.vertexSet().forEach(frame -> {
            List<GraphPath<MenuFrame, DefaultEdge>> routines = paths.getAllPaths(frame, exitFrame, true, null);
            if(routines.isEmpty()) {
                System.out.println("No path to exit from frame: " + frame.getName());
                result.add(frame);
            }
        });
        return result;
    }

    private List<MenuFrame> findCyclesFramesWithoutPathToExit(Graph<MenuFrame, DefaultEdge> graph, MenuFrame exitFrame) {
        List<Set<MenuFrame>> cycles = findCycles(graph);
        List<MenuFrame> result = new ArrayList<>();
        AllDirectedPaths<MenuFrame, DefaultEdge> paths = new AllDirectedPaths<>(graph);

        cycles.forEach(cycle -> {
            cycle.forEach(frame -> {
                List<GraphPath<MenuFrame, DefaultEdge>> pathsToExit =
                        paths.getAllPaths(frame, exitFrame, true, null);
                if(pathsToExit.isEmpty()) {
                    System.out.println("No path to exit from cycle frame: " + frame.getName());
                    result.add(frame);
                }
            });
        });
        return result;
    }

}
