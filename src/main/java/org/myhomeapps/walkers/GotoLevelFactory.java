package org.myhomeapps.walkers;

import org.myhomeapps.menuentities.MenuFrame;
import org.myhomeapps.menuentities.MenuItem;

import java.io.IOException;

public class GotoLevelFactory {

    private final GotoLevel level;

    public enum GotoLevel {
        MENU, ITEM;
    }

    public GotoLevelFactory(MenuFrame frame) throws IOException {
        if (isItemLevelGotosDefined(frame)) {
            this.level = GotoLevel.ITEM;
        } else if (frame.getGotoMenu() != null && !frame.getGotoMenu().isEmpty()) {
            this.level = GotoLevel.MENU;
        } else {
            throw new IOException("Goto not defined neither on item nor on menu level of frame: [" + frame.getName() + "]");
        }
    }

    private boolean isItemLevelGotosDefined(MenuFrame frame) {
        if (frame.getItems() == null || frame.getItems().isEmpty()) {
            return false;
        }
        for (MenuItem item : frame.getItems()) {
            if (item.getGotoMenu() == null || item.getGotoMenu().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public GotoLevel getLevel() {
        return level;
    }
}
