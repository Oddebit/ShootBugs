package com.od.input;

import com.od.game.states.Dispatcher;

import java.awt.event.KeyAdapter;

public abstract class KeyInput<G extends Dispatcher> extends KeyAdapter {

    protected final G dispatcher;

    protected KeyInput(G dispatcher) {
        this.dispatcher = dispatcher;
    }
}
