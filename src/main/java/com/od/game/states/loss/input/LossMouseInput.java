package com.od.game.states.loss.input;

import com.od.game.states.loss.LossDispatcher;
import com.od.input.NotPlayMouseInput;

public class LossMouseInput extends NotPlayMouseInput<LossDispatcher> {
    public LossMouseInput(LossDispatcher generalHandler) {
        super(generalHandler);
    }
}
