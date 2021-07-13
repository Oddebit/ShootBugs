package com.od.input;

import com.od.game.handlers.ObjectHandler;
import com.od.game.objects.creatures.Hero;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;

public class MouseWheelInput extends MouseAdapter {

    ObjectHandler objectHandler;
    Hero hero;

    public MouseWheelInput(ObjectHandler objectHandler) {
        this.objectHandler = objectHandler;
        this.hero = objectHandler.getHero();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent event) {
        int scroll = event.getWheelRotation();
        hero.setNextActiveWeapon(scroll);
    }
}
