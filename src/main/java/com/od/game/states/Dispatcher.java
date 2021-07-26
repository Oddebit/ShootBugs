package com.od.game.states;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public abstract class Dispatcher {
    private final StatesHandler.GameState defaultState;
    private StatesHandler.GameState wantedState;

    protected Dispatcher(StatesHandler.GameState defaultState) {
        this.defaultState = defaultState;
        resetWantedState();
    }

    public void resetWantedState() {
        wantedState = defaultState;
    }

    public abstract void tick();

    public abstract void render(Graphics2D graphics);
}
