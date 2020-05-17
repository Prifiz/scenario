package org.myhomeapps.walkers;

import org.apache.commons.lang3.StringUtils;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.traverse.AbstractGraphIterator;
import org.myhomeapps.menuentities.MenuFrame;
import org.myhomeapps.menuentities.MenuItem;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.NoSuchElementException;
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

        // TODO go through vertexSet and validate each transition before iterating graph
        /* FIXME each frame (vertex) consists of items. it.next() should handle ITEM chosen by user!
        e.g. home menu consists of add and exit. If user types "add",
        the next vertex should be the one which is defined as goto in add item*/
        Set<? extends E> outgoingEdges = graph.outgoingEdgesOf(currentVertex);
        if (outgoingEdges.isEmpty()) {
            System.out.println("Dead end reached");
            return currentVertex;
        }

        final String userInput = currentVertex.getUserInput();

        if (currentVertex.hasItems()) {
            if (StringUtils.isBlank(userInput)) {
                throw new IllegalStateException("Cannot choose next menu without user input");
            }

            MenuItem chosenItem;
            try {
                chosenItem = getChosenItem(userInput, currentVertex);
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                return currentVertex;
            }
            for (E edge : outgoingEdges) {
                V nextVertexCandidate = Graphs.getOppositeVertex(graph, edge, currentVertex);
                if (nextVertexCandidate.getName().equals(chosenItem.getGotoMenu())) {
                    fireEdgeTraversed(createEdgeTraversalEvent(edge));
                    fireVertexTraversed(createVertexTraversalEvent(nextVertexCandidate));
                    currentVertex = nextVertexCandidate;
                    return nextVertexCandidate;
                }
            }
            throw new IllegalStateException("Couldn't find next element among outgoing edges");
        } else {
            if (outgoingEdges.size() == 1) {
                currentVertex = Graphs.getOppositeVertex(graph, outgoingEdges.iterator().next(), currentVertex);
                return currentVertex;
            } else {
                throw new IllegalStateException("Single-item menu has more than one outgoing edge!");
            }
        }
    }

    private MenuItem getChosenItem(String userInput, V frame) throws IOException {
        for (MenuItem item : frame.getItems()) {
            if (item.getText().equalsIgnoreCase(userInput)) {
                return item;
            }
            if (item.getInputAlternatives().stream().anyMatch(altInput -> altInput.equalsIgnoreCase(userInput))) {
                return item;
            }
        }
        throw new IOException("No item chosen");
    }
}
