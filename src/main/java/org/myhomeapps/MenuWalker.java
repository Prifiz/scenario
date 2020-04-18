package org.myhomeapps;

import org.myhomeapps.menumodel.MenuFrame;

import java.util.List;

public interface MenuWalker {

    MenuFrame getHomeFrame();
    MenuFrame getCurrentFrame();
    void setCurrentFrame(MenuFrame frame);
    List<MenuFrame> getAllFrames();
    void setUserInput(String input);
}
