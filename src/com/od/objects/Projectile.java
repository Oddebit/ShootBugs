package com.od.objects;

import com.od.game.ObjectHandler;
import com.od.game.ID;
import com.od.game.SurroundingsHandler;

import java.awt.*;

public class Projectile extends GameObject {

    ObjectHandler objectHandler;
    SurroundingsHandler surroundingsHandler;

    private static final float diameter = 5;
    private static final float speed = 10;
    private Hero shooter;
    private Weapon weapon;
    private final int damage;
    private final float range;

    private float distance;
    private final float targetX;
    private final float targetY;

    public Projectile(float targetX, float targetY, GameObject shooter, ObjectHandler objectHandler, SurroundingsHandler surroundingsHandler) {
        super(shooter.x + shooter.w/2, shooter.y + shooter.h/2, diameter, diameter, ID.Projectile);
        this.objectHandler = objectHandler;
        this.surroundingsHandler = surroundingsHandler;
        this.shooter = (Hero) shooter;
        this.weapon = ((Hero) shooter).getActiveWeapon();
        this.damage = this.shooter.getActiveWeapon().damage;
        this.range = ((Hero) shooter).activeWeapon.range;

        //initial triangle
        float deltaX = targetX - x;
        float deltaY = targetY - y;
        distance = (float) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

        //conversion
        deltaX *= range/distance;
        deltaY *= range/distance;

        //within range
        this.targetX = x + deltaX;
        this.targetY = y + deltaY;
        distance = range;

        velocityX = deltaX * speed/range;
        velocityY = deltaY * speed/range;
    }

    @Override
    public void tick() {
        if (distance <= 0) {
            objectHandler.removeObject(this);
        }

        x += velocityX;
        y += velocityY;
        distance -= speed;

        collision();
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

    public void collision() {
        for (int i = 0; i < objectHandler.objects.size(); i++) {
            GameObject tempObject = objectHandler.objects.get(i);

            if (getBounds().intersects(tempObject.getBounds())) {

                if (tempObject.getId() == ID.Enemy) {
                    for (int j = 0; j < damage; j++) {
                        surroundingsHandler.addSurrounding(new Blood(x, y, surroundingsHandler));
                    }
                    ((Enemy)tempObject).setHP(((Enemy)tempObject).getHP() - damage);
                    if (weapon.getType() != Weapon.Type.Sniper) {
                        objectHandler.removeObject(this);
                    }
                }
            }
        }
    }


    public GameObject getShooter() {
        return shooter;
    }

    public int getDamage() {
        return damage;
    }
}
