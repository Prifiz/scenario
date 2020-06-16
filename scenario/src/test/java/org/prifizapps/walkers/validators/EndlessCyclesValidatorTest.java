package org.prifizapps.walkers.validators;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Assert;
import org.junit.Test;
import org.prifizapps.menuentities.MenuFrame;
import org.prifizapps.menuentities.properties.DefaultPropertiesParser;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class EndlessCyclesValidatorTest {

    @Test
    public void existingCycleTest() {
        Graph<MenuFrame, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        final String firstName = "FirstMenu";
        final String secondName = "SecondMenu";
        final String thirdName = "ThirdMenu";
        MenuFrame first = new MenuFrame(firstName);
        graph.addVertex(first);
        MenuFrame second = new MenuFrame(secondName);
        graph.addVertex(second);
        MenuFrame third = new MenuFrame(thirdName);
        graph.addVertex(third);
        MenuFrame exit = new MenuFrame("Exit");
        exit.setProperties(Collections.singletonList("exit"));
        graph.addVertex(exit);
        graph.addEdge(first, second);
        graph.addEdge(second, third);
        graph.addEdge(third, first);
        EndlessCyclesValidator<MenuFrame, DefaultEdge> validator =
                new EndlessCyclesValidator<>(graph, new DefaultPropertiesParser());
        String[] expected = new String[] {firstName, secondName, thirdName};
        Arrays.sort(expected);
        List<String> occurrences = validator.findOccurrences();
        Collections.sort(occurrences);
        Assert.assertArrayEquals(expected, occurrences.toArray());
    }

    @Test
    public void singleFrameCycleTest() {
        Graph<MenuFrame, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        final String firstName = "FirstMenu";
        MenuFrame first = new MenuFrame(firstName);
        graph.addVertex(first);
        MenuFrame exit = new MenuFrame("Exit");
        exit.setProperties(Collections.singletonList("exit"));
        graph.addVertex(exit);
        graph.addEdge(first, first);
        EndlessCyclesValidator<MenuFrame, DefaultEdge> validator =
                new EndlessCyclesValidator<>(graph, new DefaultPropertiesParser());
        String[] expected = new String[] {firstName};
        Arrays.sort(expected);
        List<String> occurrences = validator.findOccurrences();
        Collections.sort(occurrences);
        Assert.assertArrayEquals(expected, occurrences.toArray());
    }

    @Test
    public void cycleWithExitFrameTest() {
        Graph<MenuFrame, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        final String firstName = "FirstMenu";
        final String secondName = "SecondMenu";
        final String thirdName = "ThirdMenu";
        MenuFrame first = new MenuFrame(firstName);
        graph.addVertex(first);
        MenuFrame second = new MenuFrame(secondName);
        graph.addVertex(second);
        MenuFrame third = new MenuFrame(thirdName);
        graph.addVertex(third);
        MenuFrame exit = new MenuFrame("Exit");
        exit.setProperties(Collections.singletonList("exit"));
        graph.addVertex(exit);
        graph.addEdge(first, second);
        graph.addEdge(second, third);
        graph.addEdge(third, exit);
        graph.addEdge(exit, first);
        EndlessCyclesValidator<MenuFrame, DefaultEdge> validator =
                new EndlessCyclesValidator<>(graph, new DefaultPropertiesParser());
        Assert.assertTrue(validator.findOccurrences().isEmpty());
    }

    @Test
    public void noCyclesTest() {
        Graph<MenuFrame, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        final String firstName = "FirstMenu";
        final String secondName = "SecondMenu";
        MenuFrame first = new MenuFrame(firstName);
        graph.addVertex(first);
        MenuFrame second = new MenuFrame(secondName);
        graph.addVertex(second);
        MenuFrame exit = new MenuFrame("Exit");
        exit.setProperties(Collections.singletonList("exit"));
        graph.addVertex(exit);
        graph.addEdge(first, second);
        graph.addEdge(second, exit);
        EndlessCyclesValidator<MenuFrame, DefaultEdge> validator =
                new EndlessCyclesValidator<>(graph, new DefaultPropertiesParser());
        Assert.assertTrue(validator.findOccurrences().isEmpty());
    }
}
