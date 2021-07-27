package com.od.game.states.play.objects.munition;

import com.od.game.data.ColorData;
import com.od.game.states.play.objects.GameObject;
import com.od.game.util.GeomUtil;
import lombok.Getter;
import lombok.Setter;

import java.awt.geom.Point2D;

@Getter
@Setter
public abstract class Munition extends GameObject {

    private double speed;
    private final int damage;
    private final double calibre;

    private boolean isStoppable;

    private final double range;
    private double distanceLeft;
    private Point2D target;

    public Munition(double speed, int damage, double calibre, double range, boolean isStoppable) {
        super(GeomUtil.getSquare(0), GeomUtil.getSquare(calibre), ID.MUNITION);

        this.color = ColorData.PROJECTILE_YELLOW;

        this.speed = speed;
        this.damage = damage;
        this.calibre = calibre;

        this.isStoppable = isStoppable;

        this.range = range;
        this.distanceLeft = range;
    }

    public void setTrajectory(Point2D position, Point2D target) {
        setPosition(position);
        Point2D trajectory = GeomUtil.getVector(position, target, range);
        setTarget(GeomUtil.getPoint(position, trajectory));
    }

    @Override
    public void tick() {
        super.tick();
        move();
    }

    private void move() {
        Point2D velocity = GeomUtil.getVector(position, target, speed);
        setVelocity(velocity);
        GeomUtil.translate(position, getVelocity());
        distanceLeft -= speed;
    }

    public boolean isOver() {
        return distanceLeft <= 0;
    }
}
