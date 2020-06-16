package org.myhomeapps.walkers.validators;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Assert;
import org.junit.Test;
import org.myhomeapps.menuentities.MenuFrame;
import org.myhomeapps.menuentities.properties.DefaultPropertiesParser;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MultipleHomeFramesValidatorTest {

    @Test
    public void multipleHomeFramesTest() {
        Graph<MenuFrame, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        final String firstName = "FirstMenu";
        final String secondName = "SecondMenu";
        final String thirdName = "ThirdMenu";
        MenuFrame first = new MenuFrame(firstName);
        first.setProperties(Collections.singletonList("home"));
        MenuFrame second = new MenuFrame(secondName);
        second.setProperties(Collections.singletonList("home"));
        MenuFrame third = new MenuFrame(thirdName);
        graph.addVertex(first);
        graph.addVertex(second);
        graph.addVertex(third);
        MultipleHomeFramesValidator<MenuFrame, DefaultEdge> validator =
                new MultipleHomeFramesValidator<>(graph, new DefaultPropertiesParser());
        String[] expected = new String[]{ firstName, secondName};
        Arrays.sort(expected);
        List<String> occurrences = validator.findOccurrences();
        Collections.sort(occurrences);
        Assert.assertArrayEquals(expected, occurrences.toArray());
    }

    @Test
    public void singleHomeFrameTest() {
        Graph<MenuFrame, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        final String firstName = "FirstMenu";
        final String secondName = "SecondMenu";
        MenuFrame first = new MenuFrame(firstName);
        first.setProperties(Collections.singletonList("home"));
        MenuFrame second = new MenuFrame(secondName);
        graph.addVertex(first);
        graph.addVertex(second);
        MultipleHomeFramesValidator<MenuFrame, DefaultEdge> validator =
                new MultipleHomeFramesValidator<>(graph, new DefaultPropertiesParser());
        Assert.assertTrue(validator.findOccurrences().isEmpty());
    }
}
