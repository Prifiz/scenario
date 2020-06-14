package org.myhomeapps.menuentities.input;

import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

public class DefaultInputCheckerTest {

    @Test
    public void initDefaultRulesTest() {
        AbstractInputChecker inputChecker = new DefaultInputChecker();
        AbstractInputRule isNumber = new IsNumber();
        List<AbstractInputRule> expected = Collections.singletonList(isNumber);
        Assert.assertArrayEquals(expected.toArray(), inputChecker.initDefaultRules().toArray());
    }
}
