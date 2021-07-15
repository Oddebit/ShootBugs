package com.od.game.objects.creatures.enemies;

public class Bug extends Enemy {

    public Bug() {
        super(EnemyType.BUG);
        this.speed = 1.8f;
        this.diameter = 24;
        this.maxHP = 20;
        this.hp = maxHP;
    }

    @Override
    public void move() {

        x += velocityX;
        y += velocityY;
    }
}
