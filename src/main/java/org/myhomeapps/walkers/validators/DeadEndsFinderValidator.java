package org.myhomeapps.walkers.validators;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.myhomeapps.menuentities.MenuFrame;
import org.myhomeapps.walkers.GraphIssue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DeadEndsFinderValidator implements GraphValidator<MenuFrame, DefaultEdge> {

    @Override
    public Collection<GraphIssue> validate(Graph<MenuFrame, DefaultEdge> graph) {

        //adjacencyOutput(graph);

        return Collections.emptyList();

    }


    void adjacencyOutput(Graph<MenuFrame, DefaultEdge> graph) {
        try {
            int[][] adjacencyMatrix = buildAdjacencyMatrix(graph);
            for (int i = 0; i < adjacencyMatrix.length; i++) {
                for (int j = 0; j < adjacencyMatrix.length; j++) {
                    System.out.print(adjacencyMatrix[i][j] + "\t");
                }
                System.out.println();
            }

        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    int[][] buildAdjacencyMatrix(Graph<MenuFrame, DefaultEdge> graph) throws IOException {
        List<MenuFrame> vertices = new ArrayList<>(graph.vertexSet());
        int dimension = vertices.size();
        int[][] result = new int[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            MenuFrame vi = vertices.get(i);
            System.out.println("Vi = " + vi.getName() + "\t i = " + i);
            for (int j = 0; j < dimension; j++) {
                MenuFrame vj = vertices.get(j);
                System.out.println("Vj = " + vj.getName() + "\t j = " + j);

                if (vi.equals(vj)) {
                    System.out.println("Diagonal element");
                    result[i][j] = 0;
                } else {
                    if (Graphs.vertexHasSuccessors(graph, vi)) {
                        if (Graphs.vertexHasPredecessors(graph, vj)) {
                            if (Graphs.successorListOf(graph, vi).contains(vj) &&
                                    Graphs.predecessorListOf(graph, vj).contains(vi)) {
                                result[i][j] = 1;
                            } else {
                                result[i][j] = 0;
                            }
                        } else {
                            result[i][j] = 0;
                        }
                    } else {
                        result[i][j] = 0;
                    }
                }
                System.out.println(String.format("Result[%s][%s] = %s", i, j, result[i][j]));
            }

        }

        for (int idx : getZerosLineIndices(result)) {
            System.out.println("Zero line idx: " + idx);
            System.out.println("Suspicious vertex: " + vertices.get(idx).getName());
        }

        return result;
    }

    List<Integer> getZerosLineIndices(int[][] adjacencyMatrix) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (isZeroLine(adjacencyMatrix[i])) {
                result.add(i);
            }
        }
        return result;
    }

    boolean isZeroLine(int[] line) {
        for (int value : line) {
            if (value == 1) {
                return false;
            }
        }
        return true;
    }
}
