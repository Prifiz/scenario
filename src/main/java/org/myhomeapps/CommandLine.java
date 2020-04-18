package org.myhomeapps;

import org.myhomeapps.menumodel.MenuFrame;

import java.util.List;

public interface CommandLine {
    // SHOULD BE SINGLETON!
    void printText(String text);
    void printText(String text, SimpleTextFormatter formatter);
    String readUserInput();

    void printCurrentFrame();
    MenuFrame getCurrentFrame();
    void run();
    List<MenuFrame> getAllFrames();
}
