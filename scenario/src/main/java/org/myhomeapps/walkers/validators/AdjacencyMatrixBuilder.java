package org.myhomeapps.walkers.validators;

import lombok.NoArgsConstructor;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.myhomeapps.menuentities.MenuFrame;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class AdjacencyMatrixBuilder<V extends MenuFrame, E extends DefaultEdge> {

    public AdjacencyMatrix buildAdjacencyMatrix(Graph<V, E> graph) {
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
        return new AdjacencyMatrix(result);
    }
}
