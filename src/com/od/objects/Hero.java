package com.od.objects;

import com.od.game.Game;
import com.od.game.ObjectHandler;
import com.od.game.ID;
import com.od.game.SurroundingsHandler;

import java.awt.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import static com.od.game.Game.*;

public class Hero extends GameObject {

    private ObjectHandler objectHandler;
    SurroundingsHandler surroundingsHandler;
    private Game game;

    private static final float diameter = 51;
    private final float speed = 5;
    private final float range = 500;

    ArrayList<Weapon> arsenal = new ArrayList<>();
    Weapon activeWeapon;

    public int maxHP = 100;
    private int HP;
    private int killCount;

    public Hero(ObjectHandler objectHandler, SurroundingsHandler surroundingsHandler, Game game) {
        super(WIDTH_CENTER, HEIGHT_CENTER, diameter, diameter, ID.Hero);
        this.objectHandler = objectHandler;
        this.surroundingsHandler = surroundingsHandler;
        this.game = game;
        this.HP = maxHP;

        Weapon firstWeapon = new Pistol(this, objectHandler, surroundingsHandler);

        this.addWeapon(firstWeapon);
        activeWeapon = firstWeapon;
    }

    @Override
    public void tick() {

        if (HP <= 0) {
            Game.playSound("sounds/manYawn.wav");
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
        graphics.setColor(new Color(255, (int) Game.clamp(HP, 0, 255), 0));
        graphics.drawOval((int) x, (int) y, (int) diameter, (int) diameter);

        float reloadState;
        if (!activeWeapon.isReloading) {
            reloadState = (float) activeWeapon.magMunition / activeWeapon.maxMagMunition;
        } else if (activeWeapon.totalMunition > 0) {
            long period = ChronoUnit.MILLIS.between(activeWeapon.lastReload, Instant.now());
            if(activeWeapon.getType() == Weapon.Type.Shotgun) {
                reloadState = ((float) activeWeapon.magMunition + (float) period / activeWeapon.reloadTime) / (float) activeWeapon.maxMagMunition;
            } else {
                reloadState = (float) period / activeWeapon.reloadTime;
            }
        } else {
            reloadState = 0;
        }

        int newDiameter = (int) (reloadState * diameter);
        graphics.fillOval((int) (x + diameter / 2 - newDiameter / 2), (int) (y + diameter / 2 - newDiameter / 2), newDiameter, newDiameter);
    }

    public void collision() {
//        for (int i = 0; i < objectHandler.objects.size(); i++) {
//            GameObject tempObject = objectHandler.objects.get(i);
//
//            if (this.intersects(tempObject)) {
//
//                if (tempObject.getId() == ID.Projectile) {
//                    if (((Projectile) tempObject).getShooter() != this) {
//                        objectHandler.removeObject(tempObject);
//                        this.HP -= ((Projectile) tempObject).getDamage();
//                    }
//                }
//            }
//        }
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
            if (tempWeapon == activeWeapon) {
                int index = i + increment;
                while (index >= arsenal.size()) index = index - arsenal.size();
                activeWeapon = arsenal.get(index);
                activeWeapon.lastActivation = Instant.now();
                break;
            }
        }
    }

    public Weapon getActiveWeapon() {
        return activeWeapon;
    }

    public void addWeapon(Weapon weapon) {
        objectHandler.addObject(weapon);
        this.arsenal.add(weapon);
    }

    public void removeWeapon(Weapon weapon) {
        objectHandler.removeObject(weapon);
        this.arsenal.remove(weapon);
    }

    public void addKill() {
        killCount++;
    }

    public int getKillCount() {
        return killCount;
    }
}
