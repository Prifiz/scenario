package org.myhomeapps.menuentities;

import java.util.Objects;

public class MenuItem {

    private int id;
    private String name;
    private String text;
    private String gotoMenu;

    public MenuItem(int id, String name, String text, String gotoMenu) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.gotoMenu = gotoMenu;
    }

    public MenuItem() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getGotoMenu() {
        return gotoMenu;
    }

    public void setGotoMenu(String gotoMenu) {
        this.gotoMenu = gotoMenu;
    }

//    @Override
//    public String toString() {
//        return "MenuItem{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", text='" + text + '\'' +
//                ", gotoMenu='" + gotoMenu + '\'' +
//                '}';
//    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuItem item = (MenuItem) o;
        return Objects.equals(name, item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
