package com.od.game.objects.creatures.enemies;

import com.od.game.Game;
import com.od.game.objects.creatures.Hero;

public class BabySpider extends Enemy {

    public BabySpider(Hero hero, double angle, float x, float y) {
        super();
        this.speed = 2.5f;
        this.maxHP = 5;
        this.hp = maxHP;
        this.diameter = 15;

        this.x = x;
        this.y = y;
        this.velocityX = (float) Math.cos(angle) * speed;
        this.velocityY = (float) Math.sin(angle) * speed;
    }

    @Override
    public void tick() {

        move();
    }

    @Override
    public void move() {
        if (0 >= x || x >= Game.REAL_WIDTH) velocityX *= -1;
        if (0 >= y || y >= Game.REAL_HEIGHT) velocityY *= -1;

        x += velocityX;
        y += velocityY;
    }
}
