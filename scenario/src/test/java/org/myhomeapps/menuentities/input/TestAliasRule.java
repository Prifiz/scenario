package org.myhomeapps.menuentities.input;

@InputCheckingRule
public class TestAliasRule extends AbstractInputRule {

    public TestAliasRule(String customErrorMessage) {
        super(customErrorMessage);
    }

    @Override
    public String getErrorMessage() {
        return "";
    }

    @Override
    public boolean isPassed(String input) {
        return true;
    }
}
