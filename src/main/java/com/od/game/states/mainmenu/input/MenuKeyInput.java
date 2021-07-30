package com.od.game.states.mainmenu.input;

import com.od.game.states.mainmenu.MainMenuDispatcher;
import com.od.input.KeyInput;

import java.awt.event.KeyEvent;

public class MenuKeyInput extends KeyInput<MainMenuDispatcher> {
    public MenuKeyInput(MainMenuDispatcher dispatcher) {
        super(dispatcher);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_Z:
                dispatcher.up();
                break;

            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                dispatcher.down();
                break;

            case KeyEvent.VK_ENTER:
            case KeyEvent.VK_P:
                dispatcher.enter();
                break;
        }
    }
}
