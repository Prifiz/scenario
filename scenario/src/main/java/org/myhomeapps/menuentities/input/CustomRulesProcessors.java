package org.myhomeapps.menuentities.input;

import lombok.Getter;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class CustomRulesProcessors {

    @Getter
    private final Set<AbstractInputRule> rulesProcessors = new LinkedHashSet<>();

    public CustomRulesProcessors(AbstractInputRule... customProcessors) {
        rulesProcessors.addAll(Arrays.asList(customProcessors));
    }
}
