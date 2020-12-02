package com.od.objects;

import com.od.game.ID;
import com.od.game.SurroundingsHandler;

import java.awt.*;
import java.time.Instant;
import java.util.Random;

public class Blood extends GameObject{

    Random random= new Random();
    SurroundingsHandler sHandler;
    Instant lastTime;
    long timeLeft = 30;
    int radiusSpawn = 50;
    int diameter;

    public Blood(float x, float y, SurroundingsHandler sHandler) {
        super(x, y, 1, 1, ID.Blood);
        this.sHandler = sHandler;
        this.lastTime = Instant.now();
        this.diameter = random.nextInt(32);
        float tempX;
        float tempY;
        do {
            tempX = random.nextFloat() * 2*radiusSpawn - radiusSpawn;
            tempY = random.nextFloat() * 2*radiusSpawn - radiusSpawn;
        } while (Math.sqrt(Math.pow(tempX, 2) + Math.pow(tempY, 2)) > radiusSpawn);
        this.x += tempX;
        this.y += tempY;
    }

    @Override
    public void tick() {
        if (timeLeft <= 0) {
            sHandler.removeSurrounding(this);
        }

        if (lastTime.plusSeconds(1).isBefore(Instant.now())){
            lastTime = lastTime.plusSeconds(1);
            timeLeft -= 1;
        }
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(new Color(8 * (int) timeLeft, 0, 0));
        graphics.fillOval((int)x, (int)y, diameter, diameter);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, diameter, diameter);
    }
}
