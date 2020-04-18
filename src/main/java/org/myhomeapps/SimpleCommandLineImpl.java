package org.myhomeapps;

import org.myhomeapps.menumodel.MenuFrame;
import org.myhomeapps.menumodel.MenuItem;
import org.myhomeapps.menumodel.NoItem;
import org.myhomeapps.printers.FormattedMenuPrinter;

import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;

public class SimpleCommandLineImpl extends Observable implements CommandLine {

    private MenuWalker walker;

    public SimpleCommandLineImpl() throws IOException {
        // init walker, etc.
        walker = new SimpleMenuWalker();
    }

    @Override
    public void printText(String text) {
        System.out.println(text);
    }

    @Override
    public void printText(String text, SimpleTextFormatter formatter) {

    }

    @Override
    public String readUserInput() {
        Scanner userInputScanner = new Scanner(System.in);
        return userInputScanner.nextLine();
    }

    @Override
    public void printCurrentFrame() {
        new FormattedMenuPrinter(new SimpleTextFormatter(), System.out).print(getCurrentFrame());
        String userInput = readUserInput();
        getCurrentFrame().setUserInput(userInput);

        try {
            MenuItem selectedItem = getSelectedItem(getCurrentFrame(), userInput);
            GotoLevel gotoLevel = defineGotoLevel(getCurrentFrame());
            switch (gotoLevel) {
                case ITEM: {
                    processItemLevelGoto(selectedItem);
                    break;
                }
                case MENU: {
                    processMenuLevelGoto(getCurrentFrame());
                    break;
                }
                default: {
                    System.out.println("Something went wrong with goto levels...");
                }
            }

        } catch (IOException ex) {
            System.out.println("Exception occurred!");
            System.out.println(ex.getMessage());
        }
    }

    private void processMenuLevelGoto(MenuFrame currentFrame) {
        for(MenuFrame frame: walker.getAllFrames()) {
            if(currentFrame.getGotoMenu().equalsIgnoreCase(frame.getName())) {
                walker.setCurrentFrame(frame);
                printCurrentFrame();
            }
        }
    }

    private void processItemLevelGoto(MenuItem menuItem) {
        for(MenuFrame frame: walker.getAllFrames()) {
            if(menuItem.getGotoMenu().equalsIgnoreCase(frame.getName())) {
                walker.setCurrentFrame(frame);
                printCurrentFrame();
            }
        }
    }

    private enum GotoLevel {
        MENU, ITEM
    }

    private MenuItem getSelectedItem(MenuFrame frame, String userInput) throws IOException {
        if(frame.getItems() == null || frame.getItems().isEmpty()) {
            return new NoItem();
        }
        for(MenuItem menuItem : frame.getItems()) {
            if(userInput.equalsIgnoreCase(menuItem.getText())) {
                return menuItem;
            }
        }
        throw new IOException("Incorrect input, no item chosen!");
    }

    private GotoLevel defineGotoLevel(MenuFrame frame) throws IOException {
        if(isItemLevelGotosDefined(frame)) {
            return GotoLevel.ITEM;
        }
        if(frame.getGotoMenu() != null && !frame.getGotoMenu().isEmpty()) {
            return GotoLevel.MENU;
        }
        throw new IOException("Goto not defined neither on item nor on menu level");
    }

    private boolean isItemLevelGotosDefined(MenuFrame frame) {
        if(frame.getItems() == null || frame.getItems().isEmpty()) {
            return false;
        }
        for(MenuItem item : frame.getItems()) {
            if(item.getGotoMenu() == null || item.getGotoMenu().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public MenuFrame getCurrentFrame() {
        return walker.getCurrentFrame();
    }

    @Override
    public void run() {
        System.out.println("Starting UI...");
        printCurrentFrame();
    }

    @Override
    public List<MenuFrame> getAllFrames() {
        return walker.getAllFrames();
    }

}
