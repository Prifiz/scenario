package org.myhomeapps.walkers.validators;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.myhomeapps.menuentities.MenuFrame;
import org.myhomeapps.menuentities.MenuItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class DuplicatedItemsValidator<V extends MenuFrame, E extends DefaultEdge> extends AbstractValidator<V, E> {

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

    @Override
    protected Collection<String> findOccurrences() {
        return findDuplicatedItems(graph);
    }

    @Override
    protected String getDisplayName() {
        return "Duplicated Items";
    }
}
