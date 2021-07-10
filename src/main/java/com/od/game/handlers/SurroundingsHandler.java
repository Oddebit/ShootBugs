package com.od.game.handlers;

import com.od.game.objects.GameObject;

import java.awt.*;
import java.util.LinkedList;

public class SurroundingsHandler {

    public LinkedList<GameObject> surroundings = new LinkedList<>();

    public void tick() {
        surroundings.forEach(GameObject::tick);
    }

    public void render(Graphics graphics) {
            surroundings.forEach(surrounding -> surrounding.render(graphics));
    }

    public void addSurrounding(GameObject object) {
        this.surroundings.add(object);
    }

    public void removeSurrounding(GameObject object) {
        this.surroundings.remove(object);
    }
}
