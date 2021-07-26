package com.od.input.mouse;

import com.od.game.states.loss.LossDispatcher;

public class LossMouseInput extends NotPlayMouseInput<LossDispatcher> {
    public LossMouseInput(LossDispatcher generalHandler) {
        super(generalHandler);
    }
}
