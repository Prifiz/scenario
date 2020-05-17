package org.myhomeapps.menuentities.input;

public abstract class AbstractInputRule extends InputRule {
    public AbstractInputRule(String rule, String errorMessage) {
        super(rule, errorMessage);
    }

    public AbstractInputRule() {
    }

    public AbstractInputRule(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean checkRule(String input) {
        if(isPassed(input)) {
            return true;
        } else {
            System.out.println(getErrorMessage());
            return false;
        }
    }

    public abstract boolean isPassed(String input);
}
