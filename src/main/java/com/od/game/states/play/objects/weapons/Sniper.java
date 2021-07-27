package com.od.game.states.play.objects.weapons;

import com.od.game.states.play.objects.munition.Munition;
import com.od.game.states.play.objects.munition.SniperMunition;

public class Sniper extends Weapon {
    public Sniper() {
        super(WeaponType.SNIPER,1, 15, 0, 0, 3000);
    }

    @Override
    public Munition getMunition() {
        return new SniperMunition();
    }
}
