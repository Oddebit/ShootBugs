package com.od.input;

import com.od.game.handlers.ObjectHandler;
import com.od.game.objects.creatures.Hero;
import com.od.game.objects.weapons.Weapon;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelListener;

public class MouseInput extends MouseAdapter implements MouseWheelListener {

    private ObjectHandler objectHandler;
    private Hero hero;
    private float x;
    private float y;
    private boolean mousePressed;

    public MouseInput(ObjectHandler objectHandler) {
        this.objectHandler = objectHandler;
        this.hero = this.objectHandler.getHero();
    }


    private void initShootingThread() {
        new Thread() {
            public void run() {
                do {
                    hero.getActiveWeapon().askInitShot(x, y);
                } while (mousePressed && hero.getActiveWeapon().getTotalMunition() > 0);
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
                if(hero.getActiveWeapon().getType() == Weapon.Type.Rifle) {
                    mousePressed = true;
                    initShootingThread();
                } else {
                    hero.getActiveWeapon().askInitShot(x, y);
                }
                break;
            case 2:
                hero.setNextActiveWeapon(1);
                break;
            case 3:
                hero.getActiveWeapon().askInitReload();
                break;
        }
        x = event.getX();
        y = event.getY();
    }

    public void mouseDragged(MouseEvent e) {
        hero.getActiveWeapon().askInitShot(x, y);
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
