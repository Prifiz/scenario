package org.myhomeapps.menuentities.properties;

import java.util.Collection;

public class Properties {

    private final Collection<Property> properties;

    public Properties(Collection<Property> properties) {
        this.properties = properties;
    }

    public boolean containsHome() {
        return properties.stream().anyMatch(property -> HomeProperty.class.equals(property.getClass()));
    }

    public boolean isInputExpected() {
        return properties.stream().noneMatch(property -> NoInputExpectedProperty.class.equals(property.getClass()));
    }

    public boolean isExit() {
        return properties.stream().anyMatch(property -> ExitProperty.class.equals(property.getClass()));
    }
}
