package com.od.input.mouse;

import com.od.game.states.win.WinDispatcher;

public class WinMouseInput extends NotPlayMouseInput<WinDispatcher> {
    public WinMouseInput(WinDispatcher generalHandler) {
        super(generalHandler);
    }
}
