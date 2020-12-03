package com.od.objects;

import com.od.game.ObjectHandler;

public class Rifle extends Weapon{

    public Rifle(GameObject owner, ObjectHandler handler) {
        super(Type.Rifle, owner, handler);
        this.damage = 8;
        this.magazine = 30;
        this.magMunition = magazine;
        this.reloadTime = 5;
        this.range = 300;
        this.maxTotalMunition = 90;
        this.totalMunitions = maxTotalMunition;
    }
}
