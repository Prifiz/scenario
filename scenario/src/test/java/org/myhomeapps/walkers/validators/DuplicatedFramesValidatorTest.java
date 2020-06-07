package org.myhomeapps.walkers.validators;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.myhomeapps.menuentities.MenuFrame;

public class DuplicatedFramesValidatorTest {

    MenuFrame testedMenu;
    MenuFrame duplicateMenu;

    @Before
    public void initialize() {
        testedMenu = new MenuFrame();
        duplicateMenu = new MenuFrame();
        testedMenu.setName("testedMenu");
        duplicateMenu.setGotoMenu("gotoMenu");
    }

    @Test
    public void findOccurrencesPositiveTest() {
        Graph<MenuFrame, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        graph.addVertex(testedMenu);
        duplicateMenu.setName("testedMenu");
        graph.addVertex(duplicateMenu);
        DuplicatedFramesValidator<MenuFrame, DefaultEdge> deadEndsValidator =
                new DuplicatedFramesValidator<>(graph);
        Assert.assertFalse(deadEndsValidator.findOccurrences().isEmpty());
    }

    @Test
    public void findOccurrencesNegativeTest() {
        Graph<MenuFrame, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        graph.addVertex(testedMenu);
        duplicateMenu.setName("duplicateMenu");
        graph.addVertex(duplicateMenu);
        DuplicatedFramesValidator<MenuFrame, DefaultEdge> deadEndsValidator =
                new DuplicatedFramesValidator<>(graph);
        Assert.assertTrue(deadEndsValidator.findOccurrences().isEmpty());
    }
}
