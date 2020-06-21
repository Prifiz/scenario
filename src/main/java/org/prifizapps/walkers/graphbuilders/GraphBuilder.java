package org.prifizapps.walkers.graphbuilders;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.prifizapps.menuentities.MenuFrame;
import org.prifizapps.menuentities.properties.PropertiesParser;

public interface GraphBuilder {
    Graph<MenuFrame, DefaultEdge> buildFramesGraph(PropertiesParser propertiesParser);
}
