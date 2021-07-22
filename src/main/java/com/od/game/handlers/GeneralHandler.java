package com.od.game.handlers;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public abstract class GeneralHandler {
    private StatesHandler.GameState wantedState;

    public abstract void tick();

    public abstract void render(Graphics2D graphics);
}
