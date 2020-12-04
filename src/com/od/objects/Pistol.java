package com.od.objects;

import com.od.game.ObjectHandler;

public class Pistol extends Weapon {

    public Pistol(GameObject owner, ObjectHandler handler) {
        super(Type.Pistol, owner, handler);
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
