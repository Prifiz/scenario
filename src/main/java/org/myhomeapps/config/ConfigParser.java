package org.myhomeapps.config;

import org.myhomeapps.menuentities.MacrosParser;
import org.myhomeapps.menuentities.MenuItem;
import org.myhomeapps.menuentities.MenuSettings;
import org.myhomeapps.menuentities.MenuSystem;

import java.io.FileNotFoundException;
import java.util.List;

public interface ConfigParser {
    MenuSystem parseMenuSystem() throws FileNotFoundException;
    List<MenuItem> parseStandaloneItems();
    MenuSettings parseSettings();
}
