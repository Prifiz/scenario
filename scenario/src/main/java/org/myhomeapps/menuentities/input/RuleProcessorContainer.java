package org.myhomeapps.menuentities.input;

import java.util.Collection;

public interface RuleProcessorContainer {
    AbstractInputRule find(InputRule declaredRule);
    void addRuleProcessors(Collection<AbstractInputRule> ruleProcessors);
}
