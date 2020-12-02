package com.od.objects;

import com.od.game.Game;
import com.od.game.Handler;

import java.awt.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static com.od.game.Game.*;

public class Hero extends GameObject {

    private Handler handler;
    private static final float diameter = 51;
    private final float speed = 5;
    private final float range = 500;

    ArrayList<Weapon> arsenal = new ArrayList<>();
    Weapon activeWeapon;

    private int HP = 100;

    public Hero(Handler handler) {
        super(WIDTH_CENTER, HEIGHT_CENTER, diameter, diameter, ID.Hero);
        this.handler = handler;
        Weapon firstWeapon = new Pistol(this, handler);
        Weapon secondWeapon = new Rifle(this, handler);

        this.addWeapon(firstWeapon);
        this.addWeapon(secondWeapon);
        activeWeapon = firstWeapon;
    }

    @Override
    public void tick() {

        x += velocityX;
        y += velocityY;

        x = Game.clamp(x, 0, REAL_WIDTH - diameter);
        y = Game.clamp(y, 0, REAL_HEIGHT - diameter);

        collision();
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(new Color(255, Math.max(2 * HP, 0), 0));
        graphics.drawOval((int) x, (int) y, (int) diameter, (int) diameter);

        float reloadState;
        if(!activeWeapon.isReloading) {
            reloadState = (float)activeWeapon.munitionLeft / activeWeapon.magazine;
        } else{
            long period = ChronoUnit.SECONDS.between(Instant.now(), activeWeapon.lastReload);
            reloadState = period/(float)(activeWeapon.reloadTime);
        }

        int newDiameter = (int) (reloadState * diameter);
        graphics.fillOval((int) (x + diameter/2 - newDiameter/2), (int) (y + diameter/2 - newDiameter/2), newDiameter, newDiameter);
    }

    public void collision() {
        for (int i = 0; i < handler.objects.size(); i++) {
            GameObject tempObject = handler.objects.get(i);

            if (getBounds().intersects(tempObject.getBounds())) {

                if (tempObject.getId() == ID.Projectile) {
                    if (((Projectile) tempObject).getShooter() != this) {
                        handler.removeObject(tempObject);
                        this.HP -= ((Projectile) tempObject).getDamage();
                    }
                }
            }
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) diameter, (int) diameter);
    }

    public static float getDiameter() {
        return diameter;
    }

    public float getSpeed() {
        return speed;
    }

    public float getRange() {
        return range;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public void setActiveWeapon(int scroll) {
        Weapon tempWeapon;
        for (int i = 0; i < arsenal.size(); i++) {
            tempWeapon = arsenal.get(i);
            if(tempWeapon == activeWeapon) {
                while (i + scroll >= arsenal.size()) i = i + scroll - arsenal.size();
                activeWeapon = arsenal.get(i);
                break;
            }
        }
    }

    public Weapon getActiveWeapon() {
        return activeWeapon;
    }

    public void addWeapon(Weapon weapon) {
        handler.addObject(weapon);
        this.arsenal.add(weapon);
    }
}
