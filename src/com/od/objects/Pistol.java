package com.od.objects;

import com.od.game.ObjectHandler;

public class Pistol extends Weapon {

    public Pistol(GameObject owner, ObjectHandler handler) {
        super(Type.Pistol, owner, handler);
        this.damage = 5;
        this.maxMagMunition = 10;
        this.magMunition = maxMagMunition;
        this.reloadTime = 1_800;
        this.range = 200;
        this.maxTotalMunition = 1;
        this.totalMunition = maxTotalMunition;
    }

    @Override
    public void shoot(float mouseX, float mouseY) {
        super.shoot(mouseX, mouseY);
        totalMunition++;
    }
}
