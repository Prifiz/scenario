package org.prifizapps.walkers.validators;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Assert;
import org.junit.Test;
import org.prifizapps.menuentities.MenuFrame;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FramesWithoutTextValidatorTest {

    @Test
    public void menuWithoutTextTest() {
        Graph<MenuFrame, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        final String firstName = "FirstMenu";
        MenuFrame first = new MenuFrame(firstName);
        graph.addVertex(first);
        FramesWithoutTextValidator<MenuFrame, DefaultEdge> validator =
                new FramesWithoutTextValidator<>(graph);
        String[] expected = new String[] {firstName};
        Arrays.sort(expected);
        List<String> occurrences = validator.findOccurrences();
        Collections.sort(occurrences);
        Assert.assertArrayEquals(expected, occurrences.toArray());
    }

    @Test
    public void allMenusWithTextTest() {
        Graph<MenuFrame, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        final String firstName = "FirstMenu";
        MenuFrame first = new MenuFrame(firstName, "SomeText", "");
        graph.addVertex(first);
        FramesWithoutTextValidator<MenuFrame, DefaultEdge> validator =
                new FramesWithoutTextValidator<>(graph);
        Assert.assertTrue(validator.findOccurrences().isEmpty());
    }
}
