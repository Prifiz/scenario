package org.myhomeapps.menuentities.properties;

import java.util.Collection;
import java.util.Collections;

public class UnknownProperty implements Property {

    private String nonParsedText;

    public UnknownProperty(String nonParsedText) {
        this.nonParsedText = nonParsedText;
    }

    @Override
    public Collection<String> getDisplayNames() {
        return Collections.singletonList("");
    }
}
