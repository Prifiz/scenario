package org.myhomeapps.menuentities;

import mockit.*;
import org.myhomeapps.walkers.graphbuilders.GotoLevel;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class MenuFrameTest {

    private MenuFrame menuFrame;
    private MenuItem menuItem;

    @BeforeTest
    public void setUp() {
        menuFrame = new MenuFrame();
        menuItem = new MenuItem();
    }

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

    @Test(expectedExceptions = IOException.class)
    public void gotoNotDefineTest() throws IOException {
        new Expectations(menuFrame) {{
            menuFrame.isItemLevelGotoDefined();
            result = false;
        }};
        menuFrame.getGotoLevel();
    }

}
