package org.myhomeapps.menuentities;

import java.util.Collection;
import java.util.Collections;

public class UnknownMacro implements Macro {

    private String nonParsedText;

    public UnknownMacro(String nonParsedText) {
        this.nonParsedText = nonParsedText;
    }

    @Override
    public Collection<String> getDisplayNames() {
        return Collections.singletonList("");
    }
}
