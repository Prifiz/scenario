package org.myhomeapps.walkers.validators;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.myhomeapps.menuentities.MenuFrame;
import org.myhomeapps.menuentities.MenuItem;
import org.myhomeapps.walkers.GraphIssue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DuplicatesFinderValidator implements GraphValidator<MenuFrame, DefaultEdge> {

    @Override
    public Collection<GraphIssue> validate(Graph<MenuFrame, DefaultEdge> graph)  {
        List<String> duplicated = findDuplicatedItems(graph);
        return duplicated.isEmpty() ? Collections.emptyList() : Collections.singletonList(
                new GraphIssue("Duplicated Items", duplicated));
    }

    private List<String> findDuplicatedItems(Graph<MenuFrame, DefaultEdge> graph) {
        List<String> result = new ArrayList<>();
        graph.vertexSet().forEach(frame -> result.addAll(
                frame.findDuplicates().stream()
                        .map(MenuItem::getName)
                        .collect(Collectors.toList())));
        return result;
    }
}
