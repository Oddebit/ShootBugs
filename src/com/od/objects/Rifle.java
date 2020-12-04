package com.od.objects;

import com.od.game.ObjectHandler;

public class Rifle extends Weapon{
//Environ 50DPS
    public Rifle(GameObject owner, ObjectHandler handler) {
        super(Type.Rifle, owner, handler);
        this.damage = 8;
        this.maxMagMunition = 25;
        this.magMunition = maxMagMunition;
        this.reloadTime = 4000;
        this.range = 600;
        this.maxTotalMunition = 100;
        this.totalMunition = maxTotalMunition;
    }
}
