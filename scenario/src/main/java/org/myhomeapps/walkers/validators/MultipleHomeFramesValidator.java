package org.myhomeapps.walkers.validators;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.myhomeapps.menuentities.properties.PropertiesParser;
import org.myhomeapps.menuentities.MenuFrame;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MultipleHomeFramesValidator<V extends MenuFrame, E extends DefaultEdge>
        extends PropertiesBasedGraphValidator<V, E> {

    public MultipleHomeFramesValidator(Graph<V, E> graph, PropertiesParser propertiesParser) {
        super(propertiesParser, graph);
    }

    protected List<String> getHomeFramesOccurrences() {
        return graph.vertexSet().stream()
                .filter(frame -> propertiesParser.parseProperties(frame.getProperties()).containsHome())
                .map(MenuFrame::getName)
                .collect(Collectors.toList());
    }

    @Override
    protected Collection<String> findOccurrences() {
        final int MAX_ALLOWED_HOME_FRAMES = 1;
        List<String> homeFramesOccurrences = getHomeFramesOccurrences();
        return homeFramesOccurrences.size() > MAX_ALLOWED_HOME_FRAMES ? homeFramesOccurrences : Collections.emptyList();
    }

    @Override
    protected String getDisplayName() {
        return "Multiple Home Frames";
    }
}
