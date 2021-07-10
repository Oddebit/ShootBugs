package com.od.game.handlers;

import com.od.game.objects.GameObject;

import java.awt.*;
import java.util.LinkedList;

public class Handler<H extends GameObject> {

    LinkedList<H> toHandle = new LinkedList<>();

    public void tick() {
        toHandle.forEach(GameObject::tick);
    }

    public void render(Graphics graphics) {
        toHandle.forEach(h -> h.render(graphics));
    }
}
