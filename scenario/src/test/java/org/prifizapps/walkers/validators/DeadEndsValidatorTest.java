package org.prifizapps.walkers.validators;

import mockit.Expectations;
import mockit.Tested;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Assert;
import org.junit.Test;
import org.prifizapps.menuentities.MenuFrame;
import org.prifizapps.menuentities.properties.DefaultPropertiesParser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class DeadEndsValidatorTest {


    @Tested
    MenuFrame start;
    @Tested
    MenuFrame end;


    @Test
    public void getNotEmptyDeadEndFramesTest() {
        Graph<MenuFrame, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        start.setName("start");
        end.setName("end");
        graph.addVertex(start);
        graph.addVertex(end);
        graph.addEdge(start, end);
        DeadEndsValidator<MenuFrame, DefaultEdge> deadEndsValidator =
                new DeadEndsValidator<>(graph, new DefaultPropertiesParser());
        Assert.assertFalse(deadEndsValidator.getDeadEndFrames().isEmpty());
    }

    @Test
    public void getEmptyDeadEndFramesTest() {
        Graph<MenuFrame, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        start.setName("start");
        end.setName("end");
        graph.addVertex(start);
        graph.addVertex(end);
        graph.addEdge(start, end);
        graph.addEdge(end, start);
        DeadEndsValidator<MenuFrame, DefaultEdge> deadEndsValidator =
                new DeadEndsValidator<>(graph, new DefaultPropertiesParser());
        Assert.assertTrue(deadEndsValidator.getDeadEndFrames().isEmpty());
    }

    @Test
    public void findOccurrencesPositiveTest() {
        DeadEndsValidator<MenuFrame, DefaultEdge> deadEndsValidator =
                new DeadEndsValidator<>(new DefaultDirectedGraph<>(DefaultEdge.class), new DefaultPropertiesParser());
        end.setName("end");
        new Expectations(deadEndsValidator) {{
            deadEndsValidator.getDeadEndFrames();
            result = Collections.singletonList(end);
        }};
        Collection<String> actualResult = deadEndsValidator.findOccurrences();
        Assert.assertEquals(1, actualResult.size());
        Assert.assertTrue(actualResult.contains(end.getName()));
    }

    @Test
    public void findOccurrencesNegativeTest() {
        DeadEndsValidator<MenuFrame, DefaultEdge> deadEndsValidator =
                new DeadEndsValidator<>(new DefaultDirectedGraph<>(DefaultEdge.class), new DefaultPropertiesParser());
        end.setName("end");
        new Expectations(deadEndsValidator) {{
            deadEndsValidator.getDeadEndFrames();
            result = new ArrayList<>();
        }};
        Assert.assertTrue(deadEndsValidator.findOccurrences().isEmpty());
    }
}
