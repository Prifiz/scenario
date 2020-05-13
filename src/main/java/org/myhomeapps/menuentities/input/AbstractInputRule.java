package org.myhomeapps.menuentities.input;

public abstract class AbstractInputRule implements Rule {

    private String displayName;
    private String errorMessage;

    public AbstractInputRule(String displayName, String errorMessage) {
        this.displayName = displayName;
        this.errorMessage = errorMessage;
    }
}
