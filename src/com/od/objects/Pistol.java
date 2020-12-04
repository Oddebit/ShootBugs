package com.od.objects;

import com.od.game.ObjectHandler;

public class Pistol extends Weapon {

    public Pistol(GameObject owner, ObjectHandler handler) {
        super(Type.Pistol, owner, handler);
        this.damage = 5;
        this.magazine = 10;
        this.magMunition = magazine;
        this.reloadTime = 1_800;
        this.range = 200;
        this.maxTotalMunition = 1;
        this.totalMunitions = maxTotalMunition;
    }

    @Override
    public void shoot(float mouseX, float mouseY) {
        super.shoot(mouseX, mouseY);
        totalMunitions++;
    }
}
