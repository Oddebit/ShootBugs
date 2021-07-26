package com.od.input.key;

import com.od.game.states.Dispatcher;

import java.awt.event.KeyAdapter;

public abstract class KeyInput<G extends Dispatcher> extends KeyAdapter {

    protected final G generalHandler;

    protected KeyInput(G generalHandler) {
        this.generalHandler = generalHandler;
    }
}
