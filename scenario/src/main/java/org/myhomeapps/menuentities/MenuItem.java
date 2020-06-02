package org.myhomeapps.menuentities;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class MenuItem {

    private int id;
    private String name = "";
    private String text = "";
    private String gotoMenu = "";
    private List<String> inputAlternatives = new ArrayList<>();

    public MenuItem(int id, String name, String text, String gotoMenu) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.gotoMenu = gotoMenu;
    }

    public MenuItem withName(String name) {
        this.name = name;
        return this;
    }

    public MenuItem goTo(String gotoMenu) {
        this.gotoMenu = gotoMenu;
        return this;
    }

    public MenuItem withText(String text) {
        this.text = text;
        return this;
    }

    public MenuItem addInputAlternative(String inputAlternative) {
        this.inputAlternatives.add(inputAlternative);
        return this;
    }

    public MenuItem() {
    }

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
