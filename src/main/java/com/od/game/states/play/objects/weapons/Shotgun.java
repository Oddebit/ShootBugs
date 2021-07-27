package com.od.game.states.play.objects.weapons;

import com.od.game.states.play.objects.munition.Munition;
import com.od.game.states.play.objects.munition.ShotgunMunition;

public class Shotgun extends Weapon {
    //20DPS
    private int reloadCounter = 0;

    public Shotgun() {
        super(WeaponType.SHOTGUN,8, 40, 100, 800, 800);
    }

    public void askInitShot() {

        if (hasMagMunitionLeft() && isNotShooting()) {

            shotThread.start();
            reloadThread.done();
        }
    }

    @Override
    public double getReloadState() {

        long period = 0;
        return ((float) maxMagMunition + (float) period / reloadTimeMillis - 1) / (float) maxMagMunition;
    }

    @Override
    public Munition getMunition() {
        return new ShotgunMunition();
    }

    @Override
    protected void initReload() {
        reloadThread.start();
    }

    @Override
    public void reload() {
        reloadSound();
        magMunition++;
        if(magMunition < maxMagMunition) {
            reloadThread.start();
        } else {
            reloadThread.done();
        }
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
