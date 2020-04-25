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

    public MenuFrame getHomeFrame() {
        if(getHomeFramesCount() > 1) {
            throw new IllegalStateException("More than one home frames found!");
        }
        for(MenuFrame frame : menuSystem) {
            if(frame.isHome()) {
                return frame;
            }
        }
        return null;
    }

    private int getHomeFramesCount() {
        return (int) menuSystem
                .stream()
                .filter(menuFrame -> (menuFrame.isHome()))
                .count();
    }
}
