package com.od.game.objects.weapons;

import com.od.game.Game;
import com.od.game.ID;
import com.od.game.objects.Blood;
import com.od.game.objects.GameObject;
import com.od.game.objects.creatures.enemies.Enemy;

public class AirStrike extends Weapon {

    public AirStrike(Game game) {
        super(Type.AirStrike, game);
        this.maxMagMunition = 1;
        this.reshotTimeMillis = 20_000;
        this.reloadTime = 20_000;
        this.refillMunition = 1;
        this.shotTimeMillis = 2;
    }

    @Override
    public void initShot(float xShot, float yShot) {

        super.initShot(xShot, yShot);
        shootSound();
    }

    @Override
    public void shoot() {
        for (int i = 0; i < objectHandler.objects.size(); i++) {

            GameObject tempObject = objectHandler.objects.get(i);
            if (tempObject.getId() == ID.Enemy) {
                for (int j = 0; j < ((Enemy) tempObject).getHP(); j++) {
                    surroundingsHandler.addSurrounding(new Blood(tempObject.getX(), tempObject.getY(), surroundingsHandler));
                }
                ((Enemy) tempObject).setHP((0));
            }
        }
    }
}
