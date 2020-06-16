package org.myhomeapps.walkers.validators;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.myhomeapps.menuentities.MenuFrame;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Validator to find menus with equal names.
 * Prevents the case when {@link MenuFrame} goto points to uncertain menu.
 */
public class DuplicatedFramesValidator<V extends MenuFrame, E extends DefaultEdge> extends AbstractValidator<V, E> {

    public DuplicatedFramesValidator(Graph<V, E> graph) {
        super(graph);
    }

    /**
     * Finds names of menu which occurs more than once in menu graph.
     * @return {@link List} of duplicated menu names.
     */
    @Override
    protected List<String> findOccurrences() {
        List<String> framesNames = graph.vertexSet().stream()
                .map(MenuFrame::getName)
                .collect(Collectors.toList());
        return framesNames.stream()
                .filter(name -> Collections.frequency(framesNames, name) > 1)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    protected String getDisplayName() {
        return "Duplicated frames";
    }
}
