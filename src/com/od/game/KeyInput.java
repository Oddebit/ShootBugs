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
            case KeyEvent.VK_Z:
                hero.setVelocityY(-hero.getSpeed());
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                hero.setVelocityY(+hero.getSpeed());
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_Q:
                hero.setVelocityX(-hero.getSpeed());
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                hero.setVelocityX(+hero.getSpeed());
                break;
            case KeyEvent.VK_SHIFT:
            case KeyEvent.VK_R:
                hero.getActiveWeapon().reload();
                break;
            case KeyEvent.VK_NUMPAD0:
            case KeyEvent.VK_CONTROL:
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
            case KeyEvent.VK_Z:
                hero.setVelocityY(0);
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                hero.setVelocityY(0);
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_Q:
                hero.setVelocityX(0);
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                hero.setVelocityX(0);
                break;
        }
    }
}
