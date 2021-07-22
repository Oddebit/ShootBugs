package com.od.game.handlers.playhandler;

import com.od.game.ID;
import com.od.game.objects.projectiles.Projectile;
import com.od.game.objects.weapons.Weapon;
import com.od.game.util.GeomUtil;

import java.awt.geom.Point2D;
import java.util.stream.IntStream;

public class ProjectilesHandler extends PlayHandler<Projectile> {

    private final int shotgunProjectiles = 6;

    public ProjectilesHandler() {
        super(ID.PROJECTILE);
    }

    public void createProjectile(Weapon weapon, double x, double y, double targetX, double targetY) {

        handled.add(new Projectile(weapon, x, y, targetX, targetY));
    }

    public void createProjectile(Weapon weapon, Point2D position, Point2D target) {

        handled.add(new Projectile(weapon, position.getX(), position.getY(), target.getX(), target.getY()));
    }

    public void createShotgunProjectiles(Weapon weapon, Point2D position, Point2D target) {

        Point2D vector = GeomUtil.getTrigonometricVector(position, target);
        double angle = GeomUtil.getAngle(vector);

        IntStream.rangeClosed(-2, 2)
                .forEach( n -> {

            double tempAngle = angle + n * Math.PI / 27;
            Point2D newTarget = new Point2D.Double(position.getX() + Math.cos(tempAngle) * 1000d,
                    position.getY() + Math.sin(tempAngle) * 1000d);
            createProjectile(weapon, position, newTarget);
        });
    }

    public void checkProjectiles() {
        handled.removeIf(Projectile::isOver);
    }
}
