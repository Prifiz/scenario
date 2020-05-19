package org.myhomeapps.menuentities.input;

import org.testng.Assert;
import org.testng.annotations.Test;

public class IsNumberTest {

    @Test
    public void isPassedTest() {
        IsNumber isNumber = new IsNumber();
        final String input = "10";
        Assert.assertTrue(isNumber.isPassed(input));
    }

    @Test
    public void isNotPassedTest() {
        IsNumber isNumber = new IsNumber();
        boolean isPassed = isNumber.isPassed("ten");
        Assert.assertFalse(isPassed);
    }
}
