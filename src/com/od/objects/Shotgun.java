package com.od.objects;

import com.od.game.ObjectHandler;
import com.od.game.SurroundingsHandler;

import java.time.Instant;

public class Shotgun extends Weapon {
//20DPS
    private int reloadCounter = 0;

    public Shotgun(GameObject owner, ObjectHandler objectHandler, SurroundingsHandler surroundingsHandler) {
        super(Type.Shotgun, owner, objectHandler, surroundingsHandler);
        this.damage = 6;
        this.maxMagMunition = 8;
        this.magMunition = maxMagMunition;
        this.reloadTime = 800;
        this.range = 250;
        this.speed = 8;
        this.maxTotalMunition = 40;
        this.totalMunition = maxTotalMunition;
    }

    public void shoot(float mouseX, float mouseY) {
        if (magMunition > 0) {
            new MultiPojectile(mouseX, mouseY, owner, objectHandler, surroundingsHandler);
            shootSound();
            this.magMunition--;
            this.totalMunition--;
        }
    }

    public void reload() {
        if (magMunition < maxMagMunition && totalMunition > 0) {
            this.lastReload = Instant.now();
            this.isReloading = true;
        }
    }

    private void effectiveReload() {
        if (magMunition < maxMagMunition && reloadCounter < maxMagMunition) {
            lastReload = Instant.now();
            reloadSound();
            magMunition++;
            reloadCounter++;
        } else if (totalMunition <=0) {
            ((Hero) owner).setActiveWeapon(1);
            ((Hero) owner).removeWeapon(this);
        } else {
            isReloading = false;
            reloadCounter = 0;
        }
    }

    @Override
    public void tick() {
        if (lastReload.plusMillis(reloadTime).isBefore(Instant.now()) && isReloading) {
            effectiveReload();
        }
        if (magMunition <= 0 && !isReloading) {
            reload();
        }
    }
}
