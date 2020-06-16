package org.prifizapps.walkers.validators;

import org.apache.commons.lang3.StringUtils;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.prifizapps.menuentities.MenuFrame;
import org.prifizapps.menuentities.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Validator to find menu items without text to be displayed.
 * Prevents the case when user should choose the menu item but can't see which one, e.g.
 * <p>Are you sure want to exit?<br>
 * 1. Yes<br>
 * [second option should be here but nothing is displayed]
 * </p>
 */
public class MenuItemsWithoutTextValidator<V extends MenuFrame, E extends DefaultEdge> extends AbstractValidator<V, E> {

    public MenuItemsWithoutTextValidator(Graph<V, E> graph) {
        super(graph);
    }

    static final String VIOLATED_ITEM_NAME_PREFIX = "Menu Frame: ";

    /**
     * Finds names of menus some of which items have no displayed text.
     *
     * @return {@link List} of menu names.
     */
    @Override
    protected List<String> findOccurrences() {
        List<String> occurrences = new ArrayList<>();
        for (V frame : graph.vertexSet()) {
            if (frame.hasItems() && frame.isInputExpected()) {
                List<String> noTextItems = frame.getItems().stream()
                        .filter(menuItem -> StringUtils.isBlank(menuItem.getText()))
                        .map(MenuItem::getName)
                        .collect(Collectors.toList());
                if (!noTextItems.isEmpty()) {
                    StringBuffer stringBuffer = new StringBuffer();
                    stringBuffer.append(VIOLATED_ITEM_NAME_PREFIX + frame.getName());
                    if (noTextItems.size() > 1) {
                        stringBuffer.append("\n"); // todo formatter needed here
                        noTextItems.forEach(item -> {
                            stringBuffer.append("\t" + item + "\n");
                        });
                    }
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
