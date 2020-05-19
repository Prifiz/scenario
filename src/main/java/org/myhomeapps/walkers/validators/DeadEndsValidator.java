package org.myhomeapps.walkers.validators;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.myhomeapps.menuentities.properties.PropertiesParser;
import org.myhomeapps.menuentities.MenuFrame;

import java.util.*;
import java.util.stream.Collectors;

public class DeadEndsValidator<V extends MenuFrame, E extends DefaultEdge> extends PropertiesBasedGraphValidator<V, E> {

    private final int[][] adjacencyMatrix;

    public DeadEndsValidator(Graph<V, E> graph, PropertiesParser propertiesParser) {
        super(propertiesParser, graph);
        this.adjacencyMatrix = buildAdjacencyMatrix(graph);
    }

    @Override
    protected Collection<String> findOccurrences() {
        return getDeadEndFrames(graph).stream()
                .filter(frame -> !propertiesParser.parseProperties(frame.getProperties()).isExit())
                .map(MenuFrame::getName)
                .collect(Collectors.toList());
    }

    @Override
    protected String getDisplayName() {
        return "Dead Ends found in menu frames";
    }

    private void printAdjacencyMatrix() {
        for (int[] matrix : adjacencyMatrix) {
            for (int j = 0; j < adjacencyMatrix.length; j++) {
                System.out.print(matrix[j] + "\t");
            }
            System.out.println();
        }
    }

    private int[][] buildAdjacencyMatrix(Graph<V, E> graph) {
        List<V> vertices = new ArrayList<>(graph.vertexSet());
        int dimension = vertices.size();
        int[][] result = new int[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            V vi = vertices.get(i);
            for (int j = 0; j < dimension; j++) {
                V vj = vertices.get(j);
                if (vi.equals(vj)) {
                    result[i][j] = 0;
                } else {
                    if (Graphs.successorListOf(graph, vi).contains(vj) &&
                            Graphs.predecessorListOf(graph, vj).contains(vi)) {
                        result[i][j] = 1;
                    } else {
                        result[i][j] = 0;
                    }
                }
            }
        }
        return result;
    }

    private List<V> getDeadEndFrames(Graph<V, E> graph) {
        List<V> vertices = new ArrayList<>(graph.vertexSet());
        return getZerosLineIndices().stream()
                .map(vertices::get)
                .collect(Collectors.toList());
    }

    private List<Integer> getZerosLineIndices() {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (isZeroLine(adjacencyMatrix[i])) {
                result.add(i);
            }
        }
        return result;
    }

    private boolean isZeroLine(int[] line) {
        return Arrays.stream(line)
                .allMatch(current -> current == 0);
    }
}
