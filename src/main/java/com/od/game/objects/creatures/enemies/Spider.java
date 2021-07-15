package com.od.game.objects.creatures.enemies;

import lombok.Getter;

@Getter
public class Spider extends Enemy {

    private final int miniSpiders = 4;

    public Spider() {
        super(EnemyType.SPIDER);
        this.maxHP = 50;
        this.hp = maxHP;
        this.speed = 1;
        this.diameter = 60;
    }

    @Override
    public void move() {

        x += velocityX;
        y += velocityY;
    }
}