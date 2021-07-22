package com.od.game.handlers.pausehandler;

import com.od.game.data.ColorData;
import com.od.game.data.DimensionData;
import com.od.game.handlers.GeneralHandler;
import com.od.game.handlers.StatesHandler;

import java.awt.*;

public class PauseGeneralHandler extends GeneralHandler {
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

    public void resume() {
        setWantedState(StatesHandler.GameState.PLAY);
    }
}
