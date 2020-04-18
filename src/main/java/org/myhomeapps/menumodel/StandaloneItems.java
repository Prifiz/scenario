package org.myhomeapps.menumodel;

import java.util.List;

public class StandaloneItems {
    private List<MenuItem> standaloneItems;

    public StandaloneItems(List<MenuItem> standaloneItems) {
        this.standaloneItems = standaloneItems;
    }

    public StandaloneItems() {
    }

    public List<MenuItem> getStandaloneItems() {
        return standaloneItems;
    }

    public void setStandaloneItems(List<MenuItem> standaloneItems) {
        this.standaloneItems = standaloneItems;
    }
}
