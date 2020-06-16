package org.prifizapps.walkers.validators;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.prifizapps.menuentities.MenuFrame;
import org.prifizapps.menuentities.MenuItem;

import java.util.Arrays;

public class DuplicatedItemsValidatorTest {

    MenuFrame start;
    MenuItem testItem;
    MenuItem duplicateTestItem;

    @Before
    public void initialize() {
        start = new MenuFrame();
        testItem = new MenuItem();
        testItem.setName("testItem");
        duplicateTestItem = new MenuItem();
    }

    @Test
    public void findDuplicatedItemsNegativeTest() {
        Graph<MenuFrame, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        duplicateTestItem.setName("duplicateTestItem");
        start.setItems(Arrays.asList(testItem,duplicateTestItem));
        graph.addVertex(start);
        DuplicatedItemsValidator<MenuFrame, DefaultEdge> duplicatedItemsValidator =
                new DuplicatedItemsValidator<>(graph);
        Assert.assertTrue(duplicatedItemsValidator.findOccurrences().isEmpty());
    }

    @Test
    public void findDuplicatedItemsPositiveTest() {
        Graph<MenuFrame, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        duplicateTestItem.setName("testItem");
        start.setItems(Arrays.asList(testItem,duplicateTestItem));
        graph.addVertex(start);
        DuplicatedItemsValidator<MenuFrame, DefaultEdge> duplicatedItemsValidator =
                new DuplicatedItemsValidator<>(graph);
        Assert.assertFalse(duplicatedItemsValidator.findOccurrences().isEmpty());
    }
}
