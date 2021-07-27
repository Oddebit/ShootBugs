package com.od.game.states.play.objects.weapons;

public class Shotgun extends Weapon {
    //20DPS
    private int reloadCounter = 0;

    public Shotgun() {
        super(WeaponType.SHOTGUN,
                250, 8, 4.8, 2,
                8, 40,
                100, 800, 800);
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
    protected void initReload() {
        reloadThread.start();
    }

    //fixme :: to tesy
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
