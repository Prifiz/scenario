package org.myhomeapps.menuentities.input;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@InputCheckingRule
public class IsNumber extends AbstractInputRule {

    Logger logger = LogManager.getLogger(IsNumber.class);

    protected static final String INCORRECT_INPUT_MSG = "Couldn't parse input";

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
            logger.error(INCORRECT_INPUT_MSG);
            logger.error(ex.getMessage());
            return false;
        }
    }
}
