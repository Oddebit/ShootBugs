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

    private Color color = new Color (255, 120, 0);

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

        graphics.setColor(color);
        graphics.fillOval((int) x, (int) y, (int) diameter, (int) diameter);



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

        reloadState *= 100;

        int height = 10;
        int width = 100;
        graphics.setColor(color);
        graphics.drawRect((int)(WIDTH_CENTER - width/2d), 25, width, height);
        graphics.fillRect((int)(WIDTH_CENTER - width/2d), 25, (int)reloadState, height);

        int red = (int) Game.clamp(360 - 3.6f * HP, 0, 255);
        int green = (int) Game.clamp((int)(-1.8 + 3.6f * HP), 0, 255);
        int blue = (int) Game.clamp(1.8f * HP, 0, 255);
        graphics.setColor(new Color(red, green, blue));
        graphics.drawRect((int)(WIDTH_CENTER - width/2d), 10, width, height);
        graphics.fillRect((int)(WIDTH_CENTER - width/2d), 10, HP, height);
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
