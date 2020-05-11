package org.myhomeapps.walkers.validators;

import org.apache.commons.lang3.StringUtils;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.myhomeapps.menuentities.MenuFrame;
import org.myhomeapps.walkers.GraphIssue;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FramesWithoutTextValidator implements GraphValidator<MenuFrame, DefaultEdge> {

    @Override
    public Collection<GraphIssue> validate(Graph<MenuFrame, DefaultEdge> graph) {
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
