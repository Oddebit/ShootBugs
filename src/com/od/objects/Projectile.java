package com.od.objects;

import com.od.game.ObjectHandler;
import com.od.game.ID;

import java.awt.*;

public class Projectile extends GameObject {

    ObjectHandler handler;

    private static final float diameter = 5;
    private static final float speed = 10;
    private final Hero shooter;
    private final int damage;

    private float distance;
    private final float targetX;
    private final float targetY;

    public Projectile(float targetX, float targetY, GameObject shooter, ObjectHandler handler) {
        super(shooter.x + shooter.w/2, shooter.y + shooter.h/2, diameter, diameter, ID.Projectile);
        this.handler = handler;
        this.shooter = (Hero) shooter;
        this.damage = this.shooter.getActiveWeapon().damage;

        //initial triangle
        float deltaX = targetX - x;
        float deltaY = targetY - y;
        distance = (float) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

        //conversion
        deltaX *= shooter.getRange()/distance;
        deltaY *= shooter.getRange()/distance;

        //within range
        this.targetX = x + deltaX;
        this.targetY = y + deltaY;
        distance = shooter.getRange();

        velocityX = deltaX * speed/shooter.getRange();
        velocityY = deltaY * speed/shooter.getRange();
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
