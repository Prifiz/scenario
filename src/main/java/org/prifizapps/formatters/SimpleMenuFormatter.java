package org.prifizapps.formatters;

import org.prifizapps.menuentities.MenuFrame;
import org.prifizapps.menuentities.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SimpleMenuFormatter implements MenuFormatter {
    public String format(MenuFrame frame) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(frame.getText());
        stringBuffer.append("\n");
        List<MenuItem> items = frame.getItems();
        List<MenuItem> optItems = Optional.ofNullable(items).orElse(new ArrayList<>());
        optItems.forEach(item -> {
            stringBuffer.append("\t" + item.getText());
            if(!item.getInputAlternatives().isEmpty()) {
                stringBuffer.append(buildAlternatives(item));
            }
            stringBuffer.append("\n");
        });
        return stringBuffer.toString().replaceAll("\n\n", "\n");
    }

    private String buildAlternatives(MenuItem menuItem) {
        return " (" +
                String.join(", ", menuItem.getInputAlternatives()) +
                ")";
    }
}
