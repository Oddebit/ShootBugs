package com.od.input.key;

import com.od.game.states.StatesHandler;
import com.od.game.states.pause.PauseDispatcher;

import java.awt.event.KeyEvent;

public class PauseKeyInput extends KeyInput<PauseDispatcher> {

    public PauseKeyInput(PauseDispatcher generalHandler) {
        super(generalHandler);
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.VK_P) generalHandler.setWantedState(StatesHandler.GameState.PLAY);
    }
}
