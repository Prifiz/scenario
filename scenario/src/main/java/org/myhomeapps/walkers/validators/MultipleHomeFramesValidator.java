package org.myhomeapps.walkers.validators;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.myhomeapps.menuentities.MenuFrame;
import org.myhomeapps.menuentities.properties.PropertiesParser;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Validator to discover the case when menu system has multiple declared home menus.
 * Prevents the case when there's no certain starting point for iterating the menu graph.
 */
public class MultipleHomeFramesValidator<V extends MenuFrame, E extends DefaultEdge>
        extends PropertiesBasedGraphValidator<V, E> {

    public MultipleHomeFramesValidator(Graph<V, E> graph, PropertiesParser propertiesParser) {
        super(propertiesParser, graph);
    }

    /**
     * Finds names of menus with home property if there are more than 1 such menus.
     * @return {@link List} of menu names.
     */
    @Override
    protected List<String> findOccurrences() {
        final int MAX_ALLOWED_HOME_FRAMES = 1;
        List<String> homeFramesOccurrences = getHomeFramesOccurrences();
        return homeFramesOccurrences.size() > MAX_ALLOWED_HOME_FRAMES ? homeFramesOccurrences : Collections.emptyList();
    }

    private List<String> getHomeFramesOccurrences() {
        return graph.vertexSet().stream()
                .filter(frame -> propertiesParser.parseProperties(frame.getProperties()).containsHome())
                .map(MenuFrame::getName)
                .collect(Collectors.toList());
    }

    @Override
    protected String getDisplayName() {
        return "Multiple Home Frames";
    }
}
