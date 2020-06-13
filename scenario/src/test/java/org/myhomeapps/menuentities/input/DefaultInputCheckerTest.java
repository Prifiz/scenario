package org.myhomeapps.menuentities.input;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

public class DefaultInputCheckerTest {

//    @Test
//    public void constructorTest() {
//        AbstractInputChecker inputChecker = new DefaultInputChecker();
//        AbstractInputRule isNumber = new IsNumber();
//        List<AbstractInputRule> expected = Collections.singletonList(isNumber);
//        Assert.assertArrayEquals(expected.toArray(), inputChecker.getRules().toArray());
//    }
//
//    private final AbstractInputRule testRule = new AbstractInputRule() {
//        @Override
//        public String getErrorMessage() {
//            return "";
//        }
//
//        @Override
//        public boolean isPassed(String input) {
//            return false;
//        }
//    };
//
//    @Test
//    public void registerCustomProcessorsTest() {
//        AbstractInputChecker inputChecker = new DefaultInputChecker();
//    }
//
//    @Test
//    public void addRuleTest() {
//        InputChecker inputChecker = new DefaultInputChecker(rulesProcessors);
//        inputChecker.addRule(testRule);
//        AbstractInputRule[] expected = {testRule};
//        Assert.assertArrayEquals(expected, inputChecker.getRules().toArray());
//    }
//
//    @Test
//    public void removeRuleTest() {
//        InputChecker inputChecker = new DefaultInputChecker(rulesProcessors);
//        inputChecker.addRule(testRule);
//        AbstractInputRule[] expected = {testRule};
//        Assert.assertArrayEquals(expected, inputChecker.getRules().toArray());
//        inputChecker.removeRule(testRule);
//        Assert.assertTrue(inputChecker.getRules().isEmpty());
//    }
//
//    @Test
//    public void isInputCorrectEmptyInputRulesTest() {
//        InputChecker inputChecker = new DefaultInputChecker(rulesProcessors);
//        Assert.assertTrue(inputChecker.isInputCorrect(""));
//    }
//
//    AbstractInputRule failingRule = new AbstractInputRule() {
//        @Override
//        public String getErrorMessage() {
//            return "";
//        }
//
//        @Override
//        public boolean isPassed(String input) {
//            return false;
//        }
//    };
//
//    AbstractInputRule firstPassingRule = new AbstractInputRule() {
//        @Override
//        public String getErrorMessage() {
//            return "";
//        }
//
//        @Override
//        public boolean isPassed(String input) {
//            return true;
//        }
//    };
//
//    AbstractInputRule secondPassingRule = new AbstractInputRule() {
//        @Override
//        public String getErrorMessage() {
//            return "";
//        }
//
//        @Override
//        public boolean isPassed(String input) {
//            return true;
//        }
//    };
//
//    @Test
//    public void isInputCorrectFailedRuleFoundTest() {
//        InputChecker inputChecker = new DefaultInputChecker(rulesProcessors);
//        inputChecker.addRule(failingRule);
//        inputChecker.addRule(firstPassingRule);
//        Assert.assertFalse(inputChecker.isInputCorrect(""));
//    }
//
//    @Test
//    public void isInputCorrectPassedTest() {
//        InputChecker inputChecker = new DefaultInputChecker(rulesProcessors);
//        inputChecker.addRule(firstPassingRule);
//        inputChecker.addRule(secondPassingRule);
//        Assert.assertTrue(inputChecker.isInputCorrect(""));
//    }
}
