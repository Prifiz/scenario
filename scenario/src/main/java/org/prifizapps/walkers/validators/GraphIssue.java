package org.prifizapps.walkers.validators;

import lombok.Getter;

import java.util.Collection;

@Getter
public class GraphIssue {

    private String name;
    private Collection<String> occurrences;

    public GraphIssue(String name, Collection<String> occurrences) {
        this.name = name;
        this.occurrences = occurrences;
    }
}
