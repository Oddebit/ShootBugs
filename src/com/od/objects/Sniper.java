package com.od.objects;

import com.od.game.ObjectHandler;
import com.od.game.SurroundingsHandler;

public class Sniper extends Weapon {
    public Sniper(GameObject owner, ObjectHandler objectHandler, SurroundingsHandler surroundingsHandler) {
        super(Type.Sniper, owner, objectHandler, surroundingsHandler);
        this.damage = 15;
        this.maxMagMunition = 1;
        this.magMunition = maxMagMunition;
        this.reshotTime = 3_000;
        this.reloadTime = 3_000;
        this.range = 900;
        this.speed = 15;
        this.maxTotalMunition = 20;
        this.totalMunition = maxTotalMunition;
    }
}
