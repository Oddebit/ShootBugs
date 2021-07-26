package com.od.input.mouse;

import com.od.game.states.Dispatcher;

public abstract class NotPlayMouseInput<G extends Dispatcher> extends MouseInput<G> {
    protected NotPlayMouseInput(G generalHandler) {
        super(generalHandler);
    }
}
