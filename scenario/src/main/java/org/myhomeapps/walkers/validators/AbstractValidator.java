package org.myhomeapps.walkers.validators;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.myhomeapps.menuentities.MenuFrame;

import java.util.Collection;

public abstract class AbstractValidator<V extends MenuFrame, E extends DefaultEdge> {

    protected Graph<V, E> graph;

    public AbstractValidator(Graph<V, E> graph) {
        this.graph = graph;
    }

    protected abstract Collection<String> findOccurrences();

    protected abstract String getDisplayName();

    public GraphIssue validate() {
        return new GraphIssue(getDisplayName(), findOccurrences());
    }

}
