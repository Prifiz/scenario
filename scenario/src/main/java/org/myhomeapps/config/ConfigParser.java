package org.myhomeapps.config;

import org.myhomeapps.menuentities.MenuSystem;

import java.io.FileNotFoundException;

public interface ConfigParser {
    MenuSystem parseMenuSystem() throws FileNotFoundException;
}
