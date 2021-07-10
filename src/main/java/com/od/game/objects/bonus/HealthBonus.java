package com.od.game.objects.bonus;

import com.od.game.handlers.ObjectHandler;
import com.od.game.objects.creatures.Hero;

import static com.od.game.data.ColorData.HEALTH_TURQUOISE;

public class HealthBonus extends Bonus{

    public HealthBonus(ObjectHandler objectHandler) {
        super(objectHandler);
        this.color = HEALTH_TURQUOISE;
        this.name = "Health";
    }

    @Override
    public void getBonus(Hero hero) {
        hero.resetHP();
    }
}