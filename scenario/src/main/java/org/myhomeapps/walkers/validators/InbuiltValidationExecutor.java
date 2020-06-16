package org.myhomeapps.walkers.validators;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.myhomeapps.menuentities.MenuFrame;
import org.myhomeapps.menuentities.properties.DefaultPropertiesParser;
import org.myhomeapps.menuentities.properties.PropertiesParser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Runs the menu graph validation by predefined set of {@link AbstractValidator} subclasses.
 */
public class InbuiltValidationExecutor implements ValidationExecutor {

    private final DefaultDirectedGraph<MenuFrame, DefaultEdge> menuGraph;

    public InbuiltValidationExecutor(DefaultDirectedGraph<MenuFrame, DefaultEdge> menuGraph) {
        this.menuGraph = menuGraph;
    }

    /**
     * Runs the validation process.
     * @return {@link List} of {@link GraphIssue} objects if some of validators discover any violations
     * or empty {@link List} otherwise.
     */
    @Override
    public List<? extends GraphIssue> validate() {
        List<AbstractValidator<MenuFrame, DefaultEdge>> validators =
                prepareInbuiltValidators(new DefaultPropertiesParser(), menuGraph);
        return doValidateGraph(validators);
    }

    private List<AbstractValidator<MenuFrame, DefaultEdge>> prepareInbuiltValidators(
            PropertiesParser propertiesParser, DefaultDirectedGraph<MenuFrame, DefaultEdge> menuGraph) {
        List<AbstractValidator<MenuFrame, DefaultEdge>> validators = new ArrayList<>();
        validators.add(new DeadEndsValidator<>(menuGraph, propertiesParser));
        validators.add(new MultipleHomeFramesValidator<>(menuGraph, propertiesParser));
        validators.add(new EndlessCyclesValidator<>(menuGraph, propertiesParser));
        validators.add(new FramesWithoutTextValidator<>(menuGraph));
        validators.add(new MenuItemsWithoutTextValidator<>(menuGraph));
        validators.add(new DuplicatedFramesValidator<>(menuGraph));
        return validators;
    }

    private List<? extends GraphIssue> doValidateGraph(List<AbstractValidator<MenuFrame, DefaultEdge>> validators) {
        return validators.stream()
                .filter(validator -> !validator.validate().getOccurrences().isEmpty())
                .map(AbstractValidator::validate)
                .collect(Collectors.toList());
    }
}
