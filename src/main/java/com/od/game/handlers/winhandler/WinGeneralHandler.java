package com.od.game.handlers.winhandler;

import com.od.game.data.ColorData;
import com.od.game.data.DimensionData;
import com.od.game.handlers.GeneralHandler;

import java.awt.*;

public class WinGeneralHandler extends GeneralHandler {

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D graphics) {
        graphics.setColor(ColorData.HEALTH_TURQUOISE);
        graphics.setFont(new Font(Font.DIALOG, Font.BOLD, 75));
        String str = "YOU WON";
        int height = graphics.getFontMetrics().getHeight();
        int width = graphics.getFontMetrics().stringWidth(str);
        graphics.drawString(str, (int) (DimensionData.WIDTH_CENTER - width / 2d), (int) (DimensionData.HEIGHT_CENTER - height / 2d));

        graphics.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
//            String kills = String.format("You killed %d zombies.", dashBoard.getKillCount());
//            int width2 = graphics.getFontMetrics().stringWidth(kills);
//            graphics.drawString(kills, (int) (WIDTH_CENTER - width2 / 2d), HEIGHT_CENTER);
    }
}
