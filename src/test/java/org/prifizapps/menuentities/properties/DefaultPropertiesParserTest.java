package org.prifizapps.menuentities.properties;

import mockit.Tested;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DefaultPropertiesParserTest {

    @Tested DefaultPropertiesParser parser;

    @Test
    public void createHomePropertyTest() {
        Property homeProperty = parser.createProperty("home");
        Property kudoProperty = parser.createProperty("kudo");
        Assert.assertEquals(homeProperty.getClass(), HomeProperty.class);
        Assert.assertEquals(kudoProperty.getClass(), HomeProperty.class);
    }

    @Test
    public void createExitPropertyTest() {
        Property actual = parser.createProperty("exit");
        Assert.assertEquals(actual.getClass(), ExitProperty.class);
    }

    @Test
    public void createNoInputPropertyTest() {
        Property actual = parser.createProperty("noInput");
        Assert.assertEquals(actual.getClass(), NoInputExpectedProperty.class);
    }

    @Test
    public void createUnknownPropertyTest() {
        Property actual = parser.createProperty("someUnknownProperty");
        Assert.assertEquals(actual.getClass(), UnknownProperty.class);
    }

    @Test
    public void parseNullPropertiesTest(){
        Assert.assertEquals(new Properties(new ArrayList<>()),parser.parseProperties(null));
    }


    @Test
    public void parsePropertiesTest(){
        final String incorrectPropertyText = "testProperty";
        Properties actualProperties = parser.parseProperties(new ArrayList<>(Arrays.asList(" exit","home  ",incorrectPropertyText,"noInput")));
        List<Property> propObjs = new ArrayList<>();
        propObjs.add(new ExitProperty());
        propObjs.add(new HomeProperty());
        propObjs.add(new NoInputExpectedProperty());
        propObjs.add(new UnknownProperty(incorrectPropertyText));
        Properties expectedProperties = new Properties(propObjs);
        Assert.assertEquals(actualProperties, expectedProperties);
    }

}
