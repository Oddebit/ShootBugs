package com.od.game.objects.weapons;

import com.od.game.Game;
import com.od.game.objects.projectiles.ShotgunProjectilesLauncher;

public class Shotgun extends Weapon {
    //20DPS
    private int reloadCounter = 0;

    public Shotgun(Game game) {
        super(Type.Shotgun, game);
        this.damage = 6;
        this.maxMagMunition = 8;
        this.reshotTimeMillis = 800;
        this.reloadTimeMillis = 800;
        this.range = 250;
        this.speed = 8;
        this.refillMunition = 40;
        this.shotTimeMillis = 200;
    }

    @Override
    public void shoot() {

        shootSound();

        new ShotgunProjectilesLauncher(xShot, yShot, owner, objectHandler);

        this.totalMunition--;
    }

    @Override
    public double getReloadState() {

//        long period = ChronoUnit.SECONDS.between(lastInitReload, Instant.now());
        long period = 0;
        return ((float) maxMagMunition + (float) period / reloadTimeMillis - 1) / (float) maxMagMunition;
    }

    //fixme
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
