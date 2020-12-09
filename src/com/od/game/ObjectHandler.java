package com.od.game;

import com.od.objects.GameObject;
import com.od.objects.Hero;
import com.od.objects.ToxicArea;

import java.awt.*;
import java.util.LinkedList;
import java.util.Random;

public class ObjectHandler {

    public LinkedList<GameObject> objects = new LinkedList<>();
    Random random = new Random();
    Hero hero;
    ToxicArea toxicArea;

    public ObjectHandler() {
        for (int i = 0; i < objects.size(); i++) {
            GameObject tempObject = objects.get(i);
            if (tempObject.getId() == ID.Hero) {
                this.hero = (Hero) tempObject;
            }
            if (tempObject.getId() == ID.ToxicArea) {
                this.toxicArea = (ToxicArea) tempObject;
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
        if (toxicArea != null) {
            objects.remove(toxicArea);
            this.objects.add(toxicArea);
        }
    }

    public void removeObject(GameObject object) {
        this.objects.remove(object);
    }
}
