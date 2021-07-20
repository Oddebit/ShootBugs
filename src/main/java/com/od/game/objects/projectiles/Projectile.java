package com.od.game.objects.projectiles;

import com.od.game.ID;
import com.od.game.data.ColorData;
import com.od.game.objects.GameObject;
import com.od.game.objects.weapons.Weapon;
import com.od.game.util.GeomUtil;
import lombok.Getter;
import lombok.Setter;

import java.awt.geom.Point2D;

@Getter
@Setter
public class Projectile extends GameObject {

    private final Weapon weapon;
    private final double speed;
    private final int damage;
    private final double range;
    private final double calibre;

    private double distanceLeft;
    private final Point2D.Double target;

    public Projectile(Weapon weapon, double x, double y, double targetX, double targetY) {
        super(x, y, weapon.getCalibre(), weapon.getCalibre(), ID.PROJECTILE);

        this.weapon = weapon;
        this.speed = weapon.getSpeed();
        this.damage = weapon.getDamage();
        this.range = weapon.getRange();
        this.calibre = weapon.getCalibre();

        this.color = ColorData.PROJECTILE_YELLOW;

        //initial triangle
        double dx = targetX - x;
        double dy = targetY - y;
        distanceLeft = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));

        //conversion
        dx *= range / distanceLeft;
        dy *= range / distanceLeft;

        //within range
        this.target = new Point2D.Double(x + dx, y + dy);
        distanceLeft = range;

        velocity.setLocation(dx * speed / range, dy * speed / range);
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
}
