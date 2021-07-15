package com.od.input;

import com.od.game.handlers.GeneralHandler;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;

public class MouseWheelInput extends MouseAdapter {

    private final GeneralHandler generalHandler;

    public MouseWheelInput(GeneralHandler generalHandler) {
        this.generalHandler = generalHandler;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent event) {
        int scroll = event.getWheelRotation();
        int increment = scroll / Math.abs(scroll);
        generalHandler.weaponSetNextActiveWeapon(increment);
    }
}
