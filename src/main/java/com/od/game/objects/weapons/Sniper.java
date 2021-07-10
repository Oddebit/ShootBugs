package com.od.game.objects.weapons;

import com.od.game.Game;

public class Sniper extends Weapon {
    public Sniper(Game game) {
        super(Type.Sniper, game);
        this.damage = 15;
        this.maxMagMunition = 1;
        this.reshotTimeMillis = 3_000;
        this.reloadTime = 3_000;
        this.range = 900;
        this.speed = 15;
        this.refillMunition = 20;
    }
}
