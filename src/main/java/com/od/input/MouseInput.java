package com.od.input;

import com.od.game.ID;
import com.od.game.handlers.ObjectHandler;
import com.od.game.objects.GameObject;
import com.od.game.objects.creatures.Hero;
import com.od.game.objects.weapons.Weapon;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelListener;

public class MouseInput extends MouseAdapter implements MouseWheelListener {

    ObjectHandler handler;
    Hero hero;
    float x;
    float y;
    boolean mousePressed;

    public MouseInput(ObjectHandler handler) {
        this.handler = handler;
        for (int i = 0; i < handler.objects.size(); i++) {
            GameObject tempObject = handler.objects.get(i);
            if (tempObject.getId() == ID.Hero) {
                hero = (Hero) tempObject;
                break;
            }
        }
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
