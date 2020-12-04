package com.od.game;

import com.od.objects.GameObject;
import com.od.objects.Hero;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class MouseInput extends MouseAdapter {

    ObjectHandler handler;
    Hero hero;

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
    public void mouseClicked(MouseEvent event) {
    }

    @Override
    public void mousePressed(MouseEvent event) {
        int click = event.getButton();

        switch (click) {
            case 1:
                int mouseX = event.getX();
                int mouseY = event.getY();
                hero.getActiveWeapon().shoot(mouseX, mouseY);
                break;
            case 2:
                hero.setActiveWeapon(1);
                break;
            case 3:
                hero.getActiveWeapon().reload();
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent event) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent event) {
        int scroll = event.getWheelRotation();
        hero.setActiveWeapon(scroll);
    }
}
