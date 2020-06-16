package org.prifizapps.menuentities.properties;

import lombok.Getter;

import java.util.Collection;
import java.util.Collections;

@Getter
public class UnknownProperty implements Property {

    private String nonParsedText;

    public UnknownProperty(String nonParsedText) {
        this.nonParsedText = nonParsedText;
    }

    @Override
    public Collection<String> getDisplayNames() {
        return Collections.singletonList("");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UnknownProperty unknownProperty = (UnknownProperty) o;
        return nonParsedText.equals(unknownProperty.getNonParsedText());
    }
}
