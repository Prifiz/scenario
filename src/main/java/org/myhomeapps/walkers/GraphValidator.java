package org.myhomeapps.walkers;

import org.jgrapht.Graph;
import org.myhomeapps.menuentities.MenuFrame;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface GraphValidator<V extends MenuFrame, E> {
    Collection<GraphIssue> validate(Graph<V, E> graph);

}
