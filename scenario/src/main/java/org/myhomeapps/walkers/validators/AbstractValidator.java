package org.myhomeapps.walkers.validators;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.myhomeapps.menuentities.MenuFrame;

import java.util.List;

/**
 * The root of class hierarchy for checking menu system correctness based on different criteria.
 * E.g. there can be unreachable menu states or menu might have no exit.
 * The purpose of validator-classes is to discover such situations before they appear while using the CLI.
 * @param <V> {@link MenuFrame} or its children representing vertices of the menu states graph
 * @param <E> {@link DefaultEdge} or its children representing edges of the menu states graph
 */
public abstract class AbstractValidator<V extends MenuFrame, E extends DefaultEdge> {

    protected Graph<V, E> graph;

    /**
     * Creates new validator instance to validate specified {@link Graph} object.
     * @param graph menu states graph represented by {@link Graph} object to validate.
     */
    public AbstractValidator(Graph<V, E> graph) {
        this.graph = graph;
    }

    /**
     * Finds the occurrences of validation violations in simple text form.
     * These are used only for displaying error messages which need to be fixed.
     * Example of occurrence:
     * <p>"HomeFrame:<br>
     *      this menu state has no outgoing states, so can be a dead-end"</p>
     * @return collection of {@link String} representing messages for each found violation occurrence.
     */
    protected abstract List<String> findOccurrences();

    /**
     * Validation rule name which will be displayed in error message if the rule is violated.
     * E.g. "Dead-end finder"
     * @return validation rule name
     */
    protected abstract String getDisplayName();

    /**
     * Runs the validation process.
     * @return validation result as {@link GraphIssue} object.
     */
    public GraphIssue validate() {
        return new GraphIssue(getDisplayName(), findOccurrences());
    }

}
