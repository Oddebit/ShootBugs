package com.od.game.objects;

import com.od.game.ID;
import com.od.game.handlers.threads.StartedFinishedThread;
import com.od.game.util.GeomUtil;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

public class BloodDrop extends GameObjects {


    private final int radiusSpawn = 50;

    private final long dryTimeMillis = 60_000;
    private final BloodDropThread bloodDropThread;

    private final Random random = new Random();

    public BloodDrop(float x, float y, int diameter) {
        super(x, y, diameter, diameter, ID.BLOOD_DROP);

        this.bloodDropThread = new BloodDropThread(dryTimeMillis);
        bloodDropThread.start();

        setRandomPosition();
        this.shape = new Ellipse2D.Float(x, y, diameter, diameter);
    }

    public void setRandomPosition() {

        float tempX;
        float tempY;

        do {
            tempX = random.nextFloat() * 2 * radiusSpawn - radiusSpawn;
            tempY = random.nextFloat() * 2 * radiusSpawn - radiusSpawn;
        } while (Math.sqrt(Math.pow(tempX, 2) + Math.pow(tempY, 2)) > radiusSpawn);

        this.x += tempX;
        this.y += tempY;
    }

    @Override
    public void tick() {
        super.tick();
        updateColor();
    }

    private void updateColor() {
        float shade = GeomUtil.clamp(255f * getThreadState(), 0, 255);
        this.color = new Color((int)shade, 0, 0);
    }

    private float getThreadState() {
        return (dryTimeMillis - bloodDropThread.getTimeMillis()) / (float) dryTimeMillis;
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
