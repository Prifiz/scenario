package org.myhomeapps.menuentities;

import java.util.Collection;

public class Macros {
    private Collection<Macro> macros;

    public Macros(Collection<Macro> macros) {
        this.macros = macros;
    }

    public boolean containsHome() {
        return macros.stream().anyMatch(macro -> HomeMacro.class.equals(macro.getClass()));
    }

    public boolean isInputExpected() {
        return macros.stream().noneMatch(macro -> NoInputExpectedMacro.class.equals(macro.getClass()));
    }
}
