package org.prifizapps.menuentities.properties;

import java.util.Collection;
import java.util.Collections;

public class NoInputExpectedProperty implements Property {
    @Override
    public Collection<String> getDisplayNames() {
        return Collections.singletonList("noInput");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return true;
    }
}
