package org.myhomeapps.formatters;

import org.myhomeapps.menuentities.MenuFrame;
import org.myhomeapps.menuentities.MenuItem;

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
        return stringBuffer.toString();
    }

    private String buildAlternatives(MenuItem menuItem) {
        return " (" +
                String.join(", ", menuItem.getInputAlternatives()) +
                ")";
    }
}
