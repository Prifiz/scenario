package org.myhomeapps.menuentities.input;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class RuleProcessorContainerImpl implements RuleProcessorContainer {

    private final Set<AbstractInputRule> ruleProcessors;

    public RuleProcessorContainerImpl() {
        this.ruleProcessors = new LinkedHashSet<>();
    }

    public RuleProcessorContainerImpl(Set<AbstractInputRule> ruleProcessors) {
        this.ruleProcessors = ruleProcessors;
    }

    @Override
    public AbstractInputRule find(InputRule declaredRule) {
        for(AbstractInputRule rule : ruleProcessors) {
            if(rule.getRuleDefName().equals(declaredRule.getRule())) {
                return rule;
            }
        }
        return null;
    }

    @Override
    public void addRuleProcessors(Collection<AbstractInputRule> ruleProcessors) {
        this.ruleProcessors.addAll(ruleProcessors);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RuleProcessorContainerImpl that = (RuleProcessorContainerImpl) o;
        return Objects.equals(ruleProcessors, that.ruleProcessors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ruleProcessors);
    }
}
