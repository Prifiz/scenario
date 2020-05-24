package org.myhomeapps.menuentities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class MenuSystem {

    private List<MenuFrame> menuSystem;

    public MenuSystem(List<MenuFrame> menuSystem) {
        this.menuSystem = menuSystem;
    }
}
