package com.od.objects;

import com.od.game.Handler;

public class Pistol extends Weapon {

    public Pistol(GameObject owner, Handler handler) {
        super(Type.Pistol, owner, handler);
        this.damage = 5;
        this.magazine = 10;
        this.munitionLeft = magazine;
        this.reloadTime = 3;
        this.range = 200;
    }
}
