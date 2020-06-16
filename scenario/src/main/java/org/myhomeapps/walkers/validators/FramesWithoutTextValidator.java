package org.myhomeapps.walkers.validators;

import org.apache.commons.lang3.StringUtils;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.myhomeapps.menuentities.MenuFrame;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Validator to find menus without text to be printed to user.
 * Such menus can cause confusing behavior, e.g. waiting for user input without any prompting text.
 */
public class FramesWithoutTextValidator<V extends MenuFrame, E extends DefaultEdge> extends AbstractValidator<V, E> {

    public FramesWithoutTextValidator(Graph<V, E> graph) {
        super(graph);
    }

    /**
     * Gets the names of menus with no text.
     * @return the {@link List} of menu names with no text.
     */
    @Override
    protected List<String> findOccurrences() {
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
