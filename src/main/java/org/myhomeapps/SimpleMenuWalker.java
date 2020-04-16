package org.myhomeapps;

import java.util.ArrayList;
import java.util.List;

public class SimpleMenuWalker implements MenuWalker {

    private List<MenuFrame> frames = new ArrayList<>();

    private int currentFrameIdx = 0;

    public SimpleMenuWalker() {
        MenuFrame homeFrame = new MenuFrame(1, "text", "Choose action:", "");
        homeFrame.addItem(new MenuItem(1, "add", "Add", "first"));
        homeFrame.addItem(new MenuItem(2, "exit", "Exit", "1"));
        frames.add(homeFrame);
        MenuFrame firstValueFrame = new MenuFrame(1, "first", "Enter first value:", "second");
        firstValueFrame.setBindField("addFirst");
        frames.add(firstValueFrame);
        MenuFrame secondValueFrame = new MenuFrame(1, "second", "Enter second value:", "");
        secondValueFrame.setBindField("addSecond");
        secondValueFrame.setBindMethod("add");
        frames.add(secondValueFrame);
    }

    @Override
    public MenuFrame getHomeFrame() {
        return frames.get(0);
    }

    public MenuFrame getCurrentFrame() {
        return frames.get(currentFrameIdx);
    }

    @Override
    public void setCurrentFrame(MenuFrame frame) {
        for(int i = 0; i < frames.size(); i++) {
            MenuFrame currentFrame = frames.get(i);
            if(currentFrame.equals(frame)) {
                currentFrameIdx = i;
            }
        }
    }

    @Override
    public List<MenuFrame> getAllFrames() {
        return frames;
    }

    @Override
    public void setUserInput(String input) {
        getCurrentFrame().setUserInput(input);
        // check user input and change current frame idx here!
    }


}
