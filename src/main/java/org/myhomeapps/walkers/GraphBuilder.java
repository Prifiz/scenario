package org.myhomeapps.walkers;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.myhomeapps.menuentities.MenuFrame;
import org.myhomeapps.menuentities.MenuItem;
import org.myhomeapps.menuentities.MenuSystem;

public interface GraphBuilder {
    Graph<? extends MenuFrame, DefaultEdge> buildFramesGraph(MenuSystem menuSystem);
    Graph<? extends MenuItem, DefaultEdge> buildItemsGraph(MenuSystem menuSystem);
}
