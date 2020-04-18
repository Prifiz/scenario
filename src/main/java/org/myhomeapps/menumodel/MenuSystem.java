package org.myhomeapps.menumodel;

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
}
