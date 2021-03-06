package org.prifizapps.menuentities;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.prifizapps.menuentities.input.InputRule;
import org.prifizapps.menuentities.properties.DefaultPropertiesParser;
import org.prifizapps.walkers.graphbuilders.GotoLevel;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
public class MenuFrame {

    //private int id;
    private String name = "";
    private String text = "";
    private String gotoMenu = ""; // always only one!!!
    //private String userInput = "";
    private Bindings bindings = new Bindings();
//    private String method = "";
//    private String field = "";
    private List<String> properties = new ArrayList<>();
    private List<MenuItem> items = new ArrayList<>();
    private List<InputRule> inputRules = new ArrayList<>();

    //private List<PropertyChangeListener> changes = new ArrayList<>();

    public MenuFrame(String name, String text, String gotoMenu) {
        super();
        //this.id = id;
        this.name = name;
        this.text = text;
        this.items = new ArrayList<>();
        //this.userInput = "";
        this.gotoMenu = gotoMenu;
    }

//    private void notifyListeners(Object object, String property, String oldValue, String newValue) {
//        for (PropertyChangeListener name : changes) {
//            name.propertyChange(new PropertyChangeEvent(this, property, oldValue, newValue));
//        }
//    }

    public MenuFrame() {
    }

    // for unit testing
    public MenuFrame(String name) {
        this.name = name;
    }

//    public MenuFrame bindMethod(String bindMethod) {
//        this.method = bindMethod;
//        return this;
//    }

    public MenuFrame gotoMenuWithName(String gotoMenuName) {
        this.gotoMenu = gotoMenuName;
        return this;
    }

    public void addItem(MenuItem menuItem) {
        this.items.add(menuItem);
    }

//    public void addListener(PropertyChangeListener listener) {
//        this.changes.add(listener);
//    }

//    public void removeListener(PropertyChangeListener listener) {
//        this.changes.remove(listener);
//    }

//    public void setUserInput(String userInput) {
//        notifyListeners(this,
//                "userInput",
//                        this.userInput,
//                this.userInput = userInput);
//    }

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
        MenuFrame frame = (MenuFrame) o;
        return name.equals(frame.name) &&
                Objects.equals(text, frame.text) &&
                Objects.equals(gotoMenu, frame.gotoMenu) &&
                Objects.equals(properties, frame.properties) &&
                Objects.equals(items, frame.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, text, gotoMenu, properties, items);
    }

    @Override
    public String toString() {
        return name;
    }

    public boolean isInputExpected() {
        return new DefaultPropertiesParser().parseProperties(properties).isInputExpected();
    }

    public GotoLevel getGotoLevel() throws IOException {
        if (isItemLevelGotoDefined()) {
            return GotoLevel.ITEM;
        }
        if (StringUtils.isNotBlank(getGotoMenu())) {
            return GotoLevel.MENU;
        }
        throw new IOException(
                "Goto not defined neither on item nor on menu level of frame: [" + getName() + "]");
    }

    boolean isItemLevelGotoDefined() {
        if (getItems() == null || getItems().isEmpty()) {
            return false;
        }
        for (MenuItem item : getItems()) {
            if (StringUtils.isNotBlank(item.getGotoMenu())) {
                return true;
            }
        }
        return false;
    }

}
