package org.myhomeapps.menuentities.input;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DefaultInputCheckerTest {

    @Test
    public void isInputCorrectEmptyInputRulesTest() {
        InputChecker inputChecker = new DefaultInputChecker(Collections::emptySet);
        Assert.assertTrue(inputChecker.isInputCorrect(Collections.emptyList(), ""));
    }

    @Test
    public void isInputCorrectFailedRuleFoundTest() {
        InputChecker inputChecker = new DefaultInputChecker(new RuleClassFinder());
        List<InputRule> declaredRules = Arrays.asList(
                new InputRule("@IsNumber", ""),
                new InputRule("@TestAlias", "")
        );
        Assert.assertFalse(inputChecker.isInputCorrect(declaredRules, "abc"));
    }

    @Test
    public void isInputCorrectPassedTest() {
        InputChecker inputChecker = new DefaultInputChecker(new RuleClassFinder());
        List<InputRule> declaredRules = Arrays.asList(
                new InputRule("@IsNumber", ""),
                new InputRule("@TestAliasRule", "1")
        );
        Assert.assertTrue(inputChecker.isInputCorrect(declaredRules, "123"));
    }
}
