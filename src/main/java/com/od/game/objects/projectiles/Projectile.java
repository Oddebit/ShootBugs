package com.od.game.objects.projectiles;

import com.od.game.ID;
import com.od.game.data.ColorData;
import com.od.game.handlers.GeneralHandler;
import com.od.game.objects.GameObject;
import com.od.game.objects.creatures.Hero;
import com.od.game.objects.weapons.Weapon;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class Projectile extends GameObject {

    private GeneralHandler objectHandler;

    private static final float diameter = 5;
    private final float speed;
    private final Hero shooter;
    private final Weapon weapon;
    private final int damage;
    private final float range;

    private float distanceLeft;
    private final float targetX;
    private final float targetY;

    public Projectile(float targetX, float targetY, GameObject shooter, GeneralHandler generalHandler) {
        super(shooter.getX() + shooter.getW() / 2, shooter.getY() + shooter.getH() / 2, diameter, diameter, ID.PROJECTILE);
        this.objectHandler = generalHandler;
        this.shooter = (Hero) shooter;
        this.weapon = this.shooter.getActiveWeapon();
        this.speed = weapon.getSpeed();
        this.damage = this.shooter.getActiveWeapon().getDamage();
        this.range = this.shooter.getActiveWeapon().getRange();

        //initial triangle
        float deltaX = targetX - x;
        float deltaY = targetY - y;
        distanceLeft = (float) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

        //conversion
        deltaX *= range / distanceLeft;
        deltaY *= range / distanceLeft;

        //within range
        this.targetX = x + deltaX;
        this.targetY = y + deltaY;
        distanceLeft = range;

        velocityX = deltaX * speed / range;
        velocityY = deltaY * speed / range;
    }

    @Override
    public void tick() {

        move();
    }

    private void move() {
        x += velocityX;
        y += velocityY;
        distanceLeft -= speed;
    }

    public boolean isOver() {
        return distanceLeft <= 0;
    }

    @Override
    public void render(Graphics graphics) {

        graphics.setColor(ColorData.PROJECTILE_YELLOW);
        graphics.fillOval((int) x, (int) y, (int) diameter, (int) diameter);
    }
}
