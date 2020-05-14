package org.myhomeapps.menuentities.input;

@InputCheckingRule
public class IsNumber extends AbstractInputRule {

    public IsNumber() {
        super("@IsNumber", "Not a number");
    }

    public IsNumber(String errorMessage) {
        super("@IsNumber", errorMessage);
    }

    @Override
    public boolean isPassed(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException ex) {
            System.out.println("Couldn't parse input");
            System.out.println(ex.getMessage());
            return false;
        }
    }
}
