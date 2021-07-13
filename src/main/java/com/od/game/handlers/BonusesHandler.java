package com.od.game.handlers;

import com.od.game.ID;
import com.od.game.objects.bonus.Bonus;
import com.od.game.objects.creatures.Hero;

public class BonusesHandler extends Handler<Bonus> {
    public BonusesHandler(GeneralHandler generalHandler) {
        super(generalHandler, ID.BONUS);
    }

    @Override
    public void check() {
        toHandle.removeIf(Bonus::isOver);

        Hero hero = getHero();

        toHandle.stream()
                .filter(bonus -> bonus.intersects(hero))
                //fixme: shouldn't give a hero to a bonus
                .forEach(projectile -> projectile.getBonus(hero));
    }
}
