package com.od.game.states.loss.input;

import com.od.game.states.StatesHandler;
import com.od.game.states.loss.LossDispatcher;
import com.od.input.KeyInput;

import java.awt.event.KeyEvent;

public class LossKeyInput extends KeyInput<LossDispatcher> {

    public LossKeyInput(LossDispatcher generalHandler) {
        super(generalHandler);
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.VK_P) dispatcher.setWantedState(StatesHandler.GameState.PLAY);
    }
}
