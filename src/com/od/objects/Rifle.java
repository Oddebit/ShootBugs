package com.od.objects;

import com.od.game.Handler;

public class Rifle extends Weapon{

    public Rifle(GameObject owner, Handler handler) {
        super(Type.Rifle, owner, handler);
        this.damage = 5;
        this.magazine = 30;
        this.munitionLeft = magazine;
        this.reloadTime = 5;
        this.range = 300;
    }
}
