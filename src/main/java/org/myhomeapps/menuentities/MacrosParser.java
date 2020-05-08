package org.myhomeapps.menuentities;

import java.util.Collection;

public interface MacrosParser {
    Macros parseMacros(Collection<String> macros);
    void registerCustomMacro(Macro macro);
}
