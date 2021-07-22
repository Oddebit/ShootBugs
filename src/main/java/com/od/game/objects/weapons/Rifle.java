package com.od.game.objects.weapons;

import com.od.game.threads.BurstThread;
import lombok.Getter;

public class Rifle extends Weapon {
//Environ 50DPS

    @Getter
    private final BurstThread burstThread;

    public Rifle() {
        super(WeaponType.RIFLE,
                600, 8, 12, 6,
                25, 100,
                0, 100, 3_000);
        this.burstThread = new BurstThread();
    }

    public void askInitBurst() {
        if (hasMagMunitionLeft() && !isReloading() && !isBursting()) {

            burstThread.start();
        }
    }

    public void stopBurst() {
        burstThread.finish();
    }

    public boolean isBursting() {
        return !burstThread.isFinished();
    }

    public void retarget(double x, double y) {
        shotThread.setTarget(x, y);
    }
}
