package com.od.game.states.play.objects.weapons;

import com.od.game.states.play.objects.munition.GrenadeMunition;
import com.od.game.states.play.objects.munition.Munition;

public class Grenade extends Weapon{
    public Grenade() {
        super(WeaponType.GRENADE, 1, 3, 100, 0, 0);
    }

    @Override
    public Munition getMunition() {
        return new GrenadeMunition();
    }

    @Override
    public void shoot() {

        magMunition--;
        totalMunition--;
        shotThread.done();
    }
}
