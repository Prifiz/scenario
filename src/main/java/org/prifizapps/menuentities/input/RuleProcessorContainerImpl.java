package org.prifizapps.menuentities.input;

import org.apache.commons.lang3.StringUtils;

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
    public AbstractInputRule find(InputRule declaredRule) { // TODO unit testing!
        for(AbstractInputRule rule : ruleProcessors) {
            if(rule.getRuleDefName().equals(declaredRule.getRule())) {
                if(StringUtils.isNotBlank(declaredRule.getErrorMessage())) {
                    rule.setCustomErrorMessage(declaredRule.getErrorMessage());
                }
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
