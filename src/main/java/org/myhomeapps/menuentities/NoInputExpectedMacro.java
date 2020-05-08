package org.myhomeapps.menuentities;

import java.util.Collection;
import java.util.Collections;

public class NoInputExpectedMacro implements Macro {
    @Override
    public Collection<String> getDisplayNames() {
        return Collections.singletonList("noInput");
    }
}
