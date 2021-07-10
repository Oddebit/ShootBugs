package com.od.input;

import com.od.game.ID;
import com.od.game.handlers.ObjectHandler;
import com.od.game.objects.GameObject;
import com.od.game.objects.creatures.Hero;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;

public class MouseWheelInput extends MouseAdapter {

    ObjectHandler handler;
    Hero hero;

    public MouseWheelInput(ObjectHandler handler) {
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
        hero.setNextActiveWeapon(scroll);
    }
}
