package com.od.game.objects.creatures.enemies;

import com.od.game.objects.creatures.Hero;

public class Spider extends Enemy {

    private final Hero hero;
    private final int miniSpiders = 4;


    public Spider(Hero hero) {
        super();
        this.hero = hero;
        this.maxHP = 50;
        this.hp = maxHP;
        this.speed = 1;
        this.diameter = 60;
    }

    @Override
    public void tick() {

        move();
    }

    @Override
    public void move() {
        float deltaX = x + diameter / 2 - hero.getX() - hero.getW() / 2;
        float deltaY = y + diameter / 2 - hero.getY() - hero.getH() / 2;
        float distance = (float) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

        velocityX = -speed * deltaX / distance;
        velocityY = -speed * deltaY / distance;

        x += velocityX;
        y += velocityY;
    }

//    @Override
//    public void die() {
//        super.die();
//        for (int i = 0; i < miniSpiders; i++) {
//            objectHandler.addObject(
//                    new BabySpider(objectHandler, surroundingsHandler, dashBoard, hero, Math.PI * 2 / miniSpiders * i, x, y));
//        }
//    }
}