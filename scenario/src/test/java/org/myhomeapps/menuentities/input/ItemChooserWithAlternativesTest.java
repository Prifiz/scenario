package org.myhomeapps.menuentities.input;

import org.junit.Assert;
import org.junit.Test;
import org.myhomeapps.menuentities.MenuFrame;
import org.myhomeapps.menuentities.MenuItem;

public class ItemChooserWithAlternativesTest {

    @Test
    public void chooseItemExpectedInputTest() {
        MenuFrame menuFrame = new MenuFrame();
        MenuItem expected = new MenuItem().withText("First");
        menuFrame.addItem(expected);
        menuFrame.addItem(new MenuItem().withText("Second"));
        ItemChooser itemChooser = new ItemChooserWithAlternatives();
        menuFrame.setUserInput("first");
        Assert.assertEquals(expected, itemChooser.chooseItem(menuFrame));
        menuFrame.setUserInput("First");
        Assert.assertEquals(expected, itemChooser.chooseItem(menuFrame));
    }

    @Test
    public void chooseItemAlternativeInputTest() {
        MenuFrame menuFrame = new MenuFrame();
        MenuItem expected = new MenuItem()
                .withText("First")
                .addInputAlternative("One");
        menuFrame.addItem(expected);
        menuFrame.addItem(new MenuItem().withText("Second"));
        ItemChooser itemChooser = new ItemChooserWithAlternatives();
        menuFrame.setUserInput("One");
        Assert.assertEquals(expected, itemChooser.chooseItem(menuFrame));
        menuFrame.setUserInput("one");
        Assert.assertEquals(expected, itemChooser.chooseItem(menuFrame));
    }

    @Test
    public void chooseItemIncorrectInputTest() {
        MenuFrame menuFrame = new MenuFrame();
        MenuItem expected = new MenuItem()
                .withText("First")
                .addInputAlternative("One");
        menuFrame.addItem(expected);
        menuFrame.addItem(new MenuItem().withText("Second"));
        ItemChooser itemChooser = new ItemChooserWithAlternatives();
        menuFrame.setUserInput("NonMatchingInput");
        Assert.assertNull(itemChooser.chooseItem(menuFrame));
    }
}
