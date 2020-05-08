package org.myhomeapps.walkers.validators;

import org.jgrapht.Graph;
import org.myhomeapps.menuentities.MenuFrame;
import org.myhomeapps.walkers.GraphIssue;

import java.util.Collection;

public interface GraphValidator<V extends MenuFrame, E> {
    Collection<GraphIssue> validate(Graph<V, E> graph);

}
