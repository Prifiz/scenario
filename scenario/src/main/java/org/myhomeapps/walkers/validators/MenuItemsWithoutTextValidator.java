package org.myhomeapps.walkers.validators;

import org.apache.commons.lang3.StringUtils;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.myhomeapps.menuentities.MenuFrame;
import org.myhomeapps.menuentities.MenuItem;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MenuItemsWithoutTextValidator<V extends MenuFrame, E extends DefaultEdge> extends AbstractValidator<V, E> {

    public MenuItemsWithoutTextValidator(Graph<V, E> graph) {
        super(graph);
    }

    @Override
    protected Collection<String> findOccurrences() {
        List<String> occurrences = new ArrayList<>();
        for(V frame : graph.vertexSet()) {
            if(frame.hasItems() && frame.isInputExpected()) {
                List<String> noTextItems = frame.getItems().stream()
                        .filter(menuItem -> StringUtils.isBlank(menuItem.getText()))
                        .map(MenuItem::getName)
                        .collect(Collectors.toList());
                if(!noTextItems.isEmpty()) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append("Menu Frame: " + frame.getName() + "\n");
                    noTextItems.forEach(item -> {
                        stringBuffer.append("\t" + item + "\n");
                    });
                    occurrences.add(stringBuffer.toString());
                }
            }
        }
        return occurrences;
    }

    @Override
    protected String getDisplayName() {
        return "Items With Empty Text";
    }
}
