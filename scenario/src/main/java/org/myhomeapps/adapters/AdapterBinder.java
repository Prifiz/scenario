package org.myhomeapps.adapters;

import org.myhomeapps.menuentities.Bindings;

public interface AdapterBinder {
    boolean bind(Bindings bindings, String userInput);
    String getRunAdapterOutput();
    void register(CommandLineAdapter adapter);
}
