package com.od.game.objects.weapons;

public class Rifle extends Weapon {
//Environ 50DPS
    public Rifle() {
        super(WeaponType.RIFLE,
                600, 8, 12, 6,
                25, 100,
                0, 100, 3_000);
    }
}
