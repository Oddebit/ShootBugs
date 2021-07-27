package com.od.game.states.play.input;

import com.od.game.states.play.PlayDispatcher;
import com.od.input.MouseInput;

import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class PlayMouseInput extends MouseInput<PlayDispatcher> {

    public PlayMouseInput(PlayDispatcher generalHandler) {
        super(generalHandler);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        generalHandler.weaponRetarget(e.getX(), e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {

        int click = e.getButton();

        switch (click) {
            case 1:
                generalHandler.weaponAskInitBurst();
                break;
            case 3:
                generalHandler.weaponAskInitReload();
                break;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        generalHandler.weaponRetarget(e.getX(), e.getY());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        generalHandler.weaponStopBurst();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent event) {
        int scroll = event.getWheelRotation();
        int increment = scroll / Math.abs(scroll);
        generalHandler.weaponSetNextActiveWeapon(increment);
    }
}
