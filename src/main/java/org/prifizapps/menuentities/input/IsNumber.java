package org.prifizapps.menuentities.input;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IsNumber extends AbstractInputRule {

    Logger logger = LogManager.getLogger(getClass());

    protected static final String INCORRECT_INPUT_MSG = "Couldn't parse input";

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
            logger.error(INCORRECT_INPUT_MSG);
            logger.error(ex.getMessage());
            return false;
        }
    }
}
