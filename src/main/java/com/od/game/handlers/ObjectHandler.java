package com.od.game.handlers;

import com.od.game.ID;
import com.od.game.objects.GameObject;
import com.od.game.objects.creatures.Hero;

import java.awt.*;
import java.util.LinkedList;

public class ObjectHandler {

    public LinkedList<GameObject> objects = new LinkedList<>();
    Hero hero;

    public ObjectHandler() {
        for (int i = 0; i < objects.size(); i++) {
            GameObject tempObject = objects.get(i);
            if (tempObject.getId() == ID.Hero) {
                this.hero = (Hero) tempObject;
            }
        }
    }

    public void tick() {
        for (int i = 0; i < objects.size(); i++) {
            GameObject tempObject = objects.get(i);
            tempObject.tick();
        }
    }

    public void render(Graphics graphics) {
        for (int i = 0; i < objects.size(); i++) {
            GameObject tempObject = objects.get(i);
            tempObject.render(graphics);
        }
    }

    public void addObject(GameObject object) {

        this.objects.add(object);
    }

    public void removeObject(GameObject object) {
        this.objects.remove(object);
    }
}
