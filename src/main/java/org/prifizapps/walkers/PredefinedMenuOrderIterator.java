package org.prifizapps.walkers;

import lombok.Getter;
import lombok.NonNull;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.traverse.AbstractGraphIterator;
import org.prifizapps.menuentities.MenuFrame;
import org.prifizapps.menuentities.MenuItem;
import org.prifizapps.menuentities.input.ItemChooser;
import org.prifizapps.menuentities.input.ItemChooserWithAlternatives;

import java.util.Optional;
import java.util.Set;

public class PredefinedMenuOrderIterator<V extends MenuFrame, E>
        extends AbstractGraphIterator<V, E> implements InputBasedIterator<V, E> {

    Logger logger = LogManager.getLogger(getClass());

    @Getter
    private V currentVertex;
    @Getter
    private boolean startVertexVisited = false;

    private ItemChooser itemChooser;

    static final String NOT_MY_START_VERTEX_MSG = "Start vertex doesn't belong to traversed graph";

    public PredefinedMenuOrderIterator(@NonNull Graph<V, E> graph, @NonNull V startVertex) {
        super(graph);
        this.itemChooser = new ItemChooserWithAlternatives();
        this.crossComponentTraversal = false;
        if (graph.containsVertex(startVertex)) {
            this.currentVertex = startVertex;
        } else {
            throw new IllegalArgumentException(NOT_MY_START_VERTEX_MSG);
        }
    }

    public PredefinedMenuOrderIterator<V, E> withCustomItemChooser(ItemChooser itemChooser) {
        this.itemChooser = itemChooser;
        return this;
    }

    @Override
    public boolean hasNext() {
        return currentVertex != null && Graphs.vertexHasSuccessors(graph, currentVertex);
    }

    @Override
    public V next() {
        return next("");
    }

    @Override
    public V next(String userInput) {
        if (!startVertexVisited) {
            startVertexVisited = true;
            return currentVertex;
        }

        Set<? extends E> outgoingEdges = graph.outgoingEdgesOf(currentVertex);
        if (outgoingEdges.isEmpty()) {
            logger.info("End reached");
            return currentVertex;
        }
// Maybe will be never needed
//        if (outgoingEdges.size() > 1) {
//            throw new IllegalStateException(
//                    "Single-item menu has more than one outgoing edge. Check frame: " + currentVertex.getName());
//        }

        if (currentVertex.hasItems()) {
            return nextByChosenItem(outgoingEdges, userInput);
        } else {
            return currentVertex = Graphs.getOppositeVertex(graph, outgoingEdges.iterator().next(), currentVertex);
        }
    }

    V nextByChosenItem(Set<? extends E> currentVertexOutgoingEdges, String userInput) {
        Optional<MenuItem> possiblyChosenItem = Optional.ofNullable(
                itemChooser.chooseItem(currentVertex.getItems(), userInput));
        if(possiblyChosenItem.isEmpty()) {
            return currentVertex;// repeat prompt of user input
        }

        for (E edge : currentVertexOutgoingEdges) {
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
}
