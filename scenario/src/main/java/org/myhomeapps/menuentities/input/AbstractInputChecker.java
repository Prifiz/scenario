package org.myhomeapps.menuentities.input;

import lombok.Getter;
import lombok.NonNull;

import java.io.IOException;
import java.util.*;

@Getter
public abstract class AbstractInputChecker {

    protected final RuleProcessorContainer defaultRules;
    protected RuleProcessorContainer customRules = new RuleProcessorContainerImpl();
    static final String INCORRECT_RULE_MSG = "No rule class found for rule declaration: ";

    public AbstractInputChecker() {
        this.defaultRules = new RuleProcessorContainerImpl(initDefaultRules());
    }

    protected abstract Set<AbstractInputRule> initDefaultRules();

    public AbstractInputChecker initCustomRules(AbstractInputRule... customRules) {
        this.customRules.addRuleProcessors(Arrays.asList(customRules));
        return this;
    }

    // all the rules checks should pass to return true
    public boolean isInputCorrect(@NonNull Collection<InputRule> declaredRules, String userInput) throws IOException {
        if (declaredRules.isEmpty()) {
            return true;
        }

        for(InputRule declaredRule : declaredRules) {
            AbstractInputRule defaultRuleProcessor = defaultRules.find(declaredRule);
            AbstractInputRule customRuleProcessor = customRules.find(declaredRule);

            if(defaultRuleProcessor == null && customRuleProcessor == null) {
                throw new IOException(INCORRECT_RULE_MSG + declaredRule.getRule());
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
}
