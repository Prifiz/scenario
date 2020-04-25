package org.myhomeapps.walkers;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.traverse.AbstractGraphIterator;

import java.util.NoSuchElementException;
import java.util.Set;

public class PredefinedMenuOrderIterator<V, E>
        extends
        AbstractGraphIterator<V, E> {

    private boolean endReached = false;
    private V currentVertex;
    private V startVertex;

    public PredefinedMenuOrderIterator(Graph<V, E> graph, V startVertex) {
        super(graph);
        this.crossComponentTraversal = false;
        if (startVertex == null) {
            throw new IllegalArgumentException("Start vertex not specified");
        }
        if (graph.containsVertex(startVertex)) {
            this.currentVertex = startVertex;
            this.startVertex = startVertex;
        } else {
            throw new IllegalArgumentException("Start vertex doesn't belong to traversed graph");
        }
    }

    @Override
    public boolean hasNext() {
        Set<? extends E> outgoingEdges = graph.outgoingEdgesOf(currentVertex);
        boolean hasNextEdge = outgoingEdges.size() == 1;
        return currentVertex != null && !endReached && hasNextEdge;
    }

    @Override
    public V next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        if(startVertex != null) {
            startVertex = null;
            return currentVertex;
        }

        Set<? extends E> outgoingEdges = graph.outgoingEdgesOf(currentVertex);
        if (outgoingEdges.size() > 1) {
            throw new IllegalStateException(
                    "Incorrect graph structure: vertex leads to more than one other vertices");
        }

        if (outgoingEdges.isEmpty()) {
            System.out.println("Dead end reached");
            endReached = true;
            return currentVertex;
        }

        E nextEdge = outgoingEdges.iterator().next();
        V nextVertex = Graphs.getOppositeVertex(graph, nextEdge, currentVertex);
        //encounterVertex(nextVertex, nextEdge);
        fireEdgeTraversed(createEdgeTraversalEvent(nextEdge));
        fireVertexTraversed(createVertexTraversalEvent(nextVertex));
        currentVertex = nextVertex;
        return nextVertex;
    }
}
