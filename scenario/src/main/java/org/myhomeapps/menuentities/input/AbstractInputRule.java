package org.myhomeapps.menuentities.input;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public abstract class AbstractInputRule {

    private static final Logger logger = LogManager.getLogger(AbstractInputRule.class);

//    @Getter
//    protected final String ruleAlias;
    @Getter
    protected String customErrorMessage;

//    public AbstractInputRule(String ruleAlias, String errorMessage) {
//        this.ruleAlias = ruleAlias;
//        this.errorMessage = errorMessage;
//    }

    public abstract String getErrorMessage();

    public AbstractInputRule() {
        this.customErrorMessage = "";
    }
//
    public AbstractInputRule(String customErrorMessage) {
        this.customErrorMessage = customErrorMessage;
    }

    public boolean checkRule(String input) {
        if(isPassed(input)) {
            return true;
        } else {
            if(StringUtils.isBlank(customErrorMessage)) {
                logger.error(this::getCustomErrorMessage);
            } else {
                logger.error(this::getErrorMessage);
            }
            return false;
        }
    }

    public abstract boolean isPassed(String input);

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractInputRule rule = (AbstractInputRule) o;
        return Objects.equals(customErrorMessage, rule.customErrorMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customErrorMessage);
    }
}
