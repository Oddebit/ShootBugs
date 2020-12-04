package com.od.objects;

import com.od.game.Game;
import com.od.game.ObjectHandler;
import com.od.game.ID;

import java.awt.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static com.od.game.Game.*;

public class Hero extends GameObject {

    private ObjectHandler oHandler;
    private Game game;

    private static final float diameter = 51;
    private final float speed = 5;
    private final float range = 500;

    ArrayList<Weapon> arsenal = new ArrayList<>();
    Weapon activeWeapon;

    public int maxHP = 200;
    private int HP;

    public Hero(ObjectHandler oHandler, Game game) {
        super(WIDTH_CENTER, HEIGHT_CENTER, diameter, diameter, ID.Hero);
        this.oHandler = oHandler;
        this.game = game;
        this.HP = maxHP;

        Weapon firstWeapon = new Pistol(this, oHandler);
        this.addWeapon(firstWeapon);
        activeWeapon = firstWeapon;
    }

    @Override
    public void tick() {

        if(HP <= 0) {
            game.setState(State.GameOver);
        }

        x += velocityX;
        y += velocityY;

        x = Game.clamp(x, 0, REAL_WIDTH - diameter);
        y = Game.clamp(y, 0, REAL_HEIGHT - diameter);

        collision();
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(new Color(255, (int)Game.clamp(HP, 0, 255), 0));
        graphics.drawOval((int) x, (int) y, (int) diameter, (int) diameter);

        float reloadState;
        if(!activeWeapon.isReloading) {
            reloadState = (float)activeWeapon.magMunition / activeWeapon.magazine;
        } else if (activeWeapon.totalMunitions > 0){
            long period = ChronoUnit.MILLIS.between(activeWeapon.lastReload, Instant.now());
            reloadState = period/(float)(activeWeapon.reloadTime);
        } else {
            reloadState = 0;
        }

        int newDiameter = (int) (reloadState * diameter);
        graphics.fillOval((int) (x + diameter/2 - newDiameter/2), (int) (y + diameter/2 - newDiameter/2), newDiameter, newDiameter);
    }

    public void collision() {
        for (int i = 0; i < oHandler.objects.size(); i++) {
            GameObject tempObject = oHandler.objects.get(i);

            if (getBounds().intersects(tempObject.getBounds())) {

                if (tempObject.getId() == ID.Projectile) {
                    if (((Projectile) tempObject).getShooter() != this) {
                        oHandler.removeObject(tempObject);
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

    public void setActiveWeapon(int increment) {
        Weapon tempWeapon;
        for (int i = 0; i < arsenal.size(); i++) {
            tempWeapon = arsenal.get(i);
            if(tempWeapon == activeWeapon) {
                int index = i + increment;
                while (index >= arsenal.size()) index = index - arsenal.size();
                activeWeapon = arsenal.get(index);
                break;
            }
        }
    }

    public Weapon getActiveWeapon() {
        return activeWeapon;
    }

    public void addWeapon(Weapon weapon) {
        oHandler.addObject(weapon);
        this.arsenal.add(weapon);
    }
}
