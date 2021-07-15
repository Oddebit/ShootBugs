package com.od.input;

import com.od.game.handlers.GeneralHandler;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelListener;

public class MouseInput extends MouseAdapter implements MouseWheelListener {

    private final GeneralHandler generalHandler;
    private float x;
    private float y;
    private boolean mousePressed;

    public MouseInput(GeneralHandler generalHandler) {
        this.generalHandler = generalHandler;
    }


    private void initShootingThread() {
        new Thread() {
            public void run() {
                do {
                    generalHandler.weaponAskInitShot(x, y);
                } while (mousePressed);
            }
        }.start();
    }

    // // PLAYER SHOOTING EVENTS // //

    public void mousePressed(MouseEvent event) {

        int click = event.getButton();

        switch (click) {
            case 1:
                x = event.getX();
                y = event.getY();
//                if(hero.getActiveWeapon().getWeaponType() == Weapon.WeaponType.RIFLE) {
//                    mousePressed = true;
//                    initShootingThread();
//                } else {
                    generalHandler.weaponAskInitShot(x, y);
//                }
                break;
            case 3:
                generalHandler.weaponAskInitReload();
                break;
        }
        x = event.getX();
        y = event.getY();
    }

    public void mouseDragged(MouseEvent e) {
        generalHandler.weaponAskInitShot(x, y);
        x = e.getX();
        y = e.getY();
    }

    public void mouseReleased(MouseEvent e) {
        mousePressed = false;
    }

    public void mouseMoved(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}
