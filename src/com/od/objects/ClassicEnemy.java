package com.od.objects;

import com.od.game.ObjectHandler;
import com.od.game.SurroundingsHandler;

public class ClassicEnemy extends Enemy {


    public ClassicEnemy(ObjectHandler objectHandler, SurroundingsHandler surroundingsHandler, DashBoard dashBoard, Hero hero) {
        super(objectHandler, surroundingsHandler, dashBoard, hero);
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
    }
}
