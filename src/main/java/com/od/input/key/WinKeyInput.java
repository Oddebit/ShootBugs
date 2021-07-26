package com.od.input.key;

import com.od.game.states.StatesHandler;
import com.od.game.states.win.WinDispatcher;

import java.awt.event.KeyEvent;

public class WinKeyInput extends KeyInput<WinDispatcher> {

    public WinKeyInput(WinDispatcher generalHandler) {
        super(generalHandler);
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.VK_P) generalHandler.setWantedState(StatesHandler.GameState.PLAY);
    }
}
