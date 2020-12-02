package com.od.game;

import com.od.objects.GameObject;
import com.od.objects.Hero;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyInput extends KeyAdapter {

    ObjectHandler handler;
    Hero hero;

    public KeyInput(ObjectHandler handler) {
        this.handler = handler;
        for (int i = 0; i < handler.objects.size(); i++) {
            GameObject tempObject = handler.objects.get(i);
            if (tempObject.getId() == ID.Hero) {
                hero = (Hero)tempObject;
                break;
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent event) {
        int key = event.getKeyCode();

        switch (key) {
            case KeyEvent.VK_UP:
                hero.setVelocityY(-hero.getSpeed());
                break;
            case KeyEvent.VK_DOWN:
                hero.setVelocityY(+hero.getSpeed());
                break;
            case KeyEvent.VK_LEFT:
                hero.setVelocityX(-hero.getSpeed());
                break;
            case KeyEvent.VK_RIGHT:
                hero.setVelocityX(+hero.getSpeed());
                break;
            case KeyEvent.VK_NUMPAD0:
                hero.getActiveWeapon().reload();
                break;
            case KeyEvent.VK_SHIFT:
                hero.setActiveWeapon(1);
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
                hero.setVelocityY(0);
            case KeyEvent.VK_DOWN:
                hero.setVelocityY(0);
            case KeyEvent.VK_RIGHT:
                hero.setVelocityX(0);
            case KeyEvent.VK_LEFT:
                hero.setVelocityX(0);
        }
    }
}
