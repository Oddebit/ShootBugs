package com.od.game.objects;

import com.od.game.ID;

import java.awt.*;
import java.time.Instant;
import java.util.Random;

public class BloodDrop extends GameObject {


    private final int radiusSpawn = 50;
    private final int diameter;

    private Instant lastTime;
    private long timeLeft = 60;

    private final Random random = new Random();

    public BloodDrop(float x, float y) {
        super(x, y, 1, 1, ID.BLOOD);

        this.diameter = random.nextInt(32);

        this.lastTime = Instant.now();

        setRandomPosition();
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
        if (lastTime.plusSeconds(1).isBefore(Instant.now())) {
            lastTime = lastTime.plusSeconds(1);
            timeLeft -= 1;
        }
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(new Color(4 * (int) timeLeft, 0, 0));
        graphics.fillOval((int) x, (int) y, diameter, diameter);
    }

    public boolean isOver() {
        return timeLeft <= 0;
    }
}
