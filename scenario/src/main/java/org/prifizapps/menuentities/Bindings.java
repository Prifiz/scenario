package org.prifizapps.menuentities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bindings {

    private String field;
    private String runAdapter;

    public Bindings(String field, String runAdapter) {
        this.field = field;
        this.runAdapter = runAdapter;
    }

    public Bindings() {
        this.field = "";
        this.runAdapter = "";
    }

    @Override
    public String toString() {
        return "Bindings{" +
                "field='" + field + '\'' +
                ", runAdapter='" + runAdapter + '\'' +
                '}';
    }
}
