package org.myhomeapps.menuentities.input;

import java.util.List;

public interface InputChecker {
    boolean isInputCorrect(List<InputRule> inputRules, String userInput);
}
