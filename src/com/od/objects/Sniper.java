package com.od.objects;

import com.od.game.ObjectHandler;
import com.od.game.SurroundingsHandler;

public class Sniper extends Weapon {
    public Sniper(GameObject owner, ObjectHandler objectHandler, SurroundingsHandler surroundingsHandler) {
        super(Type.Sniper, owner, objectHandler, surroundingsHandler);
        this.damage = 15;
        this.maxMagMunition = 1;
        this.magMunition = maxMagMunition;
        this.reloadTime = 2_500;
        this.range = 1_000;
        this.maxTotalMunition = 20;
        this.totalMunition = maxTotalMunition;
    }
}
