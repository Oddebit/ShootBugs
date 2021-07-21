package com.od.input;

import com.od.game.handlers.GeneralHandler;

import java.awt.event.*;

public class MouseInput extends MouseAdapter implements MouseWheelListener, MouseMotionListener {

    private final GeneralHandler generalHandler;

    public MouseInput(GeneralHandler generalHandler) {
        this.generalHandler = generalHandler;
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
