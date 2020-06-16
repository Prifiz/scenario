package org.prifizapps.walkers.validators;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.prifizapps.menuentities.MenuFrame;
import org.prifizapps.menuentities.properties.PropertiesParser;

/**
 * Common ancestor for all validators requiring the {@link MenuFrame} properties values.
 */
public abstract class PropertiesBasedGraphValidator<V extends MenuFrame, E extends DefaultEdge>
        extends AbstractValidator<V, E> {

    protected PropertiesParser propertiesParser;

    /**
     * Creates new validator object which uses {@link MenuFrame} properties while searching for violations.
     * @param propertiesParser {@link PropertiesParser} object which should parse {@link String} properties
     *                                                 to special objects
     * @param graph menu states graph represented by {@link Graph} object to validate.
     */
    public PropertiesBasedGraphValidator(PropertiesParser propertiesParser, Graph<V, E> graph) {
        super(graph);
        this.propertiesParser = propertiesParser;
    }
}
