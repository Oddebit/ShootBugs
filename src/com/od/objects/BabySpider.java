package com.od.objects;

import com.od.game.Game;
import com.od.game.ObjectHandler;
import com.od.game.SurroundingsHandler;

public class BabySpider extends Enemy {

    public BabySpider(ObjectHandler objectHandler, SurroundingsHandler surroundingsHandler, DashBoard dashBoard, Hero hero, double angle, float x, float y) {
        super(objectHandler, surroundingsHandler, dashBoard, hero);
        this.speed = 2.5f;
        this.maxHP = 5;
        this.HP = maxHP;
        this.diameter = 15;

        this.x = x;
        this.y = y;
        this.velocityX = (float) Math.cos(angle) * speed;
        this.velocityY = (float) Math.sin(angle) * speed;
    }

    @Override
    public void tick() {
        if (0 >= x || x >= Game.REAL_WIDTH) velocityX *= -1;
        if (0 >= y || y >= Game.REAL_HEIGHT) velocityY *= -1;

        x += velocityX;
        y += velocityY;

        collision();

        if (HP <= 0) {
            dashBoard.addKill();
            objectHandler.removeObject(this);
        }
    }
}
