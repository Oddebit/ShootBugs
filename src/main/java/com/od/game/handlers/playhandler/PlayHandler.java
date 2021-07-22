package com.od.game.handlers.playhandler;

import com.od.game.ID;
import com.od.game.objects.GameObject;
import lombok.Getter;

import java.awt.*;
import java.util.LinkedList;

@Getter
public abstract class PlayHandler<H extends GameObject> {

    protected LinkedList<H> handled = new LinkedList<>();
    protected ID responsibility;

    public PlayHandler(ID responsibility) {
        this.responsibility = responsibility;
    }

    public void tick() {
        handled.forEach(GameObject::tick);
    }

    public void render(Graphics2D graphics) {
        handled.forEach(h -> h.render(graphics));
    }

    public void add(H toAdd) {
        handled.add(toAdd);
    }
}
