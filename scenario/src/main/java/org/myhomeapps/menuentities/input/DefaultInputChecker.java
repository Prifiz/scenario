package org.myhomeapps.menuentities.input;

import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Set;

public class DefaultInputChecker implements InputChecker {

    private final Set<Class<?>> ruleClasses;

    public DefaultInputChecker(AnnotatedClassFinder annotatedClassFinder) {
        this.ruleClasses = annotatedClassFinder.find();
    }

    @Override
    public boolean isInputCorrect(List<InputRule> inputRules, String userInput) {
        if (inputRules.isEmpty()) {
            return true;
        }
        return inputRules.stream()
                .map(this::initMatchingRule)
                .allMatch(rule -> rule.checkRule(userInput));
    }

    private AbstractInputRule initMatchingRule(InputRule inputRule) throws RuntimeException {
        for (Class<?> clazz : ruleClasses) {
            try {
                System.out.println("Checking class: " + clazz.getSimpleName());
                final String classNameToFind = inputRule.getRule().replace("@", "");
                System.out.println("Class to find: " + classNameToFind);
                if (clazz.getSimpleName().equals(classNameToFind)) {
                    Constructor<?> ruleConstructor = clazz.getConstructor(String.class);
                    return (AbstractInputRule) ruleConstructor.newInstance(inputRule.getErrorMessage());
                }
            } catch (Exception ex) {
                System.out.println("Check if rule is not declared as a nested class");
                throw new RuntimeException("Couldn't parse rule", ex);
            }
        }
        throw new RuntimeException("No such rule declared: " + inputRule.getRule());
    }
}
