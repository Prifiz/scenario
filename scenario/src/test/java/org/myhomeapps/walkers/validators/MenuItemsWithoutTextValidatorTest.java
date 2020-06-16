package org.myhomeapps.walkers.validators;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Assert;
import org.junit.Test;
import org.myhomeapps.menuentities.MenuFrame;
import org.myhomeapps.menuentities.MenuItem;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.myhomeapps.walkers.validators.MenuItemsWithoutTextValidator.VIOLATED_ITEM_NAME_PREFIX;

public class MenuItemsWithoutTextValidatorTest {

    @Test
    public void menuWithItemWithoutTextTest() {
        Graph<MenuFrame, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        final String firstName = "FirstMenu";
        final String secondName = "SecondMenu";
        MenuFrame first = new MenuFrame(firstName);
        first.addItem(new MenuItem().withText("first item"));
        first.addItem(new MenuItem());
        MenuFrame second = new MenuFrame(secondName);
        second.addItem(new MenuItem().withText("item"));
        graph.addVertex(first);
        graph.addVertex(second);
        MenuItemsWithoutTextValidator<MenuFrame, DefaultEdge> validator =
                new MenuItemsWithoutTextValidator<>(graph);
        String[] expected = new String[]{VIOLATED_ITEM_NAME_PREFIX + firstName};
        Arrays.sort(expected);
        List<String> occurrences = validator.findOccurrences();
        Collections.sort(occurrences);
        Assert.assertArrayEquals(expected, occurrences.toArray());
    }

    @Test
    public void menuWithCorrectItemsTest() {
        Graph<MenuFrame, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        final String firstName = "FirstMenu";
        final String secondName = "SecondMenu";
        MenuFrame first = new MenuFrame(firstName);
        first.addItem(new MenuItem().withText("first item"));
        first.addItem(new MenuItem().withText("second item"));
        MenuFrame second = new MenuFrame(secondName);
        second.addItem(new MenuItem().withText("item"));
        graph.addVertex(first);
        graph.addVertex(second);
        MenuItemsWithoutTextValidator<MenuFrame, DefaultEdge> validator =
                new MenuItemsWithoutTextValidator<>(graph);
        Assert.assertTrue(validator.findOccurrences().isEmpty());
    }
}
