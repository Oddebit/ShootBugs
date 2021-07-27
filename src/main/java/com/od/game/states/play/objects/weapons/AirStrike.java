package com.od.game.states.play.objects.weapons;

import com.od.game.states.play.objects.munition.AirStrikeMunition;
import com.od.game.states.play.objects.munition.Munition;

public class AirStrike extends Weapon {

    public AirStrike() {
        super(WeaponType.AIR_STRIKE, 1, 2, 2000, 2000, 2000);
    }

    @Override
    public void askInitShot() {
        super.askInitShot();
        shootSound();
    }

    @Override
    public void shoot() {
        magMunition--;
        totalMunition--;
        shotThread.done();
    }

    @Override
    public Munition getMunition() {
        return new AirStrikeMunition();
    }
}
