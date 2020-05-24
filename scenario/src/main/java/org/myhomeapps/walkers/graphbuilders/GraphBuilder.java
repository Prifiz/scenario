package org.myhomeapps.walkers.graphbuilders;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.myhomeapps.menuentities.MenuFrame;

public interface GraphBuilder {
    Graph<MenuFrame, DefaultEdge> buildFramesGraph();
}
