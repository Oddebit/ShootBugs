package com.od.game.handlers;

import com.od.game.ID;
import com.od.game.objects.GameObject;
import lombok.Getter;

import java.awt.*;
import java.util.LinkedList;

@Getter
public class GeneralHandler {


    LinkedList<Handler<GameObject>> handlers = new LinkedList<>();

    public GeneralHandler() {

    }

    public void tick() {
        handlers.forEach(Handler::tick);
    }

    public void render(Graphics graphics) {
        handlers.forEach(handler -> handler.render(graphics));
    }

    public Handler<? extends GameObject> getHandler(ID responsibility) {
        return handlers.stream().filter(handler -> handler.getResponsibility() == responsibility).findFirst().orElseThrow();
    }


}
