package org.prifizapps.adapters;

import org.prifizapps.menuentities.Bindings;

public interface AdapterBinder {
    boolean bind(Bindings bindings, String userInput);
    String getRunAdapterOutput();
    void register(CommandLineAdapter adapter);
}
