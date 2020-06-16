package org.prifizapps.menuentities.input;

import org.prifizapps.menuentities.MenuItem;

import java.util.Collection;

public interface ItemChooser {
    MenuItem chooseItem(Collection<MenuItem> items, String userInput);
}
