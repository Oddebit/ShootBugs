package com.od.input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseMotionInput extends MouseAdapter {

    private final MouseInput mouseInput;

    public MouseMotionInput(MouseInput mouseInput) {
        this.mouseInput = mouseInput;
    }

    public void mouseMoved(MouseEvent e) {
        mouseInput.setX(e.getX());
        mouseInput.setY(e.getY());
    }
}
