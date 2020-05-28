package org.myhomeapps.menuentities.properties;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HomeProperty implements Property {
    @Override
    public Collection<String> getDisplayNames() {
        List<String> homeValues = new ArrayList<>();
        homeValues.add("home");
        homeValues.add("kudo");
        return homeValues;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        return true;
    }
}
