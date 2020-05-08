package org.myhomeapps.menuentities;

import java.util.Collection;
import java.util.Collections;

public class ExitMacro implements Macro {

    public Collection<String> getDisplayNames() {
        return Collections.singletonList("exit");
    }
}
