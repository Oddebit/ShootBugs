package com.od.game;

import com.od.objects.GameObject;
import com.od.objects.Hero;
import com.od.objects.Weapon;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class MouseInput extends MouseAdapter {

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

    @Override
    public void mouseWheelMoved(MouseWheelEvent event) {
        int scroll = event.getWheelRotation();
        hero.setActiveWeapon(scroll);
    }

    private void initShootingThread() {
        new Thread() {
            public void run() {
                do {
                    hero.getActiveWeapon().shoot(x, y);
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
                    hero.getActiveWeapon().shoot(x, y);
                }
                break;
            case 2:
                hero.setActiveWeapon(1);
                break;
            case 3:
                hero.getActiveWeapon().reload();
                break;
        }
        x = event.getX();
        y = event.getY();
    }

    public void mouseDragged(MouseEvent e) {
        hero.getActiveWeapon().shoot(x, y);
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
}
