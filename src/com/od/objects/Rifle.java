package com.od.objects;

import com.od.game.ObjectHandler;

public class Rifle extends Weapon{

    public Rifle(GameObject owner, ObjectHandler handler) {
        super(Type.Rifle, owner, handler);
        this.damage = 5;
        this.magazine = 30;
        this.munitionLeft = magazine;
        this.reloadTime = 5;
        this.range = 300;
    }
}
