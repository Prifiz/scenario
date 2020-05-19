package org.myhomeapps.walkers.validators;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.connectivity.KosarajuStrongConnectivityInspector;
import org.jgrapht.alg.interfaces.StrongConnectivityAlgorithm;
import org.jgrapht.alg.shortestpath.AllDirectedPaths;
import org.jgrapht.graph.DefaultEdge;
import org.myhomeapps.menuentities.properties.PropertiesParser;
import org.myhomeapps.menuentities.MenuFrame;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EndlessCyclesValidator<V extends MenuFrame> extends PropertiesBasedGraphValidator<V> {

    public EndlessCyclesValidator(PropertiesParser propertiesParser) {
        super(propertiesParser);
    }

    @Override
    public Collection<GraphIssue> validate(Graph<V, DefaultEdge> graph) {
        V exitFrame = findExitFrame(graph);
        List<V> simplyLockedFrames = findFramesWithoutSimplePathsToExit(graph, exitFrame);
        List<V> lockedFramesInCycles = findCyclesFramesWithoutPathToExit(graph, exitFrame);

        Set<V> lockedFrames = Stream.concat(
                simplyLockedFrames.stream(),
                lockedFramesInCycles.stream())
                .collect(Collectors.toSet());

        List<String> occurrences = lockedFrames.stream()
                .filter(frame -> !propertiesParser.parseProperties(frame.getProperties()).isExit())
                .map(V::getName)
                .collect(Collectors.toList());

        if(occurrences.isEmpty()) {
            return Collections.emptyList();
        } else {
            return Collections.singletonList(new GraphIssue("No Exit Detected", occurrences));
        }
    }

    private List<Set<V>> findCycles(Graph<V, DefaultEdge> graph) {
        StrongConnectivityAlgorithm<V, DefaultEdge> inspector =
                new KosarajuStrongConnectivityInspector<>(graph);
        List<Set<V>> cyclesFixed = new ArrayList<>();

        for (Set<V> component : inspector.stronglyConnectedSets()) {
            V frame = component.iterator().next();
            if (component.size() == 1 && graph.containsEdge(frame, frame)) {
                Set<V> singleFrameSet = new HashSet<>();
                singleFrameSet.add(frame);
                cyclesFixed.add(singleFrameSet);
            }
            if (component.size() > 1) {
                cyclesFixed.add(component);
            }
        }
        return cyclesFixed;
    }

    private V findExitFrame(Graph<V, DefaultEdge> graph) {
        return graph.vertexSet().stream()
                .filter(frame -> propertiesParser.parseProperties(frame.getProperties()).isExit())
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Couldn't find exit frame"));
    }

    private List<V> findFramesWithoutSimplePathsToExit(Graph<V, DefaultEdge> graph, V exitFrame) {
        List<V> result = new ArrayList<>();
        AllDirectedPaths<V, DefaultEdge> paths = new AllDirectedPaths<>(graph);
        graph.vertexSet().forEach(frame -> {
            List<GraphPath<V, DefaultEdge>> routines =
                    paths.getAllPaths(frame, exitFrame, true, null);
            if(routines.isEmpty()) {
                System.out.println("No path to exit from frame: " + frame.getName());
                result.add(frame);
            }
        });
        return result;
    }

    private List<V> findCyclesFramesWithoutPathToExit(Graph<V, DefaultEdge> graph, V exitFrame) {
        List<Set<V>> cycles = findCycles(graph);
        List<V> result = new ArrayList<>();
        AllDirectedPaths<V, DefaultEdge> paths = new AllDirectedPaths<>(graph);

        cycles.forEach(cycle -> cycle.forEach(frame -> {
            List<GraphPath<V, DefaultEdge>> pathsToExit =
                    paths.getAllPaths(frame, exitFrame, true, null);
            if(pathsToExit.isEmpty()) {
                System.out.println("No path to exit from cycle frame: " + frame.getName());
                result.add(frame);
            }
        }));
        return result;
    }

}
