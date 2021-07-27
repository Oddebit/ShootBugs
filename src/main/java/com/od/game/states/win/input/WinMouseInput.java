package com.od.game.states.win.input;

import com.od.game.states.win.WinDispatcher;
import com.od.input.NotPlayMouseInput;

public class WinMouseInput extends NotPlayMouseInput<WinDispatcher> {
    public WinMouseInput(WinDispatcher generalHandler) {
        super(generalHandler);
    }
}
