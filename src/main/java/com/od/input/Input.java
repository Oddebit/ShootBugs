package com.od.input;

import com.od.game.states.Dispatcher;
import lombok.Data;

@Data
public class Input<G extends Dispatcher> {

    private MouseInput<G> mouseInput;
    private KeyInput<G> keyInput;

    public Input(MouseInput<G> mouseInput, KeyInput<G> keyInput) {
        this.mouseInput = mouseInput;
        this.keyInput = keyInput;
    }
}
