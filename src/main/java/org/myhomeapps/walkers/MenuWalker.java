package org.myhomeapps.walkers;

import org.myhomeapps.adapters.CommandLineAdapter;
import org.myhomeapps.menuentities.Macro;

public interface MenuWalker {

    void run();
    void registerAdapter(CommandLineAdapter adapter);
    void registerCustomMacro(Macro customMacro);
}
