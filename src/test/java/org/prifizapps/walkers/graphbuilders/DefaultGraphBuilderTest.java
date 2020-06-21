package org.prifizapps.walkers.graphbuilders;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Assert;
import org.junit.Test;
import org.prifizapps.menuentities.MenuFrame;
import org.prifizapps.menuentities.MenuItem;
import org.prifizapps.menuentities.MenuSystem;
import org.prifizapps.menuentities.properties.DefaultPropertiesParser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DefaultGraphBuilderTest {

    @Test
    public void buildFramesGraphMenuEdgesTest() {
        MenuFrame start = new MenuFrame("start")
                .gotoMenuWithName("end");
        MenuFrame end = new MenuFrame("end");
        MenuSystem menuSystem = new MenuSystem(new ArrayList<>(Arrays.asList(start, end)));
        GraphBuilder graphBuilder = new DefaultGraphBuilder(menuSystem);
        Graph<MenuFrame, DefaultEdge> graph = graphBuilder.buildFramesGraph(new DefaultPropertiesParser());
        Assert.assertTrue(graph.vertexSet().contains(start));
        Assert.assertTrue(graph.vertexSet().contains(end));
        Assert.assertEquals(Collections.singletonList(end), Graphs.successorListOf(graph, start));
        Assert.assertEquals(Collections.singletonList(start), Graphs.predecessorListOf(graph, end));
    }

    @Test
    public void buildFramesGraphItemEdgesTest() {
        MenuItem startOptionOne = new MenuItem()
                .withName("option1")
                .goTo("end");
        MenuItem startOptionTwo = new MenuItem()
                .withName("option2");
        MenuFrame start = new MenuFrame("start");
        start.addItem(startOptionOne);
        start.addItem(startOptionTwo);
        MenuFrame end = new MenuFrame("end");
        MenuSystem menuSystem = new MenuSystem(new ArrayList<>(Arrays.asList(start, end)));
        GraphBuilder graphBuilder = new DefaultGraphBuilder(menuSystem);
        Graph<MenuFrame, DefaultEdge> graph = graphBuilder.buildFramesGraph(new DefaultPropertiesParser());
        Assert.assertTrue(graph.vertexSet().contains(start));
        Assert.assertTrue(graph.vertexSet().contains(end));
        Assert.assertEquals(Collections.singletonList(end), Graphs.successorListOf(graph, start));
        Assert.assertEquals(Collections.singletonList(start), Graphs.predecessorListOf(graph, end));
    }

    @Test
    public void buildFramesGraphBothEdgesTest() {
        MenuItem startOptionOne = new MenuItem()
                .withName("option1")
                .goTo("end");
        MenuItem startOptionTwo = new MenuItem()
                .withName("option2");
        MenuFrame start = new MenuFrame("start")
                .gotoMenuWithName("mid");
        start.addItem(startOptionOne);
        start.addItem(startOptionTwo);
        MenuFrame mid = new MenuFrame("mid");
        MenuFrame end = new MenuFrame("end");
        MenuSystem menuSystem = new MenuSystem(new ArrayList<>(Arrays.asList(start, mid, end)));
        GraphBuilder graphBuilder = new DefaultGraphBuilder(menuSystem);
        Graph<MenuFrame, DefaultEdge> graph = graphBuilder.buildFramesGraph(new DefaultPropertiesParser());
        Assert.assertTrue(graph.vertexSet().contains(start));
        Assert.assertTrue(graph.vertexSet().contains(mid));
        Assert.assertTrue(graph.vertexSet().contains(end));
        Assert.assertEquals(Collections.singletonList(end), Graphs.successorListOf(graph, start));
        Assert.assertEquals(Collections.singletonList(start), Graphs.predecessorListOf(graph, end));
        Assert.assertFalse(Graphs.vertexHasPredecessors(graph, mid));
        Assert.assertFalse(Graphs.vertexHasSuccessors(graph, mid));
    }
}
