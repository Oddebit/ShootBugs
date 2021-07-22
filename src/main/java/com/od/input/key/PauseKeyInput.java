package com.od.input.key;

import com.od.game.handlers.GeneralHandler;
import com.od.game.handlers.StatesHandler;
import com.od.game.handlers.pausehandler.PauseGeneralHandler;

import java.awt.event.KeyEvent;

public class PauseKeyInput extends KeyInput<GeneralHandler> {
    public PauseKeyInput(PauseGeneralHandler generalHandler) {
        super(generalHandler);
    }

    @Override
    public void keyPressed(KeyEvent event) {

        if(event.getKeyCode() == KeyEvent.VK_P) generalHandler.setWantedState(StatesHandler.GameState.PLAY);
    }
}
