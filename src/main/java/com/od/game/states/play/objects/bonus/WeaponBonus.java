package com.od.game.states.play.objects.bonus;

import com.od.game.states.play.objects.weapons.Weapon;
import lombok.Getter;

import static com.od.game.data.ColorData.HERO_ORANGE;

@Getter
public class WeaponBonus extends Bonus{

    Weapon.WeaponType weaponType;

    public WeaponBonus(Weapon.WeaponType weaponType) {
        super(BonusType.WEAPON);
        this.weaponType = weaponType;
        this.color = HERO_ORANGE;
        this.name = weaponType.getName();
    }
}
