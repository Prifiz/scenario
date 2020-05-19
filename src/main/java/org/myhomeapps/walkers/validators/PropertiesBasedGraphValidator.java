package org.myhomeapps.walkers.validators;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.myhomeapps.menuentities.properties.PropertiesParser;
import org.myhomeapps.menuentities.MenuFrame;

public abstract class PropertiesBasedGraphValidator<V extends MenuFrame, E extends DefaultEdge>
        extends AbstractValidator<V, E> {

    protected PropertiesParser propertiesParser;

    public PropertiesBasedGraphValidator(PropertiesParser propertiesParser, Graph<V, E> graph) {
        super(graph);
        this.propertiesParser = propertiesParser;
    }
}
