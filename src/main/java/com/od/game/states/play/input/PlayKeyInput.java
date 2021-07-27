package com.od.game.states.play.input;

import com.od.game.states.StatesHandler;
import com.od.game.states.play.PlayDispatcher;
import com.od.input.KeyInput;

import java.awt.event.KeyEvent;

public class PlayKeyInput extends KeyInput<PlayDispatcher> {

    public PlayKeyInput(PlayDispatcher generalHandler) {
        super(generalHandler);
    }

    @Override
    public void keyPressed(KeyEvent event) {

        int key = event.getKeyCode();

        switch (key) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_Z:
                generalHandler.heroSetMovementY(-1);
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                generalHandler.heroSetMovementY(+1);
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_Q:
                generalHandler.heroSetMovementX(-1);
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                generalHandler.heroSetMovementX(+1);
                break;
            case KeyEvent.VK_SHIFT:
            case KeyEvent.VK_R:
                generalHandler.weaponAskInitReload();
                break;
            case KeyEvent.VK_NUMPAD0:
            case KeyEvent.VK_CONTROL:
                generalHandler.weaponSetNextActiveWeapon(1);
                break;
            case KeyEvent.VK_P:
                generalHandler.setWantedState(StatesHandler.GameState.PAUSE);
                break;
            case KeyEvent.VK_ESCAPE:
                generalHandler.setWantedState(StatesHandler.GameState.LOSS);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        int key = event.getKeyCode();

        switch (key) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_Z:
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                generalHandler.heroSetMovementY(0);
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_Q:
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                generalHandler.heroSetMovementX(0);
                break;
        }
    }
}
