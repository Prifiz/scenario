package org.prifizapps.walkers.graphbuilders;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.prifizapps.menuentities.MenuFrame;

public interface GraphBuilder {
    Graph<MenuFrame, DefaultEdge> buildFramesGraph();
}
