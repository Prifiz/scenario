package org.prifizapps.walkers.validators;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.prifizapps.menuentities.MenuFrame;
import org.prifizapps.menuentities.properties.PropertiesParser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Checks if menu graph has vertices with no outgoing edges.
 * The algorithm is based on building the adjacency matrix and searching zero lines.
 * If vertex with no outgoing edges is marked as <b>exit</b> menu,
 * it is not supposed to be a violation.
 */
public class DeadEndsValidator<V extends MenuFrame, E extends DefaultEdge> extends PropertiesBasedGraphValidator<V, E> {


    public DeadEndsValidator(Graph<V, E> graph, PropertiesParser propertiesParser) {
        super(propertiesParser, graph);
    }

    /**
     * Finds names of menus with no outgoing edges.
     * @return the {@link List} of menu names with no outgoing edges.
     */
    @Override
    protected List<String> findOccurrences() {
        return getDeadEndFrames().stream()
                .filter(frame -> !propertiesParser.parseProperties(frame.getProperties()).isExit())
                .map(MenuFrame::getName)
                .collect(Collectors.toList());
    }

    List<V> getDeadEndFrames() {
        List<V> vertices = new ArrayList<>(graph.vertexSet());
        AdjacencyMatrix adjacencyMatrix = new AdjacencyMatrixBuilder<V, E>().buildAdjacencyMatrix(graph);
        return adjacencyMatrix.getZerosLineIndices().stream()
                .map(vertices::get)
                .collect(Collectors.toList());
    }

    @Override
    protected String getDisplayName() {
        return "Dead Ends found in menu frames";
    }

}
