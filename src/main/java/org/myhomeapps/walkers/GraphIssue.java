package org.myhomeapps.walkers;

import java.util.List;

public class GraphIssue {

    private String name;
    private List<String> occurrences;

    public GraphIssue(String name, List<String> occurrences) {
        this.name = name;
        this.occurrences = occurrences;
    }

    public String getName() {
        return name;
    }

    public List<String> getOccurrences() {
        return occurrences;
    }
}
