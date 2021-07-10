package com.od.input;

import com.od.game.Game;
import com.od.game.handlers.ObjectHandler;

public class InputHandler {

    public InputHandler(Game game, ObjectHandler objectHandler){

        MouseInput mouseInput = new MouseInput(objectHandler);
        game.addMouseListener(mouseInput);
        game.addMouseWheelListener(new MouseWheelInput(objectHandler));

        game.addKeyListener(new KeyInput(objectHandler, game));
/*        //fonctionne pas
        this.addMouseMotionListener(new MouseMotionInput(objectHandler, mouseInput));*/
    }
}
