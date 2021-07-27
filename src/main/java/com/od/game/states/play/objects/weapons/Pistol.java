package com.od.game.states.play.objects.weapons;

public class Pistol extends Weapon {

    public Pistol() {
        super(WeaponType.PISTOL,
                350, 6, 6, 4,
                10, 0,
                0, 50, 1_500);
        this.totalMunition = 100;
    }

    @Override
    public void askInitShot() {
        super.askInitShot();
        totalMunition++;
    }
}
