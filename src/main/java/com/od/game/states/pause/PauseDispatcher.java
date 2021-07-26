package com.od.game.states.pause;

import com.od.game.data.ColorData;
import com.od.game.data.DimensionData;
import com.od.game.states.Dispatcher;
import com.od.game.states.StatesHandler;

import java.awt.*;

public class PauseDispatcher extends Dispatcher {

    public PauseDispatcher() {
        super(StatesHandler.GameState.PAUSE);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D graphics) {
        graphics.setColor(ColorData.HERO_ORANGE);
        graphics.setFont(new Font(Font.DIALOG, Font.BOLD, 75));
        String str = "PAUSE";
        int height = graphics.getFontMetrics().getHeight();
        int width = graphics.getFontMetrics().stringWidth(str);
        graphics.drawString(str, (int) (DimensionData.WIDTH_CENTER - width / 2d), (int) (DimensionData.HEIGHT_CENTER - height / 2d));
    }
}
