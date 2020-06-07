package org.myhomeapps.walkers;

import org.myhomeapps.adapters.CommandLineAdapter;

import java.io.IOException;

public interface MenuWalker {
    MenuWalker registerAdapter(CommandLineAdapter adapter);
    void run() throws IOException;
}
