package com.od.objects;

import com.od.game.ID;
import com.od.game.ObjectHandler;
import com.od.game.SurroundingsHandler;

import java.time.Instant;

public class AirStrike extends Weapon {

    private int timeToEffectiveShoot;
    private boolean isCalled;

    public AirStrike(GameObject owner, ObjectHandler objectHandler, SurroundingsHandler surroundingsHandler) {
        super(Type.AirStrike, owner, objectHandler, surroundingsHandler);
        this.maxMagMunition = 1;
        this.magMunition = maxMagMunition;
        this.reshotTime = 20_000;
        this.reloadTime = 20_000;
        this.maxTotalMunition = 1;
        this.totalMunition = maxTotalMunition;
        this.timeToEffectiveShoot = 2;
    }

    @Override

    public void shoot(float mouseX, float mouseY) {
        if (magMunition > 0) {
            if (!isReloading) {
                shootSound();
                this.magMunition--;
                this.totalMunition--;
                this.lastShot = Instant.now();
                isCalled = true;
            }
        } else {
            reload();
        }
    }

    private void effectiveShoot() {
        for (int i = 0; i < objectHandler.objects.size(); i++) {

            GameObject tempObject = objectHandler.objects.get(i);
            if (tempObject.getId() == ID.Enemy) {
                for (int j = 0; j < ((Enemy) tempObject).getHP(); j++) {
                    surroundingsHandler.addSurrounding(new Blood(tempObject.getX(), tempObject.getY(), surroundingsHandler));
                }
                ((Enemy) tempObject).setHP((0));
            }
        }
    }

    public void reload() {
        if (!isReloading && totalMunition > 0) {
            this.lastReload = Instant.now();
            this.isReloading = true;
        } else if (totalMunition <= 0 && !isCalled) {
            ((Hero) owner).setActiveWeapon(1);
            ((Hero) owner).removeWeapon(this);
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (isCalled && Instant.now().isAfter(lastShot.plusSeconds(timeToEffectiveShoot))) {
            effectiveShoot();
            isCalled = false;
        }

        if (magMunition <= 0 && !isReloading) {
            reload();
        }
        if (Instant.now().isAfter(lastReload.plusMillis(reloadTime)) && isReloading) {
            effectiveReload();
            isReloading = false;
        }
    }
}
