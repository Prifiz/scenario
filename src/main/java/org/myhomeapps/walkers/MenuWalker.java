package org.myhomeapps.walkers;

import org.myhomeapps.adapters.CommandLineAdapter;

public interface MenuWalker {
    void run();
    void registerAdapter(CommandLineAdapter adapter);
}
