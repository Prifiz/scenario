package org.myhomeapps.walkers.validators;

import org.jgrapht.graph.DefaultEdge;
import org.myhomeapps.menuentities.properties.PropertiesParser;
import org.myhomeapps.menuentities.MenuFrame;

public abstract class PropertiesBasedGraphValidator<V extends MenuFrame> implements GraphValidator<V, DefaultEdge> {

    protected PropertiesParser propertiesParser;

    public PropertiesBasedGraphValidator(PropertiesParser propertiesParser) {
        this.propertiesParser = propertiesParser;
    }
}
