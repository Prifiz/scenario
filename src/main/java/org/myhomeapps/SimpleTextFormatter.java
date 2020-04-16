package org.myhomeapps;

public class SimpleTextFormatter {
    public String format(MenuFrame frame) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(frame.getText());
        stringBuffer.append("\n");
        frame.getMenuItems().forEach(item -> {
            stringBuffer.append("\t" + item.getText() + "\n");
        });
        return stringBuffer.toString();
    }
}
