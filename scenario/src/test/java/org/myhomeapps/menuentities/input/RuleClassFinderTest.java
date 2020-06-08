package org.myhomeapps.menuentities.input;

import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

public class RuleClassFinderTest {

    @InputCheckingRule
    public class CorrectRule extends AbstractInputRule {

        public CorrectRule(String customErrorMessage) {
            super(customErrorMessage);
        }

        @Override
        public String getErrorMessage() {
            return null;
        }

        @Override
        public boolean isPassed(String input) {
            return false;
        }
    }

    @Test(timeout = 1000)
    public void findPositiveTest() {
        AnnotatedClassFinder ruleFinder = new RuleClassFinder();
        Set<Class<?>> actual = ruleFinder.find();
        Assert.assertTrue(actual.contains(CorrectRule.class));
    }

    private class RuleWithoutAnnotation extends AbstractInputRule {


        public RuleWithoutAnnotation(String customErrorMessage) {
            super(customErrorMessage);
        }

        @Override
        public String getErrorMessage() {
            return null;
        }

        @Override
        public boolean isPassed(String input) {
            return false;
        }
    }

    @Test(timeout = 1000)
    public void findRuleWithoutAnnotationTest() {
        AnnotatedClassFinder ruleFinder = new RuleClassFinder();
        Set<Class<?>> actual = ruleFinder.find();
        Assert.assertFalse(actual.contains(RuleWithoutAnnotation.class));
    }

    @InputCheckingRule
    private class RuleNotExtendingAbstractInputRule {

    }

    @Test(timeout = 1000)
    public void findRuleNotExtendingAbstractRuleTest() {
        AnnotatedClassFinder ruleFinder = new RuleClassFinder();
        Set<Class<?>> actual = ruleFinder.find();
        Assert.assertFalse(actual.contains(RuleNotExtendingAbstractInputRule.class));
    }

    @InputCheckingRule
    private class NestedSuperClass extends AbstractInputRule {


        public NestedSuperClass(String customErrorMessage) {
            super(customErrorMessage);
        }

        @Override
        public String getErrorMessage() {
            return null;
        }

        @Override
        public boolean isPassed(String input) {
            return false;
        }
    }

    @InputCheckingRule
    private class Nested extends NestedSuperClass {

        public Nested(String customErrorMessage) {
            super(customErrorMessage);
        }
    }

    @Test
    public void findNestedInheritedRuleTest() {
        AnnotatedClassFinder ruleFinder = new RuleClassFinder();
        Set<Class<?>> actual = ruleFinder.find();
        Assert.assertTrue(actual.contains(Nested.class));
    }
}
