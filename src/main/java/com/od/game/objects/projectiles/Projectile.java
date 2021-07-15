package com.od.game.objects.projectiles;

import com.od.game.ID;
import com.od.game.data.ColorData;
import com.od.game.objects.GameObjects;
import com.od.game.objects.weapons.Weapon;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Projectile extends GameObjects {

    private final Weapon weapon;
    private final float speed;
    private final int damage;
    private final float range;
    private final float calibre;

    private float distanceLeft;
    private final float targetX;
    private final float targetY;

    public Projectile(Weapon weapon, float x, float y, float targetX, float targetY) {
        super(x, y, weapon.getCalibre(), weapon.getCalibre(), ID.PROJECTILE);

        this.weapon = weapon;
        this.speed = weapon.getSpeed();
        this.damage = weapon.getDamage();
        this.range = weapon.getRange();
        this.calibre = weapon.getCalibre();

        this.color = ColorData.PROJECTILE_YELLOW;

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
        super.tick();
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
}
