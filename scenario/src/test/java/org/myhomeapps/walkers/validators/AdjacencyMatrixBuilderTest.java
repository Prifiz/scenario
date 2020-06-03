package org.myhomeapps.walkers.validators;

import mockit.Tested;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Assert;
import org.junit.Test;
import org.myhomeapps.menuentities.MenuFrame;

public class AdjacencyMatrixBuilderTest {

    @Tested
    AdjacencyMatrixBuilder<MenuFrame, DefaultEdge> adjacencyMatrixBuilder;

    @Test
    public void buildAdjacencyMatrixTest() {
        Graph<MenuFrame, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        MenuFrame start = new MenuFrame();
        start.setName("start");
        MenuFrame end = new MenuFrame();
        end.setName("end");
        graph.addVertex(start);
        graph.addVertex(end);
        graph.addEdge(start, end);
        AdjacencyMatrix adjacencyMatrix = adjacencyMatrixBuilder.buildAdjacencyMatrix(graph);
        AdjacencyMatrix expected = new AdjacencyMatrix(new int[][]{{0, 1}, {0, 0}});
        Assert.assertEquals(expected, adjacencyMatrix);
    }
}
