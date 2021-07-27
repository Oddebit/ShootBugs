package com.od.game.states.play.objects.munition;

import com.od.game.data.ColorData;
import com.od.game.data.SoundData;
import com.od.game.states.play.objects.GameObject;
import com.od.game.states.play.objects.weapons.Grenade;
import com.od.game.states.play.threads.ExplosionThread;
import com.od.game.util.GeomUtil;
import com.od.output.SoundPlayer;

import java.awt.geom.Point2D;

public class GrenadeMunition extends Munition {

    private Point2D initialPosition;
    private final ExplosionThread explosionThread;

    public GrenadeMunition() {
        super(0, 40, 1, 1000, false);

        this.explosionThread = new ExplosionThread(1000, 500);
        explosionThread.start();
    }

    @Override
    public void setTrajectory(Point2D position, Point2D target) {
        this.initialPosition = position;
        setPosition(GeomUtil.copyOf(initialPosition));
        setTarget(target);

        double distance = GeomUtil.getDistance(initialPosition, target);
        setDistanceLeft(distance);

        setSpeed(distance/100);
        setVelocity(GeomUtil.getVector(initialPosition, target, getSpeed()));
    }

    @Override
    public void tick() {
        super.tick();
        explosionThread.tick();

        if (isSet()) {
            int minDiameter = 20;
            int maxDiameter = 30;
            double distance = GeomUtil.getDistance(initialPosition, getTarget());
            double distanceTraveled = distance - getDistanceLeft();
            double diameter =
                    -4 * (maxDiameter - minDiameter) / Math.pow(distance, 2) * Math.pow(distanceTraveled, 2)
                            + 4 * (maxDiameter - minDiameter) / distance * distanceTraveled
                            + 5;
            setDimension(diameter, diameter);
        }
        else if(isAskingToExplode()) {
            SoundPlayer.playSound(SoundData.getSoundLocation(Grenade.class, SoundData.Action.SHOOT).get());
            explosionThread.done();
        }
        else if(isExploding()) {
            setSpeed(0);
            setColor(ColorData.EXPLOSION_YELLOW);
            if (explosionThread.getTimeFromDoneMillis() % 250 < 125) {
                setDimension(140, 140);
            }
            else {
                setDimension(0, 0);
            }
        }
    }

    @Override
    public boolean intersects(GameObject gameObject) {
        return super.intersects(gameObject) && isExploding();
    }

    @Override
    public boolean isOver() {
        return super.isOver() && hasExploded();
    }

    private boolean isSet() {
        return explosionThread.isStarted();
    }

    private boolean isAskingToExplode() {
        return explosionThread.isReady();
    }

    private boolean isExploding() {
        return explosionThread.isDone();
    }

    private boolean hasExploded() {
        return explosionThread.isFinished();
    }
}