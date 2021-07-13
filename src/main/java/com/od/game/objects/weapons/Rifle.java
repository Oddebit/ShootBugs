package com.od.game.objects.weapons;

import com.od.game.Game;

public class Rifle extends Weapon {
//Environ 50DPS
    public Rifle(Game game) {
        super(Type.Rifle, game);
        this.damage = 8;
        this.maxMagMunition = 25;
        this.reshotTimeMillis = 100;
        this.reloadTimeMillis = 3_500;
        this.range = 600;
        this.speed = 12;
        this.refillMunition = 100;
    }
}
