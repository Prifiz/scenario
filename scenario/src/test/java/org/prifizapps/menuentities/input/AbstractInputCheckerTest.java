package org.prifizapps.menuentities.input;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.util.*;

import static org.prifizapps.menuentities.input.AbstractInputChecker.INCORRECT_RULE_MSG;


public class AbstractInputCheckerTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    private final AbstractInputRule defaultRule = new AbstractInputRule() {
        @Override
        public String getErrorMessage() {
            return null;
        }

        @Override
        public String getRuleDefName() {
            return "defaultRule";
        }

        @Override
        public boolean isPassed(String input) {
            return true;
        }
    };

    private class TestInputChecker extends AbstractInputChecker {
        @Override
        protected Set<AbstractInputRule> initDefaultRules() {
            Set<AbstractInputRule> defaultRules = new HashSet<>();
            defaultRules.add(defaultRule);
            return defaultRules;
        }
    }

    @Test
    public void defaultRulesTest() {
        TestInputChecker testInputChecker = new TestInputChecker();
        Set<AbstractInputRule> expectedDefaultRules = new HashSet<>();
        expectedDefaultRules.add(defaultRule);
        RuleProcessorContainer defaultRuleContainer = new RuleProcessorContainerImpl(expectedDefaultRules);
        Assert.assertEquals(defaultRuleContainer, testInputChecker.getDefaultRules());
    }

    @Test
    public void initCustomRulesTest() {
        AbstractInputRule customRule = new AbstractInputRule() {
            @Override
            public String getErrorMessage() {
                return null;
            }

            @Override
            public String getRuleDefName() {
                return "customRule";
            }

            @Override
            public boolean isPassed(String input) {
                return false;
            }
        };
        TestInputChecker testInputChecker = new TestInputChecker();
        testInputChecker.initCustomRules(customRule);
        Set<AbstractInputRule> expectedCustomRules = new HashSet<>();
        expectedCustomRules.add(customRule);
        RuleProcessorContainer customRuleContainer = new RuleProcessorContainerImpl(expectedCustomRules);
        Assert.assertEquals(customRuleContainer, testInputChecker.getCustomRules());
    }

    @Test
    public void isInputCorrectNoDeclaredRulesTest() throws IOException {
        TestInputChecker testInputChecker = new TestInputChecker();
        Assert.assertTrue(testInputChecker.isInputCorrect(Collections.emptyList(), "input"));
    }

    @Test
    public void incorrectRuleDeclarationTest() throws IOException {
        AbstractInputChecker testInputChecker = new AbstractInputChecker() {
            @Override
            protected Set<AbstractInputRule> initDefaultRules() {
                return new HashSet<>();
            }
        };
        List<InputRule> declaredRules = new ArrayList<>();
        final String ruleDeclaredName = "incorrectRule";
        InputRule incorrectRule = new InputRule(ruleDeclaredName, "");
        declaredRules.add(incorrectRule);
        expectedEx.expect(IOException.class);
        expectedEx.expectMessage(INCORRECT_RULE_MSG + ruleDeclaredName);
        testInputChecker.isInputCorrect(declaredRules, "input");
    }

    @Test
    public void defaultRuleFailedTest() throws IOException {
        final String ruleName = "failingDefaultRule";
        AbstractInputRule failingDefaultRule = new AbstractInputRule() {
            @Override
            public String getErrorMessage() {
                return "";
            }

            @Override
            public String getRuleDefName() {
                return ruleName;
            }

            @Override
            public boolean isPassed(String input) {
                return false;
            }
        };
        AbstractInputChecker testInputChecker = new AbstractInputChecker() {
            @Override
            protected Set<AbstractInputRule> initDefaultRules() {
                Set<AbstractInputRule> result = new HashSet<>();
                result.add(failingDefaultRule);
                return result;
            }
        };
        List<InputRule> declaredRules = new ArrayList<>();
        InputRule incorrectRule = new InputRule(ruleName, "");
        declaredRules.add(incorrectRule);
        Assert.assertFalse(testInputChecker.isInputCorrect(declaredRules, ""));
    }

    @Test
    public void customRuleFailedTest() throws IOException {
        final String defaultRule = "passingDefaultRule";
        final String customRule = "failingCustomRule";
        AbstractInputRule passingDefaultRule = new AbstractInputRule() {
            @Override
            public String getErrorMessage() {
                return "";
            }

            @Override
            public String getRuleDefName() {
                return defaultRule;
            }

            @Override
            public boolean isPassed(String input) {
                return true;
            }
        };

        AbstractInputRule failingCustomRule = new AbstractInputRule() {
            @Override
            public String getErrorMessage() {
                return "";
            }

            @Override
            public String getRuleDefName() {
                return customRule;
            }

            @Override
            public boolean isPassed(String input) {
                return false;
            }
        };
        AbstractInputChecker testInputChecker = new AbstractInputChecker() {
            @Override
            protected Set<AbstractInputRule> initDefaultRules() {
                Set<AbstractInputRule> result = new HashSet<>();
                result.add(passingDefaultRule);
                return result;
            }
        };
        testInputChecker.initCustomRules(failingCustomRule);
        List<InputRule> declaredRules = new ArrayList<>();
        InputRule passingRule = new InputRule(defaultRule, "");
        InputRule failingRule = new InputRule(customRule, "");
        declaredRules.add(passingRule);
        declaredRules.add(failingRule);
        Assert.assertFalse(testInputChecker.isInputCorrect(declaredRules, ""));
    }

    @Test
    public void passingRulesTest() throws IOException {
        final String defaultRule = "passingDefaultRule";
        final String customRule = "passingCustomRule";
        AbstractInputRule passingDefaultRule = new AbstractInputRule() {
            @Override
            public String getErrorMessage() {
                return "";
            }

            @Override
            public String getRuleDefName() {
                return defaultRule;
            }

            @Override
            public boolean isPassed(String input) {
                return true;
            }
        };

        AbstractInputRule passingCustomRule = new AbstractInputRule() {
            @Override
            public String getErrorMessage() {
                return "";
            }

            @Override
            public String getRuleDefName() {
                return customRule;
            }

            @Override
            public boolean isPassed(String input) {
                return true;
            }
        };
        AbstractInputChecker testInputChecker = new AbstractInputChecker() {
            @Override
            protected Set<AbstractInputRule> initDefaultRules() {
                Set<AbstractInputRule> result = new HashSet<>();
                result.add(passingDefaultRule);
                return result;
            }
        };
        testInputChecker.initCustomRules(passingCustomRule);
        List<InputRule> declaredRules = new ArrayList<>();
        InputRule passingRule = new InputRule(defaultRule, "");
        InputRule failingRule = new InputRule(customRule, "");
        declaredRules.add(passingRule);
        declaredRules.add(failingRule);
        Assert.assertTrue(testInputChecker.isInputCorrect(declaredRules, ""));
    }
}
