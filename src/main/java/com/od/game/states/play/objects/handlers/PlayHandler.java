package com.od.game.states.play.objects.handlers;

import com.od.game.states.play.objects.GameObject;
import lombok.Getter;

import java.awt.*;
import java.util.LinkedList;

@Getter
public abstract class PlayHandler<H extends GameObject> {

    protected LinkedList<H> handled = new LinkedList<>();
    protected GameObject.ID responsibility;

    public PlayHandler(GameObject.ID responsibility) {
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
