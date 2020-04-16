package org.myhomeapps;

import java.util.Observable;
import java.util.Observer;

public class AppToCliAdapter implements Observer {

    private CommandLine commandLine = new SimpleCommandLineImpl();

    public AppToCliAdapter() {
        commandLine.getAllFrames().forEach(frame -> {

        });
    }

    @Override
    public void update(Observable o, Object arg) {
//        commandLine = (CommandLine) o;
//        MenuFrame currentFrame = commandLine.getCurrentFrame();


    }
}
