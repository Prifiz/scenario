package org.myhomeapps.menuentities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bindings {

    private String field;
    private String method;

    public Bindings(String field, String method) {
        this.field = field;
        this.method = method;
    }

    public Bindings() {
        this.field = "";
        this.method = "";
    }
}
