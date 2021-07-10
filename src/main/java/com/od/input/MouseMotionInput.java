package com.od.input;

import com.od.game.ID;
import com.od.game.handlers.ObjectHandler;
import com.od.game.objects.GameObject;
import com.od.game.objects.creatures.Hero;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseMotionInput extends MouseAdapter {

    private MouseInput mouseInput;
    private ObjectHandler handler;
    private Hero hero;

    public MouseMotionInput(ObjectHandler handler, MouseInput mouseInput) {
        this.handler = handler;
        for (int i = 0; i < handler.objects.size(); i++) {
            GameObject tempObject = handler.objects.get(i);
            if (tempObject.getId() == ID.Hero) {
                hero = (Hero) tempObject;
                break;
            }
        }
        this.mouseInput = mouseInput;
    }

    public void mouseMoved(MouseEvent e) {
        mouseInput.setX(e.getX());
        mouseInput.setY(e.getY());
    }
}
