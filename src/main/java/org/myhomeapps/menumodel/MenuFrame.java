package org.myhomeapps.menumodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Observable;

public class MenuFrame extends Observable {

    private int id;
    private String name;
    private String text;
    private String gotoMenu;
    private String userInput;
    private String method;
    private String field;
    private List<MenuItem> items;

    public MenuFrame(int id, String name, String text, String gotoMenu) {
        this.id = id;
        this.name = name;
        this.text = text;
        this.items = new ArrayList<>();
        this.userInput = "";
        this.gotoMenu = gotoMenu;
    }

    public MenuFrame() {
    }

    public MenuFrame bindMethod(String bindMethod) {
        this.method = bindMethod;
        return this;
    }

    public MenuFrame gotoMenuWithName(String gotoMenuName) {
        this.gotoMenu = gotoMenuName;
        return this;
    }

    public void addItem(MenuItem menuItem) {
        this.items.add(menuItem);
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
                Objects.equals(method, menuFrame.method) &&
                Objects.equals(items, menuFrame.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, text, userInput, method, items);
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

    public String getUserInput() {
        return userInput;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public List<MenuItem> getItems() {
        return items;
    }

    public void setItems(List<MenuItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "MenuFrame{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", gotoMenu='" + gotoMenu + '\'' +
                ", userInput='" + userInput + '\'' +
                ", method='" + method + '\'' +
                ", field='" + field + '\'' +
                ", menuItems=" + items +
                '}';
    }
}
