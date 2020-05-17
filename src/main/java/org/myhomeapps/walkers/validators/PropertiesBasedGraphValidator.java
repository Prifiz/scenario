package org.myhomeapps.walkers.validators;

import org.jgrapht.graph.DefaultEdge;
import org.myhomeapps.menuentities.MacrosParser;
import org.myhomeapps.menuentities.MenuFrame;

public abstract class PropertiesBasedGraphValidator implements GraphValidator<MenuFrame, DefaultEdge> {

    protected MacrosParser macrosParser;

    public PropertiesBasedGraphValidator(MacrosParser macrosParser) {
        this.macrosParser = macrosParser;
    }
}
