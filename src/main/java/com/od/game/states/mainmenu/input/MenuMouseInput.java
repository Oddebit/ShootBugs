package com.od.game.states.mainmenu.input;

import com.od.game.states.mainmenu.MainMenuDispatcher;
import com.od.input.NotPlayMouseInput;

public class MenuMouseInput extends NotPlayMouseInput<MainMenuDispatcher> {
    public MenuMouseInput(MainMenuDispatcher generalHandler) {
        super(generalHandler);
    }
}
