package org.prifizapps.menuentities.properties;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class DefaultPropertiesParser implements PropertiesParser {

    private Collection<Property> registeredProperties;

    public DefaultPropertiesParser() {
        this.registeredProperties = initInbuiltProperties();
    }

    public void registerCustomProperty(Property property) {
        this.registeredProperties.add(property);
    }

    private Collection<Property> initInbuiltProperties() {
        Set<Property> properties = new HashSet<>();
        properties.add(new HomeProperty());
        properties.add(new ExitProperty());
        properties.add(new NoInputExpectedProperty());
        return properties;
    }

    @Override
    public Properties parseProperties(Collection<String> properties) {
        if (properties == null) {
            return new Properties(Collections.emptyList());
        }
        return new Properties(properties.stream()
                .map(String::trim)
                .map(this::createProperty)
                .collect(Collectors.toList()));
    }

    protected Property createProperty(String parsedName) {
        for(Property property : registeredProperties) {
            for(String possibleDisplayName : property.getDisplayNames()) {
                if(possibleDisplayName.equalsIgnoreCase(parsedName)) {
                    return property;
                }
            }
        }
        return new UnknownProperty(parsedName);
    }
}
