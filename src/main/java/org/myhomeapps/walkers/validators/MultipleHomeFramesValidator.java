package org.myhomeapps.walkers.validators;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.myhomeapps.menuentities.MacrosParser;
import org.myhomeapps.menuentities.MenuFrame;
import org.myhomeapps.walkers.GraphIssue;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MultipleHomeFramesValidator extends PropertiesBasedGraphValidator {


    public MultipleHomeFramesValidator(MacrosParser macrosParser) {
        super(macrosParser);
    }

    @Override
    public Collection<GraphIssue> validate(Graph<MenuFrame, DefaultEdge> graph) {
        final int MAX_ALLOWED_HOME_FRAMES = 1;
        List<String> homeFramesOccurrences = graph.vertexSet().stream()
                .filter(frame -> macrosParser.parseMacros(frame.getProperties()).containsHome())
                .map(MenuFrame::getName)
                .collect(Collectors.toList());

        if(homeFramesOccurrences.size() > MAX_ALLOWED_HOME_FRAMES) {
            return Collections.singletonList(new GraphIssue("Multiple Home Frames", homeFramesOccurrences));
        }
        return Collections.emptyList();
    }
}
