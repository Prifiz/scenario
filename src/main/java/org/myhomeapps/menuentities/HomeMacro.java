package org.myhomeapps.menuentities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HomeMacro implements Macro {
    @Override
    public Collection<String> getDisplayNames() {
        List<String> homeValues = new ArrayList<>();
        homeValues.add("home");
        homeValues.add("kudo");
        return homeValues;
    }
}
