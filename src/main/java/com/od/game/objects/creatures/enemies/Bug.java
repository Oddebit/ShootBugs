package com.od.game.objects.creatures.enemies;

import com.od.game.handlers.ObjectHandler;
import com.od.game.handlers.SurroundingsHandler;
import com.od.game.objects.DashBoard;
import com.od.game.objects.creatures.Hero;

public class Bug extends Enemy {

    public Bug(ObjectHandler objectHandler, SurroundingsHandler surroundingsHandler, DashBoard dashBoard, Hero hero) {
        super(objectHandler, surroundingsHandler, dashBoard, hero);
        this.speed = 1.8f;
        this.maxHP = 20;
        this.HP = maxHP;
    }

    @Override
    public void tick() {

        move();
        askDie();
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
