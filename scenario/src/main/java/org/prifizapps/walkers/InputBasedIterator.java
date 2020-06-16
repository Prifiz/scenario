package org.prifizapps.walkers;

import org.prifizapps.menuentities.MenuFrame;

public interface InputBasedIterator<V extends MenuFrame, E> {
    V next(String userInput);
}
