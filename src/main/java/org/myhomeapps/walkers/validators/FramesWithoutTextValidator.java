package org.myhomeapps.walkers.validators;

import org.apache.commons.lang3.StringUtils;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.myhomeapps.menuentities.MenuFrame;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FramesWithoutTextValidator<V extends MenuFrame> implements GraphValidator<V, DefaultEdge> {

    @Override
    public Collection<GraphIssue> validate(Graph<V, DefaultEdge> graph) {
        List<String> occurrences = graph.vertexSet().stream()
                .filter(MenuFrame::isInputExpected)
                .filter(frame -> StringUtils.isBlank(frame.getText()))
                .map(MenuFrame::getName)
                .collect(Collectors.toList());
        if(occurrences.isEmpty()) {
            return Collections.emptyList();
        } else {
            return Collections.singletonList(new GraphIssue("No Text In Frame", occurrences));
        }
    }
}
