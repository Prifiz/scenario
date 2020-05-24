package org.myhomeapps.walkers.validators;

import org.apache.commons.lang3.StringUtils;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.myhomeapps.menuentities.MenuFrame;

import java.util.Collection;
import java.util.stream.Collectors;

public class FramesWithoutTextValidator<V extends MenuFrame, E extends DefaultEdge> extends AbstractValidator<V, E> {

    public FramesWithoutTextValidator(Graph<V, E> graph) {
        super(graph);
    }

    @Override
    protected Collection<String> findOccurrences() {
        return graph.vertexSet().stream()
                .filter(MenuFrame::isInputExpected)
                .filter(frame -> StringUtils.isBlank(frame.getText()))
                .map(MenuFrame::getName)
                .collect(Collectors.toList());
    }

    @Override
    protected String getDisplayName() {
        return "No Text In Frame";
    }
}
