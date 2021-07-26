package com.od.input.mouse;

import com.od.game.states.Dispatcher;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;

public class MouseInput<G extends Dispatcher> extends MouseAdapter implements MouseWheelListener, MouseMotionListener {

    protected final G generalHandler;

    protected MouseInput(G generalHandler) {
        this.generalHandler = generalHandler;
    }
}
