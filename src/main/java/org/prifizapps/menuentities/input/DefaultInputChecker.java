package org.prifizapps.menuentities.input;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

public class DefaultInputChecker extends AbstractInputChecker {

    public DefaultInputChecker(PrintStream printStream) {
        super(printStream);
    }

    public Set<AbstractInputRule> initDefaultRules() {
        Set<AbstractInputRule> result = new HashSet<>();
        result.add(new IsNumber());
        return result;
    }

}
