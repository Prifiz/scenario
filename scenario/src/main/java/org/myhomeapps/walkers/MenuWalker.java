package org.myhomeapps.walkers;

import org.myhomeapps.adapters.CommandLineAdapter;

public interface MenuWalker {
    MenuWalker registerAdapter(CommandLineAdapter adapter);
    void run();
}
