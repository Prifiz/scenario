package org.myhomeapps.menuentities.properties;

import java.util.Collection;
import java.util.Collections;

public class NoInputExpectedProperty implements Property {
    @Override
    public Collection<String> getDisplayNames() {
        return Collections.singletonList("noInput");
    }
}
