package org.myhomeapps.menuentities.properties;

import java.util.Collection;
import java.util.Collections;

public class ExitProperty implements Property {

    public Collection<String> getDisplayNames() {
        return Collections.singletonList("exit");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return true;
    }

}
