package org.myhomeapps.walkers.graphbuilders;

import org.apache.commons.lang3.StringUtils;
import org.myhomeapps.menuentities.MenuFrame;
import org.myhomeapps.menuentities.MenuItem;

import java.io.IOException;

public class GotoLevelFactory {

    private final MenuFrame frame;

    public GotoLevelFactory(MenuFrame frame) {
        this.frame = frame;
    }

    public GotoLevel defineLevel() throws IOException {
        if (isItemLevelGotosDefined()) {
            return GotoLevel.ITEM;
        }
        if (StringUtils.isNotBlank(frame.getGotoMenu())) {
            return GotoLevel.MENU;
        }
        throw new IOException(
                "Goto not defined neither on item nor on menu level of frame: [" + frame.getName() + "]");
    }

    protected boolean isItemLevelGotosDefined() {
        if (frame.getItems() == null || frame.getItems().isEmpty()) {
            return false;
        }
        for (MenuItem item : frame.getItems()) {
            if (StringUtils.isBlank(item.getGotoMenu())) {
                return false;
            }
        }
        return true;
    }
}
