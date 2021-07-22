package com.od.input.key;

import com.od.game.handlers.playhandler.PlayGeneralHandler;

import java.awt.event.KeyEvent;

public class PlayKeyInput extends KeyInput<PlayGeneralHandler> {

    public PlayKeyInput(PlayGeneralHandler generalHandler) {
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
                generalHandler.pause();
                break;
            case KeyEvent.VK_ESCAPE:
                System.exit(1);
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
