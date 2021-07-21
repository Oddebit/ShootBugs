package com.od.input;

import com.od.game.Game;
import com.od.game.handlers.GeneralHandler;

public class InputHandler {

    public InputHandler(Game game, GeneralHandler generalHandler){

        MouseInput mouseInput = new MouseInput(generalHandler);
        game.addMouseListener(mouseInput);
        game.addMouseMotionListener(mouseInput);
        game.addMouseWheelListener(mouseInput);

        game.addKeyListener(new KeyInput(generalHandler));
    }
}
