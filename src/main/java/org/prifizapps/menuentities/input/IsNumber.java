package org.prifizapps.menuentities.input;

public class IsNumber extends AbstractInputRule {

    public IsNumber(String customErrorMessage) {
        super(customErrorMessage);
    }

    public IsNumber() {
        super();
    }

    @Override
    public String getErrorMessage() {
        return "Not a number";
    }

    @Override
    public String getRuleDefName() {
        return "IsNumber";
    }

    @Override
    public boolean isPassed(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
