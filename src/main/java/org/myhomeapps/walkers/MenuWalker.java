package org.myhomeapps.walkers;

import org.myhomeapps.adapters.CommandLineAdapter;
import org.myhomeapps.menuentities.Macro;

import java.io.IOException;

public interface MenuWalker {

    void run() throws IOException;
    void registerAdapter(CommandLineAdapter adapter);
    void registerCustomMacro(Macro customMacro);
}
