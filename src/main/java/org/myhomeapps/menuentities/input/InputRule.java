package org.myhomeapps.menuentities.input;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InputRule {

    protected String rule = "";
    protected String errorMessage = "";

    public InputRule(String rule, String errorMessage) {
        this.rule = rule;
        this.errorMessage = errorMessage;
    }
}
