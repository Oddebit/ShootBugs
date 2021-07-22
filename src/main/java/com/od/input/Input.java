package com.od.input;

import com.od.game.handlers.GeneralHandler;
import com.od.input.key.KeyInput;
import com.od.input.mouse.MouseInput;
import lombok.Data;

@Data
public class Input<G extends GeneralHandler> {

    private MouseInput<G> mouseInput;
    private KeyInput<G> keyInput;
}
