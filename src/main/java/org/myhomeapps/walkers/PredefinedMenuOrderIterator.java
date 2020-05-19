package org.myhomeapps.walkers;

import org.apache.commons.lang3.StringUtils;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.traverse.AbstractGraphIterator;
import org.myhomeapps.menuentities.MenuFrame;
import org.myhomeapps.menuentities.MenuItem;

import javax.annotation.Nonnull;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

public class PredefinedMenuOrderIterator<V extends MenuFrame, E> extends AbstractGraphIterator<V, E> {

    private V currentVertex;
    private boolean startVertexVisited = false;

    public PredefinedMenuOrderIterator(@Nonnull Graph<V, E> graph, @Nonnull V startVertex) {
        super(graph);
        this.crossComponentTraversal = false;
        if (graph.containsVertex(startVertex)) {
            this.currentVertex = startVertex;
        } else {
            throw new IllegalArgumentException("Start vertex doesn't belong to traversed graph");
        }
    }

    @Override
    public boolean hasNext() {
        return currentVertex != null && Graphs.vertexHasSuccessors(graph, currentVertex);
    }

    @Override
    public V next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }

        if (!startVertexVisited) {
            startVertexVisited = true;
            return currentVertex;
        }

        Set<? extends E> outgoingEdges = graph.outgoingEdgesOf(currentVertex);
        if (outgoingEdges.isEmpty()) {
            System.out.println("Dead end reached");
            return currentVertex;
        }

        if (currentVertex.hasItems()) {
            return nextByChosenItem(outgoingEdges);
        } else {
            return nextByPassedFrame(outgoingEdges);
        }
    }

    protected V nextByChosenItem(Set<? extends E> outgoingEdges) {
        final String userInput = currentVertex.getUserInput();
        if (StringUtils.isBlank(userInput)) {
            throw new IllegalStateException("Cannot choose next menu without user input");
        }

        Optional<MenuItem> possiblyChosenItem = getChosenItem(userInput, currentVertex);
        if(possiblyChosenItem.isEmpty()) {
            return currentVertex;
        }

        for (E edge : outgoingEdges) {
            V nextVertexCandidate = Graphs.getOppositeVertex(graph, edge, currentVertex);
            if (nextVertexCandidate.getName().equals(possiblyChosenItem.get().getGotoMenu())) {
                fireEdgeTraversed(createEdgeTraversalEvent(edge));
                fireVertexTraversed(createVertexTraversalEvent(nextVertexCandidate));
                currentVertex = nextVertexCandidate;
                return nextVertexCandidate;
            }
        }
        throw new IllegalStateException("Couldn't find next element among outgoing edges");
    }

    protected V nextByPassedFrame(Set<? extends E> outgoingEdges) {
        if (outgoingEdges.size() == 1) {
            currentVertex = Graphs.getOppositeVertex(graph, outgoingEdges.iterator().next(), currentVertex);
            return currentVertex;
        } else {
            throw new IllegalStateException("Single-item menu has more than one outgoing edge!");
        }
    }

    protected Optional<MenuItem> getChosenItem(String userInput, V frame) {
        for (MenuItem item : frame.getItems()) {
            if (item.getText().equalsIgnoreCase(userInput)) {
                return Optional.of(item);
            }
            if (item.getInputAlternatives().stream().anyMatch(altInput -> altInput.equalsIgnoreCase(userInput))) {
                return Optional.of(item);
            }
        }
        return Optional.empty();
    }
}
