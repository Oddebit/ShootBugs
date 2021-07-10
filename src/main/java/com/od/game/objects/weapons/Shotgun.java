package com.od.game.objects.weapons;

import com.od.game.Game;
import com.od.game.objects.projectiles.MultiPojectile;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class Shotgun extends Weapon {
    //20DPS
    private int reloadCounter = 0;

    public Shotgun(Game game) {
        super(Type.Shotgun, game);
        this.damage = 6;
        this.maxMagMunition = 8;
        this.reshotTimeMillis = 800;
        this.reloadTime = 800;
        this.range = 250;
        this.speed = 8;
        this.refillMunition = 40;
        this.shotTimeMillis = 200;
    }

    @Override
    public void shoot() {

        shootSound();

        new MultiPojectile(xShot, yShot, owner, objectHandler, surroundingsHandler);

        this.totalMunition--;
        this.lastShot = Instant.now();
        this.isShooting = false;
    }

    @Override
    public double getReloadState() {

        long period = ChronoUnit.SECONDS.between(lastInitReload, Instant.now());
        return ((float) maxMagMunition + (float) period / reloadTime - 1) / (float) maxMagMunition;
    }

    public void processReload() {
//        if (magMunition < maxMagMunition && reloadCounter < maxMagMunition) {
//            lastInitReload = Instant.now();
//            reloadSound();
//            magMunition++;
//            reloadCounter++;
//        } else {
//            isReloading = false;
//            reloadCounter = 0;
//        }
    }

}
