package com.od.objects;

import com.od.game.ObjectHandler;
import com.od.game.SurroundingsHandler;

public class Spider extends Enemy {

    int miniSpiders = 4;

    public Spider(ObjectHandler objectHandler, SurroundingsHandler surroundingsHandler, DashBoard dashBoard, Hero hero) {
        super(objectHandler, surroundingsHandler, dashBoard, hero);
        this.maxHP = 50;
        this.HP = maxHP;
        this.speed = 1;
        this.diameter = 60;
    }

    @Override
    public void tick() {

        float deltaX = x + diameter / 2 - hero.getX() - hero.getW() / 2;
        float deltaY = y + diameter / 2 - hero.getY() - hero.getH() / 2;
        float distance = (float) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

        velocityX = -speed * deltaX / distance;
        velocityY = -speed * deltaY / distance;

        x += velocityX;
        y += velocityY;

        collision();

        if (HP <= 0) {
            dashBoard.addKill();
            objectHandler.removeObject(this);
        }

        if (HP <= 0) {
            for (int i = 0; i < miniSpiders; i++) {
                objectHandler.addObject(
                        new BabySpider(objectHandler, surroundingsHandler, dashBoard, hero, Math.PI * 2 / miniSpiders * i, x, y));
            }
        }
    }
}