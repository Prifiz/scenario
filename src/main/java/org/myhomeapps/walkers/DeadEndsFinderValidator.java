package org.myhomeapps.walkers;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.myhomeapps.menuentities.MenuFrame;

import java.util.Collection;
import java.util.Collections;

public class DeadEndsFinderValidator implements GraphValidator<MenuFrame, DefaultEdge> {

    @Override
    public Collection<GraphIssue> validate(Graph<MenuFrame, DefaultEdge> graph) {
        return Collections.emptyList();
    }
}
