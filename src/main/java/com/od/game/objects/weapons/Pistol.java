package com.od.game.objects.weapons;

import com.od.game.Game;

public class Pistol extends Weapon {

    public Pistol(Game game) {
        super(Type.Pistol, game);
        this.damage = 6;
        this.maxMagMunition = 10;
        this.reshotTimeMillis = 0;
        this.reloadTime = 1_500;
        this.range = 350;
        this.speed = 10;
        this.totalMunition = maxMagMunition;
    }

    @Override
    public void askInitShot(float mouseX, float mouseY) {
        super.askInitShot(mouseX, mouseY);
        totalMunition++;
    }
}
