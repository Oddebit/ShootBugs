package com.od.game.states.win;

import com.od.game.data.ColorData;
import com.od.game.data.DimensionData;
import com.od.game.data.FontData;
import com.od.game.states.Dispatcher;
import com.od.game.states.StatesHandler;

import java.awt.*;

public class WinDispatcher extends Dispatcher {

    public WinDispatcher() {
        super(StatesHandler.GameState.WIN);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D graphics) {
        graphics.setColor(ColorData.HEALTH_TURQUOISE);
        graphics.setFont(FontData.TITLE);
        String string = "YOU WON";
        int height = graphics.getFontMetrics().getHeight();
        int width = graphics.getFontMetrics().stringWidth(string);
        graphics.drawString(string, (int) (DimensionData.WIDTH_CENTER - width / 2d), (int) (DimensionData.HEIGHT_CENTER - height / 2d));

        graphics.setFont(FontData.BOLD);
        string = "Press 'P' to Restart";
        height = graphics.getFontMetrics().getHeight();
        width = graphics.getFontMetrics().stringWidth(string);
        graphics.drawString(string, (int) (DimensionData.WIDTH_CENTER - width / 2d), (int) (DimensionData.HEIGHT_CENTER - height / 2d + 20));

//            String kills = String.format("You killed %d zombies.", dashBoard.getKillCount());
//            int width2 = graphics.getFontMetrics().stringWidth(kills);
//            graphics.drawString(kills, (int) (WIDTH_CENTER - width2 / 2d), HEIGHT_CENTER);
    }
}
