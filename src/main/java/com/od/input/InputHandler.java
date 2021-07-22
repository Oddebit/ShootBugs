package com.od.input;

import com.od.game.Game;
import com.od.game.handlers.StatesHandler;
import com.od.input.key.LossKeyInput;
import com.od.input.key.PauseKeyInput;
import com.od.input.key.PlayKeyInput;
import com.od.input.key.WinKeyInput;
import com.od.input.mouse.PlayMouseInput;

public class InputHandler {

    public InputHandler(Game game, StatesHandler statesHandler){

        PlayMouseInput playMouseInput = new PlayMouseInput(statesHandler.getPlayGeneralHandler());
        game.addMouseListener(playMouseInput);
        game.addMouseMotionListener(playMouseInput);
        game.addMouseWheelListener(playMouseInput);

        game.addKeyListener(new PlayKeyInput(statesHandler.getPlayGeneralHandler()));
        game.addKeyListener(new PauseKeyInput(statesHandler.getPauseGeneralHandler()));
        game.addKeyListener(new WinKeyInput(statesHandler.getWinGeneralHandler()));
        game.addKeyListener(new LossKeyInput(statesHandler.getLossGeneralHandler()));
    }
}
