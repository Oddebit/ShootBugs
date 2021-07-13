package com.od.game.objects.threads;

import com.od.game.objects.weapons.Weapon;

import java.time.Instant;

public class ShotThread extends Thread {

    private final Weapon weapon;
    private final long shotTimeMillis;
    private final long reshotTimeMillis;

    public ShotThread(Weapon weapon) {
        this.weapon = weapon;
        this.shotTimeMillis = weapon.getShotTimeMillis();
        this.reshotTimeMillis = weapon.getReshotTimeMillis();
    }

    @Override
    public void run() {

        try {
            wait(weapon.getShotTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Instant start = Instant.now();
        while (start.plusMillis(shotTimeMillis).isAfter(Instant.now())){

        }
        weapon.shoot();
        while (start.plusMillis(shotTimeMillis).plusMillis(reshotTimeMillis).isAfter(Instant.now())){

        }
    }
}
