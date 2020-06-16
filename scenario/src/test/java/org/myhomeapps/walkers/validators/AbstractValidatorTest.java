package org.myhomeapps.walkers.validators;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.junit.Assert;
import org.junit.Test;
import org.myhomeapps.menuentities.MenuFrame;

import java.util.Collections;
import java.util.List;

public class AbstractValidatorTest {

    @Test
    public void validateTest() {
        final String name = "name";
        final List<String> occurrences = Collections.singletonList("occurrence");
        AbstractValidator<MenuFrame, DefaultEdge> validator = new AbstractValidator<>(
                new DefaultDirectedGraph<>(DefaultEdge.class)) {
            @Override
            protected List<String> findOccurrences() {
                return occurrences;
            }

            @Override
            protected String getDisplayName() {
                return name;
            }
        };
        GraphIssue issue = validator.validate();
        Assert.assertEquals(name, issue.getName());
        Assert.assertArrayEquals(occurrences.toArray(), issue.getOccurrences().toArray());
    }
}
