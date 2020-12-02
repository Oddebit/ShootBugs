package com.od.objects;

import com.od.game.Handler;

import java.awt.*;

public class Projectile extends GameObject {

    Handler handler;

    private static final float diameter = 5;
    private static final float speed = 10;
    private final GameObject shooter;
    private final int damage;

    private float distance;
    private final float targetX;
    private final float targetY;

    public Projectile(float x, float y, float targetX, float targetY, GameObject shooter, int damage, Handler handler) {
        super(x, y, diameter, diameter, ID.Projectile);
        this.damage = damage;
        this.shooter = shooter;
        this.handler = handler;

        //initial triangle
        float deltaX = x - targetX;
        float deltaY = y - targetY;
        distance = (float) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

        //conversion
        deltaX *= shooter.getRange()/distance;
        deltaY *= shooter.getRange()/distance;

        //within range
        this.targetX = x - deltaX;
        this.targetY = y - deltaY;
        distance = shooter.getRange();

        velocityX = - deltaX * speed/shooter.getRange();
        velocityY = - deltaY * speed/shooter.getRange();
    }

    @Override
    public void tick() {
        if (distance <= 0) {
            handler.removeObject(this);
        }

        x += velocityX;
        y += velocityY;
        distance -= speed;
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(new Color(255, 255, 120));
        graphics.fillOval((int)x, (int)y, (int)diameter, (int)diameter);

    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, (int)diameter, (int)diameter);
    }

    public GameObject getShooter() {
        return shooter;
    }

    public int getDamage() {
        return damage;
    }
}
