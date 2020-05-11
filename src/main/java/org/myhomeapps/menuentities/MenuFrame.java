package org.myhomeapps.menuentities;

import java.util.*;
import java.util.stream.Collectors;

public class MenuFrame extends Observable {

    private int id;
    private String name;
    private String text;
    private String gotoMenu; // always only one!!!
    private String userInput;
    private String method;
    private String field;
    private List<String> properties;
    private List<MenuItem> items;
    private boolean inputExpected = true;


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

    public boolean hasItems() {
        return !(items == null || items.isEmpty());
    }

    public Collection<MenuItem> findDuplicates() {
        Set<MenuItem> uniques = new HashSet<>();
        return Optional.ofNullable(items)
                .orElse(new ArrayList<>())
                .stream()
                .filter(e -> !uniques.add(e))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MenuFrame menuFrame = (MenuFrame) o;
        return
                Objects.equals(name, menuFrame.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
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

    public void setInputExpected(boolean inputExpected) {
        this.inputExpected = inputExpected;
    }

    //    @Override
//    public String toString() {
//        return "MenuFrame{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", text='" + text + '\'' +
//                ", gotoMenu='" + gotoMenu + '\'' +
//                ", userInput='" + userInput + '\'' +
//                ", method='" + method + '\'' +
//                ", field='" + field + '\'' +
//                ", menuItems=" + items +
//                '}';
//    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isInputExpected() {
        return new DefaultMacrosParser().parseMacros(properties).isInputExpected();
    }

    public List<String> getProperties() {
        return properties;
    }

    public void setProperties(List<String> properties) {
        this.properties = properties;
    }
}