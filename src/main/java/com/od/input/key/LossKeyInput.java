package com.od.input.key;

import com.od.game.handlers.GeneralHandler;
import com.od.game.handlers.StatesHandler;
import com.od.game.handlers.losshandler.LossGeneralHandler;

import java.awt.event.KeyEvent;

public class LossKeyInput extends KeyInput<GeneralHandler> {

    public LossKeyInput(LossGeneralHandler generalHandler) {
        super(generalHandler);
    }

    @Override
    public void keyPressed(KeyEvent event) {

        if(event.getKeyCode() == KeyEvent.VK_P) generalHandler.setWantedState(StatesHandler.GameState.PLAY);
    }
}
