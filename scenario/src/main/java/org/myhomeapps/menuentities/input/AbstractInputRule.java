package org.myhomeapps.menuentities.input;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractInputRule extends InputRule {

    private static final Logger logger = LogManager.getLogger(AbstractInputRule.class);

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
            logger.error(getErrorMessage());
            return false;
        }
    }

    public abstract boolean isPassed(String input);
}
