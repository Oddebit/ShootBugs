package com.od.game.states.loss;

import com.od.game.data.DimensionData;
import com.od.game.data.FontData;
import com.od.game.states.Dispatcher;
import com.od.game.states.StatesHandler;

import java.awt.*;

public class LossDispatcher extends Dispatcher {

    public LossDispatcher() {
        super(StatesHandler.GameState.LOSS);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics2D graphics) {
        graphics.setColor(Color.RED);
        graphics.setFont(new Font(Font.DIALOG, Font.BOLD, 75));
        String string = "GAME OVER";
        int height = graphics.getFontMetrics().getHeight();
        int width = graphics.getFontMetrics().stringWidth(string);
        graphics.drawString(string, (int) (DimensionData.WIDTH_CENTER - width / 2d), (int) (DimensionData.HEIGHT_CENTER - height / 2d));

        graphics.setFont(FontData.BOLD.getFont());
        string = "Press 'P' to Restart";
        height = graphics.getFontMetrics().getHeight();
        width = graphics.getFontMetrics().stringWidth(string);
        graphics.drawString(string, (int) (DimensionData.WIDTH_CENTER - width / 2d), (int) (DimensionData.HEIGHT_CENTER - height / 2d + 20));
//            String kills = String.format("You killed %d zombies.", dashBoard.getKillCount());
//            int width2 = graphics.getFontMetrics().stringWidth(kills);
//            graphics.drawString(kills, (int) (WIDTH_CENTER - width2 / 2d), (int) (HEIGHT_CENTER));
    }

    public void restart() {
        setWantedState(StatesHandler.GameState.PLAY);
    }
}
