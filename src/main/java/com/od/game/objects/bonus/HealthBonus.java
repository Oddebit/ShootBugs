package com.od.game.objects.bonus;

import static com.od.game.data.ColorData.HEALTH_TURQUOISE;

public class HealthBonus extends Bonus{

    public HealthBonus() {
        super(BonusType.HEALTH);
        this.color = HEALTH_TURQUOISE;
        this.name = "Health";
    }
}