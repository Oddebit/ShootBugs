package com.od.objects;

import com.od.game.ObjectHandler;
import com.od.game.SurroundingsHandler;

public class Pistol extends Weapon {

    public Pistol(GameObject owner, ObjectHandler objectHandler, SurroundingsHandler surroundingsHandler) {
        super(Type.Pistol, owner, objectHandler, surroundingsHandler);
        this.damage = 6;
        this.maxMagMunition = 10;
        this.magMunition = maxMagMunition;
        this.reloadTime = 1_500;
        this.range = 350;
        this.maxTotalMunition = maxMagMunition;
        this.totalMunition = maxTotalMunition;
    }

    @Override
    public void shoot(float mouseX, float mouseY) {
        super.shoot(mouseX, mouseY);
        totalMunition++;
    }
}
