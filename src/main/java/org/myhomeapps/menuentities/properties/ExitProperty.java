package org.myhomeapps.menuentities.properties;

import java.util.Collection;
import java.util.Collections;

public class ExitProperty implements Property {

    public Collection<String> getDisplayNames() {
        return Collections.singletonList("exit");
    }
}
