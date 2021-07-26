package com.od.game.states.play.objects.projectiles;

import com.od.game.data.ColorData;
import com.od.game.states.play.objects.creatures.enemies.Enemy;
import com.od.game.states.play.objects.weapons.Weapon;
import com.od.game.util.GeomUtil;

import java.awt.geom.Point2D;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.function.Consumer;

public class GrenadeProjectile extends Projectile{

    private final Point2D initialPosition;
    private final Instant initialTime;

    //todo : cleaner impl
    private Instant explosionTime;

    public GrenadeProjectile(Weapon weapon, Point2D position, Point2D target) {
        super(weapon, position, target);
        this.initialPosition = position;
        this.initialTime = Instant.now();
        this.explosionTime = Instant.MAX.minusSeconds(1000);
        setTarget(target);
        setDistanceLeft(GeomUtil.getDistance(position, target));
    }

    @Override
    public void tick() {
        if(getDistanceLeft() > 0) {

            super.tick();
            int minDiameter = 10;
            int maxDiameter = 30;
            double distance = GeomUtil.getDistance(initialPosition, getTarget());
            double distanceTraveled = distance - getDistanceLeft();
            double diameter =
                    -4 * (maxDiameter - minDiameter) / Math.pow(distance, 2) * Math.pow(distanceTraveled, 2)
                            + 4 * (maxDiameter - minDiameter) / distance * distanceTraveled
                            + 5;
            setDimension(diameter, diameter);
        } else {

            explosionTime = Instant.now();
            setColor(ColorData.EXPLOSION_YELLOW);
            if(ChronoUnit.MILLIS.between(initialTime, Instant.now()) % 100 < 50) {
                setDimension(50, 50);

            } else {
                setDimension(0, 0);
            }
        }
    }

    @Override
    public Consumer<Enemy> getDeathDamage() {
        return enemy -> enemy.removeHp((int) Math.abs(100_000 / Math.pow(GeomUtil.getDistance(this, enemy), 2)));
    }

    @Override
    public boolean isOver() {
        return super.isOver() && explosionTime.plusMillis(1000).isBefore(Instant.now());
    }
}
