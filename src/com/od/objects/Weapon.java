package com.od.objects;

import com.od.game.Game;
import com.od.game.ID;
import com.od.game.ObjectHandler;
import com.od.game.SurroundingsHandler;

import java.awt.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class Weapon extends GameObject {

    protected ObjectHandler objectHandler;
    protected SurroundingsHandler surroundingsHandler;
    protected GameObject owner;
    private Type type;

    protected float range;
    protected int damage;
    protected int speed = 8;

    protected int maxMagMunition;
    protected int magMunition;
    protected int maxTotalMunition;
    protected int totalMunition;

    protected Instant lastActivation;

    protected int reshotTime;
    protected Instant lastShot;

    protected int reloadTime;
    protected boolean isReloading;
    protected Instant lastReload;


    public Weapon(Type type, GameObject owner, ObjectHandler objectHandler, SurroundingsHandler surroundingsHandler) {
        super(-1000, -1000, 1, 1, ID.Weapon);
        this.type = type;
        this.owner = owner;
        this.objectHandler = objectHandler;
        this.surroundingsHandler = surroundingsHandler;
        this.lastShot = Instant.now();
        this.isReloading = false;
        this.lastReload = Instant.now();
        this.lastActivation = Instant.now();
    }

    public void shoot(float mouseX, float mouseY) {
        if (magMunition > 0) {
            if (!isReloading) {
                if(lastShot.plusMillis(reshotTime).isBefore(Instant.now())) {
                    shootSound();
                    objectHandler.addObject(new Projectile(mouseX, mouseY, owner, objectHandler, surroundingsHandler));
                    this.magMunition--;
                    this.totalMunition--;
                    this.lastShot = Instant.now();
                }
            }
        } else {
            reload();
        }
    }

    public void shootSound() {
        switch (this.type) {
            case Pistol:
                Game.playSound("sounds/shoot.wav");
                break;
            case Rifle:
                Game.playSound("sounds/rifleShoot.wav");
                break;
            case Shotgun:
                Game.playSound("sounds/shotgunShoot.wav");
                break;
            case Sniper:
                Game.playSound("sounds/sniperShoot.wav");
                break;
        }
    }

    public void reload() {
        if (!isReloading && totalMunition > 0) {
            reloadSound();
            this.lastReload = Instant.now();
            this.isReloading = true;
        } else if (totalMunition <= 0) {
            ((Hero) owner).setActiveWeapon(1);
            ((Hero) owner).removeWeapon(this);
        }
    }

    public void reloadSound() {
        switch (this.type) {
            case Pistol:
                Game.playSound("sounds/reload.wav");
                break;
            case Rifle:
                Game.playSound("sounds/rifleReload.wav");
                break;
            case Shotgun:
                Game.playSound("sounds/shotgunInsertBullet.wav");
                break;
        }
    }

    private void effectiveReload() {
        if (totalMunition > 0) {
            this.magMunition = Math.min(maxMagMunition, totalMunition);
        }
    }

    @Override
    public void tick() {
        if (magMunition <= 0 && !isReloading) {
            reload();
        }
        if (lastReload.plusMillis(reloadTime).isBefore(Instant.now()) && isReloading) {
            effectiveReload();
            isReloading = false;
        }
    }

    @Override
    public void render(Graphics graphics) {
        long period = ChronoUnit.MILLIS.between(lastActivation, Instant.now());
        graphics.setColor(new Color(255, 120, 0));
        graphics.setFont(new Font(Font.DIALOG, Font.BOLD, 64));
        if (this.owner.getId() == ID.Hero) {
            if (this == ((Hero) owner).getActiveWeapon()) {
                String name = this.type.toString();
                int height = graphics.getFontMetrics().getHeight();
                int width = graphics.getFontMetrics().stringWidth(name);
                graphics.drawString(name, 10, (int) (3d / 4 * height - Math.pow(period/100d - 10, 2)));
            }
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) w, (int) h);
    }

    public int getMagMunition() {
        return magMunition;
    }

    public int getMagazine() {
        return maxMagMunition;
    }

    public Type getType() {
        return type;
    }

    public void setTotalMunition(int totalMunition) {
        this.totalMunition = totalMunition;
    }

    public int getMaxTotalMunition() {
        return maxTotalMunition;
    }

    public int getTotalMunition() {
        return totalMunition;
    }

    public enum Type {
        Pistol, Rifle, Shotgun, Health, Sniper
    }
}
