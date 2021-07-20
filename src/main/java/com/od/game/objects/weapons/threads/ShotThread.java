package com.od.game.objects.weapons.threads;

import lombok.Getter;

import java.awt.geom.Point2D;

@Getter
public class ShotThread extends StartedReadyDoneFinishedThread {

    private Point2D.Double target;

    public ShotThread(long beforeShotMillis, long afterShotMillis) {
        super(beforeShotMillis, afterShotMillis);
    }

    public void setTarget(double x, double y) {
        target = new Point2D.Double(x, y);
    }
}
