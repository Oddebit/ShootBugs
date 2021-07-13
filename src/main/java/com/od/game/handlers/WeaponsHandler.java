package com.od.game.handlers;

import com.od.game.ID;
import com.od.game.objects.weapons.Weapon;

public class WeaponsHandler extends Handler<Weapon> {
    public WeaponsHandler(GeneralHandler generalHandler) {
        super(generalHandler, ID.WEAPON);
    }

    @Override
    public void check() {

    }
}
