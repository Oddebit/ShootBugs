package com.od.game.objects.weapons;

public class Pistol extends Weapon {

    public Pistol() {
        super(WeaponType.PISTOL,
                350, 6, 10, 4,
                10, 0,
                0, 50, 1_500);
        this.totalMunition = 100;
    }

    @Override
    public void askInitShot(float targetX, float targetY) {
        super.askInitShot(targetX, targetY);
        totalMunition++;
    }
}
