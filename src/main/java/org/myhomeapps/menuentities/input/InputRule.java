package org.myhomeapps.menuentities.input;

public class InputRule {

    protected String rule;
    protected String errorMessage;

    public InputRule(String rule, String errorMessage) {
        this.rule = rule;
        this.errorMessage = errorMessage;
    }

    public InputRule() {
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

}
