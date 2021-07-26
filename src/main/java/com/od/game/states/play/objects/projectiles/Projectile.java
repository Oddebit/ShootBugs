package com.od.game.states.play.objects.projectiles;

import com.od.game.data.ColorData;
import com.od.game.states.play.objects.GameObject;
import com.od.game.states.play.objects.creatures.enemies.Enemy;
import com.od.game.states.play.objects.weapons.Weapon;
import com.od.game.util.GeomUtil;
import lombok.Getter;
import lombok.Setter;

import java.awt.geom.Point2D;
import java.util.function.Consumer;

@Getter
@Setter
public class Projectile extends GameObject {

    private final Weapon weapon;
    private final double speed;
    private final int damage;
    private final double calibre;

    private final double range;
    private double distanceLeft;
    private Point2D target;

    public Projectile(Weapon weapon, Point2D position, Point2D target) {
        super(position.getX(), position.getY(), weapon.getCalibre(), weapon.getCalibre(), ID.PROJECTILE);

        this.color = ColorData.PROJECTILE_YELLOW;

        this.weapon = weapon;
        this.speed = weapon.getSpeed();
        this.damage = weapon.getDamage();
        this.calibre = weapon.getCalibre();

        this.range = weapon.getRange();
        this.distanceLeft = range;

        Point2D trajectory = GeomUtil.getVector(position, target, range);
        this.target = GeomUtil.getPoint(position, trajectory);

        Point2D velocity = GeomUtil.getVector(position, target, speed);
        setVelocity(velocity);
    }

    @Override
    public void tick() {
        super.tick();
        move();
    }

    private void move() {
        GeomUtil.translate(position, getVelX(), getVelY());
        distanceLeft -= speed;
    }

    public boolean isOver() {
        return distanceLeft <= 0;
    }

    public Consumer<Enemy> getDeathDamage() {
        return enemy -> {};
    };
}
