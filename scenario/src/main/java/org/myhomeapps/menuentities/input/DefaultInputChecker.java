package org.myhomeapps.menuentities.input;

import java.util.HashSet;
import java.util.Set;

public class DefaultInputChecker extends AbstractInputChecker {

    public Set<AbstractInputRule> initDefaultRules() {
        Set<AbstractInputRule> result = new HashSet<>();
        result.add(new IsNumber());
        return result;
    }

}
