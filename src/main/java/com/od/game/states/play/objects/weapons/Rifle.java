package com.od.game.states.play.objects.weapons;

import com.od.game.states.play.objects.munition.Munition;
import com.od.game.states.play.objects.munition.RifleMunition;
import com.od.game.states.play.threads.BurstThread;
import lombok.Getter;

public class Rifle extends Weapon {
//Environ 50DPS

    @Getter
    private final BurstThread burstThread;

    public Rifle() {
        super(WeaponType.RIFLE,25, 75, 0, 100, 3_000);
        this.burstThread = new BurstThread();
    }

    public void askInitBurst() {
        if (hasMagMunitionLeft() && isNotReloading() && !isBursting()) {

            burstThread.start();
        }
    }

    public void stopBurst() {
        burstThread.finish();
    }

    public boolean isBursting() {
        return !burstThread.isFinished();
    }

    @Override
    public Munition getMunition() {
        return new RifleMunition();
    }

    public void retarget(double x, double y) {
        shotThread.setTarget(x, y);
    }
}
