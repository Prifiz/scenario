package org.myhomeapps.menuentities.properties;

import org.testng.Assert;
import org.testng.annotations.Test;

public class DefaultPropertiesParserTest {

    @Test
    public void createPropertyTest() {
        DefaultPropertiesParser parser = new DefaultPropertiesParser();
        Property actual = parser.createProperty("home");
        Assert.assertEquals(actual.getClass(), HomeProperty.class);
    }
}
