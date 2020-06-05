package org.myhomeapps.walkers;

import org.myhomeapps.menuentities.MenuFrame;

public interface InputBasedIterator<V extends MenuFrame, E> {
    V next(String userInput);
}
