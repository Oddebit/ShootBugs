package com.od.game.objects.creatures.enemies;

import com.od.game.objects.creatures.Hero;

public class Bug extends Enemy {

    private final Hero hero;
    public Bug(Hero hero) {
        super();
        this.hero = hero;
        this.speed = 1.8f;
        this.diameter = 24;
        this.maxHP = 20;
        this.hp = maxHP;
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
}
