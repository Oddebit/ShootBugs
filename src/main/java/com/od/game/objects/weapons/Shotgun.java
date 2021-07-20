package com.od.game.objects.weapons;

public class Shotgun extends Weapon {
    //20DPS
    private int reloadCounter = 0;

    public Shotgun() {
        super(WeaponType.SHOTGUN,
                250, 8, 8, 2,
                8, 40,
                100, 800, 800);
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
