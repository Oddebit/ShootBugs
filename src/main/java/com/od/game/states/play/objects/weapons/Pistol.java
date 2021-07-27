package com.od.game.states.play.objects.weapons;

import com.od.game.states.play.objects.munition.Munition;
import com.od.game.states.play.objects.munition.PistolMunition;

public class Pistol extends Weapon {

    public Pistol() {
        super(WeaponType.PISTOL,10, 0, 0, 50, 1_500);
        this.totalMunition = 100;
    }

    @Override
    public void askInitShot() {
        super.askInitShot();
        totalMunition++;
    }

    @Override
    public Munition getMunition() {
        return new PistolMunition();
    }
}
