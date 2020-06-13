package org.myhomeapps.menuentities.input;

import lombok.Getter;

import java.io.IOException;
import java.util.*;

@Getter
public abstract class AbstractInputChecker {

    protected final Set<AbstractInputRule> defaultRules;
    protected Set<AbstractInputRule> customRules = new LinkedHashSet<>();

    public AbstractInputChecker() {
        this.defaultRules = initDefaultRules();
    }

    public abstract Set<AbstractInputRule> initDefaultRules();

    public AbstractInputChecker initCustomRules(AbstractInputRule... customRules) {
        this.customRules.addAll(Arrays.asList(customRules));
        return this;
    }

    // all the rules checks should pass to return true
    public boolean isInputCorrect(Collection<InputRule> declaredRules, String userInput) throws IOException {
        if (declaredRules.isEmpty()) {
            return true;
        }

        for(InputRule declaredRule : declaredRules) {
            AbstractInputRule defaultRuleProcessor = findDefaultRuleProcessor(declaredRule);
            AbstractInputRule customRuleProcessor = findCustomRuleProcessor(declaredRule);

            if(defaultRuleProcessor == null && customRuleProcessor == null) {
                throw new IOException("No rule class found for rule declaration: " + declaredRule.getRule());
            }

            if (defaultRuleProcessor != null && !defaultRuleProcessor.checkRule(userInput)) {
                return false;
            }

            if (customRuleProcessor != null && !customRuleProcessor.checkRule(userInput)) {
                return false;
            }
        }
        return true;
    }


    private AbstractInputRule findDefaultRuleProcessor(InputRule declaredRule) {
        return findRuleProcessor(declaredRule, defaultRules);
    }

    private AbstractInputRule findCustomRuleProcessor(InputRule declaredRule) {
        return findRuleProcessor(declaredRule, customRules);
    }

    private AbstractInputRule findRuleProcessor(InputRule declaredRule, Collection<AbstractInputRule> rulesProcessors) {

        for(AbstractInputRule rule : rulesProcessors) {
            if(rule.getClass().getSimpleName().equals(declaredRule.getRule())) {
                return rule;
            }
        }
        return null;
        //
    }
}
