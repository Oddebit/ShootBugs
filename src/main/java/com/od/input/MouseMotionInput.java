package com.od.input;

import com.od.game.handlers.ObjectHandler;
import com.od.game.objects.creatures.Hero;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseMotionInput extends MouseAdapter {

    private MouseInput mouseInput;
    private ObjectHandler objectHandler;
    private Hero hero;

    public MouseMotionInput(ObjectHandler objectHandler, MouseInput mouseInput) {
        this.objectHandler = objectHandler;
        this.hero = objectHandler.getHero();
        this.mouseInput = mouseInput;
    }

    public void mouseMoved(MouseEvent e) {
        mouseInput.setX(e.getX());
        mouseInput.setY(e.getY());
    }
}
