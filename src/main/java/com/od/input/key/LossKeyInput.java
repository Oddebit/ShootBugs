package com.od.input.key;

import com.od.game.states.StatesHandler;
import com.od.game.states.loss.LossDispatcher;

import java.awt.event.KeyEvent;

public class LossKeyInput extends KeyInput<LossDispatcher> {

    public LossKeyInput(LossDispatcher generalHandler) {
        super(generalHandler);
    }

    @Override
    public void keyReleased(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.VK_P) generalHandler.setWantedState(StatesHandler.GameState.PLAY);
    }
}
