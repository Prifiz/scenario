package org.myhomeapps;

public class MenuItem {

    private int id;
    private String name;
    private String text;
    private String gotoMenuName;

    public MenuItem(int id, String name, String text, String gotoMenuName) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.gotoMenuName = gotoMenuName;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getText() {
        return text;
    }

    public String getGotoMenuName() {
        return gotoMenuName;
    }
}
