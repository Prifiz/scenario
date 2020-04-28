package org.myhomeapps.walkers;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.myhomeapps.menuentities.MenuFrame;
import org.myhomeapps.menuentities.MenuItem;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class DefaultGraphValidator implements GraphValidator<MenuFrame, DefaultEdge> {

    private Map<String, List<String>> issues = new LinkedHashMap<>();

    @Override
    public void validate(Graph<MenuFrame, DefaultEdge> graph) throws IOException {
        issues.put("Duplicates", findDuplicatedItems(graph));

        if(hasIssues()) {
            throw new IOException("Issues found");
        }
    }

    @Override
    public Map<String, List<String>> getIssues() {
        return issues;
    }

    private boolean hasIssues() {
        return issues.values().stream().anyMatch(list -> !list.isEmpty());
    }

    private List<String> findDuplicatedItems(Graph<MenuFrame, DefaultEdge> graph) {
        List<String> result = new ArrayList<>();
        graph.vertexSet().forEach(frame -> {
            result.addAll(
                    frame.findDuplicates().stream()
                            .map(MenuItem::getName)
                            .collect(Collectors.toList()));
        });
        return result;
    }
}
