package com.od.objects;

import com.od.game.Game;
import com.od.game.Handler;

import java.awt.*;

public class Projectile extends GameObject {

    Handler handler;
    private static final float diameter = 5;
    private static final float speed = 10;
    private float targetX;
    private float targetY;

    public Projectile(float x, float y, float targetX, float targetY, Handler handler) {
        super(x, y, diameter, diameter, ID.Projectile);
        this.handler = handler;
        this.targetX = targetX;
        this.targetY = targetY;

        float deltaX = x + Hero.getDiameter() / 2 - targetX;
        float deltaY = y + Hero.getDiameter() / 2 - targetY;
        float distance = (float) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

        this.targetX *= Hero.getRange()/distance;
        this.targetY *= Hero.getRange()/distance;
        deltaX *= Hero.getRange()/distance;
        deltaY *= Hero.getRange()/distance;

        velocityX = -speed * deltaX / distance;
        velocityY = -speed * deltaY / distance;
    }

    @Override
    public void tick() {
        if (x >= targetX && y >= targetY) {
            handler.removeObject(this);
        }

        x += velocityX;
        y += velocityY;
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(new Color(255, 255, 120));
        graphics.fillOval((int)x, (int)y, (int)diameter, (int)diameter);

    }

    @Override
    public Rectangle getBounds() {
        return null;
    }
}
