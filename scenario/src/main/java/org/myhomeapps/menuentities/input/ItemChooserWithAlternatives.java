package org.myhomeapps.menuentities.input;

import org.myhomeapps.menuentities.MenuFrame;
import org.myhomeapps.menuentities.MenuItem;

public class ItemChooserWithAlternatives implements ItemChooser {
    @Override
    public MenuItem chooseItem(MenuFrame frame) {
        for (MenuItem item : frame.getItems()) {
            if (item.getText().equalsIgnoreCase(frame.getUserInput())) {
                return item;
            }
            if (item.getInputAlternatives().stream()
                    .anyMatch(altInput -> altInput.equalsIgnoreCase(frame.getUserInput()))) {
                return item;
            }
        }
        return null;
    }
}
