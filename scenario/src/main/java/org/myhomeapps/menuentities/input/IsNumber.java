package org.myhomeapps.menuentities.input;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@InputCheckingRule
public class IsNumber extends AbstractInputRule {

    Logger logger = LogManager.getLogger(IsNumber.class);

    protected static final String INCORRECT_INPUT_MSG = "Couldn't parse input";

    public IsNumber(String customErrorMessage) {
        super(customErrorMessage);
    }

    @Override
    public String getErrorMessage() {
        return "Not a number";
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
