package org.myhomeapps;

import org.myhomeapps.config.ConfigParser;
import org.myhomeapps.config.SimpleYamlParser;
import org.myhomeapps.menumodel.MenuFrame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpleMenuWalker implements MenuWalker {

    private List<MenuFrame> frames = new ArrayList<>();

    private int currentFrameIdx = 0;

    public SimpleMenuWalker() throws IOException {
        ConfigParser parser = new SimpleYamlParser("menuSystem.yaml");
        frames.addAll(parser.parseMenuSystem().getMenuSystem());
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
