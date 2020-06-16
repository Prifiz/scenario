package org.myhomeapps.walkers.validators;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.myhomeapps.menuentities.MenuFrame;
import org.myhomeapps.menuentities.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Validator to find menus containing items with the same names.
 * Prevents the case when some goto points to 2 or more menu items at the same time.
 */
public class DuplicatedItemsValidator<V extends MenuFrame, E extends DefaultEdge> extends AbstractValidator<V, E> {
    // fixme should the goto be checked too??

    public DuplicatedItemsValidator(Graph<V, E> graph) {
        super(graph);
    }

    private List<String> findDuplicatedItems(Graph<V, E> graph) {
        List<String> result = new ArrayList<>();
        graph.vertexSet().forEach(frame -> result.addAll(
                frame.findDuplicates().stream()
                        .map(MenuItem::getName)
                        .collect(Collectors.toList())));
        return result;
    }

    /**
     * Finds names of menus with duplicated {@link MenuItem} inside.
     * @return {@link List} of menu names.
     */
    @Override
    protected List<String> findOccurrences() {
        return findDuplicatedItems(graph);
    }

    @Override
    protected String getDisplayName() {
        return "Duplicated Items";
    }
}
