package org.myhomeapps.menuentities;

import mockit.Expectations;
import mockit.Mock;
import mockit.MockUp;
import mockit.Tested;
import org.junit.Assert;
import org.junit.Test;
import org.myhomeapps.walkers.graphbuilders.GotoLevel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class MenuFrameTest {

    @Tested MenuFrame menuFrame;
    @Tested MenuItem menuItem;

    @Test
    public void isItemLevelGotoDefinedFrameWithNoItemsTest() {
        menuFrame.setItems(null);
        Assert.assertFalse(menuFrame.isItemLevelGotoDefined());
    }

    @Test
    public void isItemLevelGotoDefinedFrameWithEmptyItemsTest() {
        menuFrame.setItems(new ArrayList<>());
        Assert.assertFalse(menuFrame.isItemLevelGotoDefined());
    }

    @Test
    public void isItemLevelGotoDefinedItemsWithoutGoToTest() {
        menuItem.setGotoMenu("");
        menuFrame.setItems(Collections.singletonList(menuItem));
        Assert.assertFalse(menuFrame.isItemLevelGotoDefined());
    }

    @Test
    public void isItemLevelGotoDefinedPositiveTest() {
        menuItem.setGotoMenu("firstMenu");
        menuFrame.setItems(Collections.singletonList(menuItem));
        Assert.assertTrue(menuFrame.isItemLevelGotoDefined());
    }

    @Test
    public void getItemLevelGotoTest() throws IOException {
        new Expectations(menuFrame) {{
            menuFrame.isItemLevelGotoDefined();
            result = true;
        }};
       /* new MockUp<MenuFrame>() {
            @Mock
            boolean isItemLevelGotoDefined() {
                return true;
            }
        };*/
        GotoLevel actual = menuFrame.getGotoLevel();
        Assert.assertEquals(actual, GotoLevel.ITEM);
    }

    @Test
    public void getMenuLevelGotoTest() throws IOException {
        new Expectations(menuFrame) {{
            menuFrame.isItemLevelGotoDefined();
            result = false;
        }};
        menuFrame.setGotoMenu("firstMenu");
        GotoLevel actual = menuFrame.getGotoLevel();
        Assert.assertEquals(actual, GotoLevel.MENU);
    }

    @Test(expected = IOException.class)
    public void gotoNotDefineTest() throws IOException {
        new Expectations(menuFrame) {{
            menuFrame.isItemLevelGotoDefined();
            result = false;
        }};
        menuFrame.setGotoMenu("");
        menuFrame.getGotoLevel();
    }

    @Test
    public void positiveFindDuplicatesTest() {
        MenuItem defaultItem = new MenuItem();
        new MockUp<MenuItem>() {
            @Mock
            String getName() {
                return "NotUniqueName";
            }
        };
        menuFrame.setItems(new ArrayList<>(Arrays.asList(defaultItem, defaultItem)));
        Assert.assertFalse(menuFrame.findDuplicates().isEmpty());
    }

    @Test
    public void negativeFindDuplicatesTest() {
        MenuItem firstMenuItem = new MenuItem();
        firstMenuItem.setName("firstUniqName");
        MenuItem secondMenuItem = new MenuItem();
        secondMenuItem.setName("secondUniqName");
        menuFrame.setItems(new ArrayList<>(Arrays.asList(firstMenuItem, secondMenuItem)));
        Assert.assertTrue(menuFrame.findDuplicates().isEmpty());
    }
}
