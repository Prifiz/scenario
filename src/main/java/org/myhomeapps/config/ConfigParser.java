package org.myhomeapps.config;

import org.myhomeapps.menumodel.MenuItem;
import org.myhomeapps.menumodel.MenuSettings;
import org.myhomeapps.menumodel.MenuSystem;

import java.io.FileNotFoundException;
import java.util.List;

public interface ConfigParser {
    MenuSystem parseMenuSystem() throws FileNotFoundException;
    List<MenuItem> parseStandaloneItems();
    MenuSettings parseSettings();
}
