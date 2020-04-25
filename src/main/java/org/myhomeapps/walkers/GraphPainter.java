package org.myhomeapps.walkers;

import org.jgrapht.Graph;

public interface GraphPainter<V, E> {

    void paint(Graph<V, E> graph, String filePath);
}
