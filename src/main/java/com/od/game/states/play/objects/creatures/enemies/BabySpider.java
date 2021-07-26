package com.od.game.states.play.objects.creatures.enemies;

import com.od.game.data.DimensionData;
import com.od.game.util.GeomUtil;

import java.util.Random;

public class BabySpider extends Enemy {

    public BabySpider(double angle, double x, double y) {
        super(EnemyType.BABY_SPIDER, 15, 2.5d, 5);

        setPosition(x, y);
        setVelocity(Math.cos(angle) * speed, Math.sin(angle) * speed);
    }

    public BabySpider(double x, double y) {
        super(EnemyType.BABY_SPIDER, 15, 2.5d, 5);

        setPosition(x, y);
        double angle = new Random().nextDouble() * 2 * Math.PI;
        setVelocity(Math.cos(angle) * speed, Math.sin(angle) * speed);
    }

    public BabySpider() {
        super(EnemyType.BABY_SPIDER, 15, 2.5d, 5);

        setStartingPosition();
        double angle = new Random().nextDouble() * 2 * Math.PI;
        setVelocity(Math.cos(angle) * speed, Math.sin(angle) * speed);
    }

    @Override
    public void move() {
        if (GeomUtil.clamp(getX(), 0, DimensionData.REAL_WIDTH) != getX()) setVelX(getVelX() * -1);
        if (GeomUtil.clamp(getY(), 0, DimensionData.REAL_HEIGHT) != getY()) setVelY(getVelY() * -1);

        GeomUtil.translate(position, velocity);
    }
}
