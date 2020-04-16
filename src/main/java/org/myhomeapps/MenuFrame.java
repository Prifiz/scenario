package org.myhomeapps;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Observable;

public class MenuFrame extends Observable {

    private int id;
    private String name;
    private String text;
    private String gotoMenuName;

    private String userInput;

    private String bindMethod;
    private String bindField;

    private List<MenuItem> menuItems;

    public MenuFrame(int id, String name, String text, String gotoMenuName) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.menuItems = new ArrayList<>();
        this.userInput = "";
        this.gotoMenuName = gotoMenuName;
    }

    public MenuFrame bindMethod(String bindMethod) {
        this.bindMethod = bindMethod;
        return this;
    }

    public MenuFrame gotoMenuWithName(String gotoMenuName) {
        this.gotoMenuName = gotoMenuName;
        return this;
    }

    public void addItem(MenuItem menuItem) {
        this.menuItems.add(menuItem);
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

    public List<MenuItem> getMenuItems() {
        return menuItems;
    }

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
        setChanged();
        notifyObservers();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuFrame menuFrame = (MenuFrame) o;
        return id == menuFrame.id &&
                Objects.equals(name, menuFrame.name) &&
                Objects.equals(text, menuFrame.text) &&
                Objects.equals(userInput, menuFrame.userInput) &&
                Objects.equals(bindMethod, menuFrame.bindMethod) &&
                Objects.equals(menuItems, menuFrame.menuItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, text, userInput, bindMethod, menuItems);
    }

    public String getBindMethod() {
        return bindMethod;
    }

    public void setBindMethod(String bindMethod) {
        this.bindMethod = bindMethod;
    }

    public String getBindField() {
        return bindField;
    }

    public void setBindField(String bindField) {
        this.bindField = bindField;
    }

    public String getGotoMenuName() {
        return gotoMenuName;
    }

    public void setGotoMenuName(String gotoMenuName) {
        this.gotoMenuName = gotoMenuName;
    }
}
