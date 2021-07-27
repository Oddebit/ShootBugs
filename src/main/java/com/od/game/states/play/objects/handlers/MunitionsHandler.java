package com.od.game.states.play.objects.handlers;

import com.od.game.states.play.objects.GameObject;
import com.od.game.states.play.objects.munition.Munition;
import com.od.game.states.play.objects.weapons.Weapon;
import com.od.game.util.GeomUtil;

import java.awt.geom.Point2D;
import java.util.stream.IntStream;

public class MunitionsHandler extends PlayHandler<Munition> {

    public MunitionsHandler() {
        super(GameObject.ID.MUNITION);
    }

    public void createMunition(Weapon weapon, Point2D position, Point2D target) {

        if (weapon.getWeaponType() == Weapon.WeaponType.SHOTGUN) createShotgunMunition(weapon, position, target);
        else addProjectile(weapon, position, target);
    }

    public void createShotgunMunition(Weapon weapon, Point2D position, Point2D target) {

        IntStream.rangeClosed(-2, 2)
                .forEach(n -> {

                    Point2D vector = GeomUtil.getRotatedVector(position, target, n * Math.PI / 30);
                    Point2D newTarget = GeomUtil.getPoint(position, vector);
                    addProjectile(weapon, position, newTarget);
                });
    }

    private void addProjectile(Weapon weapon, Point2D position, Point2D target) {
        Munition munition = weapon.getMunition();
        munition.setTrajectory(GeomUtil.copyOf(position), target);
        handled.add(munition);
    }

    public void removeOverProjectiles() {
        handled.removeIf(Munition::isOver);
    }
}
