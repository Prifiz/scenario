package org.myhomeapps.menuentities;

import java.util.*;
import java.util.stream.Collectors;

public class DefaultMacrosParser implements MacrosParser {

    private Collection<Macro> registeredMacros;

    public DefaultMacrosParser() {
        this.registeredMacros = initInbuiltMacros();
    }

    public void registerCustomMacro(Macro macro) {
        this.registeredMacros.add(macro);
    }

    private Collection<Macro> initInbuiltMacros() {
        Set<Macro> macros = new HashSet<>();
        macros.add(new HomeMacro());
        macros.add(new ExitMacro());
        macros.add(new NoInputExpectedMacro());
        return macros;
    }

    @Override
    public Macros parseMacros(Collection<String> macros) {
        if (macros == null) {
            return new Macros(Collections.emptyList());
        }
        return new Macros(macros.stream()
                .map(String::trim)
                .map(this::createMacro)
                .collect(Collectors.toList()));
    }

    protected Macro createMacro(String parsedName) {
        for(Macro macro : registeredMacros) {
            for(String possibleDisplayName : macro.getDisplayNames()) {
                if(possibleDisplayName.equalsIgnoreCase(parsedName)) {
                    return macro;
                }
            }
        }
        return new UnknownMacro(parsedName);
    }
}
