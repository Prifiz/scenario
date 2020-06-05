package org.myhomeapps.menuentities.input;

import org.myhomeapps.menuentities.MenuItem;

import java.util.Collection;

public interface ItemChooser {
    MenuItem chooseItem(Collection<MenuItem> items, String userInput);
}
