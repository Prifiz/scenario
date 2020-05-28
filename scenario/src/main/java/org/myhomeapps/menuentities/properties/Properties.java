package org.myhomeapps.menuentities.properties;

import lombok.Getter;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Properties propertiesForComparison = (Properties) o;
        return properties.size()== propertiesForComparison.getProperties().size() &&
                properties.stream().filter(property -> !propertiesForComparison.getProperties().contains(property)).count() == 0;
    }
}
