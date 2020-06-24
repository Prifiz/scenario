package org.prifizapps.formatters;

import org.prifizapps.menuentities.MenuFrame;
import org.prifizapps.menuentities.MenuItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SimpleMenuFormatter implements MenuFormatter {

    private static final String EOL = "\n";
    private static final String INDENT = "\t";

    public String format(MenuFrame frame) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(frame.getText());
        stringBuffer.append(EOL);
        List<MenuItem> items = Optional.ofNullable(frame.getItems())
                .orElse(new ArrayList<>());
        items.forEach(item -> {
            stringBuffer.append(INDENT);
            stringBuffer.append(item.getText());
            if(!item.getInputAlternatives().isEmpty()) {
                stringBuffer.append(buildAlternatives(item));
            }
            stringBuffer.append(EOL);
        });
        return stringBuffer.toString().replaceAll(EOL + EOL, EOL);
    }

    private String buildAlternatives(MenuItem menuItem) {
        return " (" +
                String.join(", ", menuItem.getInputAlternatives()) +
                ")";
    }
}
