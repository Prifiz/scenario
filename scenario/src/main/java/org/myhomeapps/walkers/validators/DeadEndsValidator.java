package org.myhomeapps.walkers.validators;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.myhomeapps.menuentities.MenuFrame;
import org.myhomeapps.menuentities.properties.PropertiesParser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class DeadEndsValidator<V extends MenuFrame, E extends DefaultEdge> extends PropertiesBasedGraphValidator<V, E> {

    public DeadEndsValidator(Graph<V, E> graph, PropertiesParser propertiesParser) {
        super(propertiesParser, graph);
    }

    @Override
    protected Collection<String> findOccurrences() {
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
