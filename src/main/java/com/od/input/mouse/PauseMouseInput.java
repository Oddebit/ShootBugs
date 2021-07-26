package com.od.input.mouse;

import com.od.game.states.pause.PauseDispatcher;

public class PauseMouseInput extends NotPlayMouseInput<PauseDispatcher> {
    public PauseMouseInput(PauseDispatcher generalHandler) {
        super(generalHandler);
    }
}
