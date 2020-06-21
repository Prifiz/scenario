package org.prifizapps.menuentities.input;

import lombok.Getter;
import lombok.NonNull;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;

@Getter
public abstract class AbstractInputChecker {

    protected final PrintStream printStream;
    protected final RuleProcessorContainer defaultRules;
    protected RuleProcessorContainer customRules = new RuleProcessorContainerImpl();
    static final String INCORRECT_RULE_MSG = "No rule class found for rule declaration: ";

    public AbstractInputChecker(PrintStream printStream) {
        this.printStream = printStream;
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

            if (defaultRuleProcessor != null && !defaultRuleProcessor.checkRule(userInput, printStream)) {
                return false;
            }

            if (customRuleProcessor != null && !customRuleProcessor.checkRule(userInput, printStream)) {
                return false;
            }
        }
        return true;
    }
}
