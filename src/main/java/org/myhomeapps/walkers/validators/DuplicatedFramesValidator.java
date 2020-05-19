package org.myhomeapps.walkers.validators;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.myhomeapps.menuentities.MenuFrame;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class DuplicatedFramesValidator<V extends MenuFrame> implements GraphValidator<V, DefaultEdge> {
    @Override
    public Collection<GraphIssue> validate(Graph<V, DefaultEdge> graph) {
        List<String> framesNames = graph.vertexSet().stream()
                .map(MenuFrame::getName)
                .collect(Collectors.toList());
        List<String> occurrences = framesNames.stream()
                .filter(name -> Collections.frequency(framesNames, name) > 1)
                .distinct()
                .collect(Collectors.toList());
        if(occurrences.isEmpty()) {
            return Collections.emptyList();
        } else {
            return Collections.singletonList(new GraphIssue("Duplicated frames", occurrences));
        }
    }
}
