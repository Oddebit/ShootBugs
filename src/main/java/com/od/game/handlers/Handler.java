package com.od.game.handlers;

import com.od.game.ID;
import com.od.game.objects.GameObject;
import lombok.Getter;

import java.awt.*;
import java.util.LinkedList;

@Getter
public abstract class Handler<H extends GameObject> {

    protected LinkedList<H> toHandle = new LinkedList<>();
    protected GeneralHandler generalHandler;
    protected ID responsibility;

    public Handler(GeneralHandler generalHandler, ID responsibility) {
        this.generalHandler = generalHandler;
        this.responsibility = responsibility;
    }

    public void tick() {
        check();
        toHandle.forEach(GameObject::tick);
    }

    public abstract void check();

    public void render(Graphics graphics) {
        toHandle.forEach(h -> h.render(graphics));
    }

    public void add(H toAdd) {
        toHandle.add(toAdd);
    }

    public void remove(H toRemove) {
        toHandle.remove(toRemove);
    }

}
