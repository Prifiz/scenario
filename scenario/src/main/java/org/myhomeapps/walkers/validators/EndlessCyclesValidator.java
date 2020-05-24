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

public class EndlessCyclesValidator<V extends MenuFrame, E extends DefaultEdge>
        extends PropertiesBasedGraphValidator<V, E> {

    public EndlessCyclesValidator(Graph<V, E> graph, PropertiesParser propertiesParser) {
        super(propertiesParser, graph);
    }

    private List<Set<V>> findCycles(Graph<V, E> graph) {
        StrongConnectivityAlgorithm<V, E> inspector =
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

    private V findExitFrame(Graph<V, E> graph) {
        return graph.vertexSet().stream()
                .filter(frame -> propertiesParser.parseProperties(frame.getProperties()).isExit())
                .findAny()
                .orElseThrow(() -> new IllegalStateException("Couldn't find exit frame"));
    }

    private List<V> findFramesWithoutSimplePathsToExit(Graph<V, E> graph, V exitFrame) {
        List<V> result = new ArrayList<>();
        AllDirectedPaths<V, E> paths = new AllDirectedPaths<>(graph);
        graph.vertexSet().forEach(frame -> {
            List<GraphPath<V, E>> routines =
                    paths.getAllPaths(frame, exitFrame, true, null);
            if(routines.isEmpty()) {
                System.out.println("No path to exit from frame: " + frame.getName());
                result.add(frame);
            }
        });
        return result;
    }

    private List<V> findCyclesFramesWithoutPathToExit(Graph<V, E> graph, V exitFrame) {
        List<Set<V>> cycles = findCycles(graph);
        List<V> result = new ArrayList<>();
        AllDirectedPaths<V, E> paths = new AllDirectedPaths<>(graph);

        cycles.forEach(cycle -> cycle.forEach(frame -> {
            List<GraphPath<V, E>> pathsToExit =
                    paths.getAllPaths(frame, exitFrame, true, null);
            if(pathsToExit.isEmpty()) {
                System.out.println("No path to exit from cycle frame: " + frame.getName());
                result.add(frame);
            }
        }));
        return result;
    }

    @Override
    protected Collection<String> findOccurrences() {
        V exitFrame = findExitFrame(graph);
        List<V> simplyLockedFrames = findFramesWithoutSimplePathsToExit(graph, exitFrame);
        List<V> lockedFramesInCycles = findCyclesFramesWithoutPathToExit(graph, exitFrame);

        Set<V> lockedFrames = Stream.concat(
                simplyLockedFrames.stream(),
                lockedFramesInCycles.stream())
                .collect(Collectors.toSet());

        return lockedFrames.stream()
                .filter(frame -> !propertiesParser.parseProperties(frame.getProperties()).isExit())
                .map(V::getName)
                .collect(Collectors.toList());
    }

    @Override
    protected String getDisplayName() {
        return "No Exit Detected";
    }
}
