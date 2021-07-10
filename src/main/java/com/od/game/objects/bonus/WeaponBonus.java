package com.od.game.objects.bonus;

import com.od.game.handlers.ObjectHandler;
import com.od.game.objects.creatures.Hero;
import com.od.game.objects.weapons.Weapon;

import static com.od.game.data.ColorData.HERO_ORANGE;

public class WeaponBonus extends Bonus{

    Weapon.Type weaponType;

    public WeaponBonus(ObjectHandler objectHandler, Weapon.Type weaponType) {
        super(objectHandler);
        this.weaponType = weaponType;
        this.color = HERO_ORANGE;
        this.name = weaponType.toString();
    }

    public void getBonus(Hero hero) {
        hero.refillWeapon(weaponType);
    }
}
