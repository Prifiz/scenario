package org.myhomeapps.menuentities;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collections;

public class DefaultMacrosParserTest {

    @Test
    public void createMacroTest() {
        DefaultMacrosParser parser = new DefaultMacrosParser();
        Macro actual = parser.createMacro("home");
        Assert.assertEquals(actual.getClass(), HomeMacro.class);
    }
}
