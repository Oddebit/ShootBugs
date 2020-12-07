package com.od.objects;

import com.od.game.ObjectHandler;
import com.od.game.SurroundingsHandler;

public class Rifle extends Weapon{
//Environ 50DPS
    public Rifle(GameObject owner, ObjectHandler objectHandler, SurroundingsHandler surroundingsHandler) {
        super(Type.Rifle, owner, objectHandler, surroundingsHandler);
        this.damage = 8;
        this.maxMagMunition = 25;
        this.magMunition = maxMagMunition;
        this.reshotTime = 100;
        this.reloadTime = 4000;
        this.range = 600;
        this.speed = 12;
        this.maxTotalMunition = 100;
        this.totalMunition = maxTotalMunition;
    }
}
