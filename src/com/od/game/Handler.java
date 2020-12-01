package com.od.game;

import com.od.objects.GameObject;
import com.od.objects.Hero;
import com.od.objects.ID;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class Handler {

    public LinkedList<GameObject> objects = new LinkedList<>();
    Random random = new Random();
    Hero hero;

    public Handler() {
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
