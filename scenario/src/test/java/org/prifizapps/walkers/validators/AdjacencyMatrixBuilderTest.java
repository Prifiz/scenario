package org.prifizapps.walkers.validators;

import mockit.Tested;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Assert;
import org.junit.Test;
import org.prifizapps.menuentities.MenuFrame;

public class AdjacencyMatrixBuilderTest {

    @Tested
    AdjacencyMatrixBuilder<MenuFrame, DefaultEdge> adjacencyMatrixBuilder;
    @Tested
    MenuFrame start;
    @Tested
    MenuFrame end;

    @Test
    public void buildAdjacencyMatrixWithNullLinesTest() {
        Graph<MenuFrame, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        start.setName("start");
        end.setName("end");
        graph.addVertex(start);
        graph.addVertex(end);
        graph.addEdge(start, end);
        AdjacencyMatrix adjacencyMatrix = adjacencyMatrixBuilder.buildAdjacencyMatrix(graph);
        AdjacencyMatrix expected = new AdjacencyMatrix(new int[][]{{0, 1}, {0, 0}});
        Assert.assertEquals(expected, adjacencyMatrix);
    }

    @Test
    public void buildAdjacencyMatrixWithoutNullLinesTest() {
        Graph<MenuFrame, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        start.setName("start");
        end.setName("end");
        graph.addVertex(start);
        graph.addVertex(end);
        graph.addEdge(start, end);
        graph.addEdge(end, start);
        AdjacencyMatrix adjacencyMatrix = adjacencyMatrixBuilder.buildAdjacencyMatrix(graph);
        AdjacencyMatrix expected = new AdjacencyMatrix(new int[][]{{0, 1}, {1, 0}});
        Assert.assertEquals(expected, adjacencyMatrix);
    }
}
