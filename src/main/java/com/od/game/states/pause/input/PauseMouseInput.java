package com.od.game.states.pause.input;

import com.od.game.states.pause.PauseDispatcher;
import com.od.input.NotPlayMouseInput;

public class PauseMouseInput extends NotPlayMouseInput<PauseDispatcher> {
    public PauseMouseInput(PauseDispatcher generalHandler) {
        super(generalHandler);
    }
}
