package com.od.game.states.play.objects.handlers;

import com.od.game.states.play.objects.GameObject;
import com.od.game.states.play.objects.projectiles.GrenadeProjectile;
import com.od.game.states.play.objects.projectiles.Projectile;
import com.od.game.states.play.objects.weapons.Weapon;
import com.od.game.util.GeomUtil;

import java.awt.geom.Point2D;
import java.util.stream.IntStream;

public class ProjectilesHandler extends PlayHandler<Projectile> {

    private final int shotgunProjectiles = 6;

    public ProjectilesHandler() {
        super(GameObject.ID.PROJECTILE);
    }

    public void createProjectile(Weapon weapon, Point2D position, Point2D target) {

        handled.add(new Projectile(weapon, GeomUtil.getCopy(position), target));
    }

    public void createShotgunProjectiles(Weapon weapon, Point2D position, Point2D target) {

        Point2D vector = GeomUtil.getTrigonometricVector(position, target);
        double angle = GeomUtil.getAngle(vector);

        IntStream.rangeClosed(-2, 2)
                .forEach(n -> {

            double tempAngle = angle + n * Math.PI / 27;
            Point2D newTarget = new Point2D.Double(position.getX() + Math.cos(tempAngle) * 1000d,
                    position.getY() + Math.sin(tempAngle) * 1000d);
            createProjectile(weapon, position, newTarget);
        });
    }

    public void createGrenadeProjectiles(Weapon weapon, Point2D position, Point2D target) {

        handled.add(new GrenadeProjectile(weapon, position, target));
    }

    public void removeOverProjectiles() {

        handled.removeIf(Projectile::isOver);
    }
}
