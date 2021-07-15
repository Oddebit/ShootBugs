package com.od.game.handlers;

import com.od.game.ID;
import com.od.game.objects.GameObjects;
import lombok.Getter;

import java.awt.*;
import java.util.LinkedList;

@Getter
public abstract class Handler<H extends GameObjects> {

    protected LinkedList<H> handled = new LinkedList<>();
    protected ID responsibility;

    public Handler(ID responsibility) {
        this.responsibility = responsibility;
    }

    public void tick() {
        handled.forEach(GameObjects::tick);
    }

    public void render(Graphics2D graphics) {
        handled.forEach(h -> h.render(graphics));
    }

    public void add(H toAdd) {
        handled.add(toAdd);
    }
}
