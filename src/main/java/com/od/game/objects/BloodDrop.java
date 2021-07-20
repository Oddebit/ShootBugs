package com.od.game.objects;

import com.od.game.ID;
import com.od.game.handlers.threads.StartedFinishedThread;
import com.od.game.util.GeomUtil;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class BloodDrop extends GameObject {


    private final int radiusSpawn = 50;

    private final long dryTimeMillis = 60_000;
    private final BloodDropThread bloodDropThread;

    private final Random random = new Random();

    public BloodDrop(double x, double y, int diameter) {
        super(x, y, diameter, diameter, ID.BLOOD_DROP);

        this.bloodDropThread = new BloodDropThread(dryTimeMillis);
        bloodDropThread.start();

        setRandomPosition();
        this.shape = new Ellipse2D.Float();
        shape.setFrame(getFrame());
    }

    public void setRandomPosition() {

        float dx;
        float dy;

        do {
            dx = random.nextFloat() * 2 * radiusSpawn - radiusSpawn;
            dy = random.nextFloat() * 2 * radiusSpawn - radiusSpawn;
        } while (Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2)) > radiusSpawn);


        GeomUtil.translate(position, dx, dy);
    }

    @Override
    public void tick() {
        super.tick();
        updateColor();
    }

    private void updateColor() {
        double shade = GeomUtil.clamp(255d * getThreadState(), 0, 255);
        this.color = new Color((int)shade, 0, 0);
    }

    private double getThreadState() {
        return (dryTimeMillis - bloodDropThread.getTimeMillis()) / (double) dryTimeMillis;
    }

    public boolean isOver() {
        return bloodDropThread.isFinished();
    }

    public class BloodDropThread extends StartedFinishedThread {

        public BloodDropThread(long dryTimeMillis) {
            super(dryTimeMillis);
        }
    }
}
