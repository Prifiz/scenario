package org.myhomeapps.menuentities;

import java.util.List;

public class MenuSystem {

    private List<MenuFrame> menuSystem;


    public MenuSystem(List<MenuFrame> menuSystem) {
        this.menuSystem = menuSystem;
    }

    public MenuSystem() {
    }

    public List<MenuFrame> getMenuSystem() {
        return menuSystem;
    }

    public void setMenuSystem(List<MenuFrame> menuSystem) {
        this.menuSystem = menuSystem;
    }

    // TODO macros validator needed here

    public MenuFrame getHomeFrame(MacrosParser parser) {
        // FIXME this check wouldn't be needed if macros validator existed
        // FIXME containsHome called TWICE here!!!!!!!!!!!!!!!!!!!!!
        if(getHomeFramesCount(parser) > 1) {
            throw new IllegalStateException("More than one home frames found!");
        } else if(getHomeFramesCount(parser) == 0) {
            throw new IllegalStateException("No home frames found!");
        }

        return menuSystem.stream()
                .filter(frame -> parser.parseMacros(frame.getProperties()).containsHome())
                .findAny()
                .orElse(null);
    }

    private int getHomeFramesCount(MacrosParser parser) {
        return (int) menuSystem.stream()
                .filter(frame -> parser.parseMacros(frame.getProperties()).containsHome())
                .count();
    }
}
