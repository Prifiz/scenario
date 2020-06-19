package org.prifizapps.menuentities.properties;

import java.util.Collection;

public interface PropertiesParser {
    Properties parseProperties(Collection<String> properties);
    void registerCustomProperty(Property property);
}
