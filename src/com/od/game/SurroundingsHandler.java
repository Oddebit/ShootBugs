package com.od.game;

import com.od.objects.GameObject;
import com.od.objects.Hero;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class SurroundingsHandler {

    public LinkedList<GameObject> surroundings = new LinkedList<>();
    Random random = new Random();

    public void tick() {
        for (int i = 0; i < surroundings.size(); i++) {
            GameObject tempObject = surroundings.get(i);
            tempObject.tick();
        }
    }

    public void render(Graphics graphics) {
        for (int i = 0; i < surroundings.size(); i++) {
            GameObject tempObject = surroundings.get(i);
            tempObject.render(graphics);
        }
    }

    public void addSurrounding(GameObject object) {
        this.surroundings.add(object);
    }

    public void removeSurrounding(GameObject object) {
        this.surroundings.remove(object);
    }
}
