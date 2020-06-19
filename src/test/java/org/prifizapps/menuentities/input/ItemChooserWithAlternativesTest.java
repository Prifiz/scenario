package org.prifizapps.menuentities.input;

import org.junit.Assert;
import org.junit.Test;
import org.prifizapps.menuentities.MenuItem;

import java.util.Arrays;
import java.util.List;

public class ItemChooserWithAlternativesTest {

    @Test
    public void chooseItemExpectedInputTest() {
        MenuItem expected = new MenuItem().withText("First");
        MenuItem second = new MenuItem().withText("Second");
        List<MenuItem> items = Arrays.asList(expected, second);
        ItemChooser itemChooser = new ItemChooserWithAlternatives();
        Assert.assertEquals(expected, itemChooser.chooseItem(items, "first"));
        Assert.assertEquals(expected, itemChooser.chooseItem(items, "First"));
    }

    @Test
    public void chooseItemAlternativeInputTest() {
        MenuItem expected = new MenuItem()
                .withText("First")
                .addInputAlternative("One");
        MenuItem second = new MenuItem().withText("Second");
        List<MenuItem> items = Arrays.asList(expected, second);
        ItemChooser itemChooser = new ItemChooserWithAlternatives();
        Assert.assertEquals(expected, itemChooser.chooseItem(items, "One"));
        Assert.assertEquals(expected, itemChooser.chooseItem(items, "one"));
    }

    @Test
    public void chooseItemIncorrectInputTest() {
        MenuItem expected = new MenuItem()
                .withText("First")
                .addInputAlternative("One");
        MenuItem second = new MenuItem().withText("Second");
        List<MenuItem> items = Arrays.asList(expected, second);
        ItemChooser itemChooser = new ItemChooserWithAlternatives();
        Assert.assertNull(itemChooser.chooseItem(items, "NonMatchingInput"));
    }
}
