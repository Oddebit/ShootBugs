package com.od.input.key;

import com.od.game.handlers.GeneralHandler;

import java.awt.event.KeyAdapter;

public abstract class KeyInput<G extends GeneralHandler> extends KeyAdapter {

    protected final G generalHandler;

    protected KeyInput(G generalHandler) {
        this.generalHandler = generalHandler;
    }
}
