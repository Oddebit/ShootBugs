package com.od.game.states.menu.input;

import com.od.game.states.menu.MenuDispatcher;
import com.od.input.NotPlayMouseInput;

public class MenuMouseInput extends NotPlayMouseInput<MenuDispatcher> {
    public MenuMouseInput(MenuDispatcher generalHandler) {
        super(generalHandler);
    }
}
