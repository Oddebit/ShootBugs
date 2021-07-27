package com.od.game.states.pause.input;

import com.od.game.states.StatesHandler;
import com.od.game.states.pause.PauseDispatcher;
import com.od.input.KeyInput;

import java.awt.event.KeyEvent;

public class PauseKeyInput extends KeyInput<PauseDispatcher> {

    public PauseKeyInput(PauseDispatcher generalHandler) {
        super(generalHandler);
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.VK_P) dispatcher.setWantedState(StatesHandler.GameState.PLAY);
    }
}
