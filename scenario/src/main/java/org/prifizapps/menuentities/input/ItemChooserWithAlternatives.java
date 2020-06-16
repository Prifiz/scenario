package org.prifizapps.menuentities.input;

import org.prifizapps.menuentities.MenuItem;

import java.util.Collection;

public class ItemChooserWithAlternatives implements ItemChooser {
    @Override
    public MenuItem chooseItem(Collection<MenuItem> items, String userInput) {
        for (MenuItem item : items) {
            if (item.getText().equalsIgnoreCase(userInput)) {
                return item;
            }
            if (item.getInputAlternatives().stream()
                    .anyMatch(altInput -> altInput.equalsIgnoreCase(userInput))) {
                return item;
            }
        }
        return null;
    }
}
