package com.od.objects;

import com.od.game.Game;
import com.od.game.ID;
import com.od.game.ObjectHandler;

import java.awt.*;
import java.time.Instant;

public class Weapon extends GameObject {

    ObjectHandler handler;
    GameObject owner;
    private Type type;

    protected float range;
    protected int damage;

    protected int maxMagMunition;
    protected int magMunition;
    protected int maxTotalMunition;
    protected int totalMunition;

    protected int reloadTime;
    protected boolean isReloading;
    protected Instant lastReload;


    public Weapon(Type type, GameObject owner, ObjectHandler handler) {
        super(-1000, -1000, 1, 1, ID.Weapon);
        this.type = type;
        this.owner = owner;
        this.handler = handler;
        this.isReloading = false;
        this.lastReload = Instant.now();
    }

    public void shoot(float mouseX, float mouseY) {
        if (magMunition > 0) {
            if (!isReloading) {
                Game.playSound("sounds/shoot.wav");
                handler.addObject(new Projectile(mouseX, mouseY, owner, handler));
                this.magMunition--;
                this.totalMunition--;
            }
        } else {
            reload();
        }
    }

    public void reload() {
        if (!isReloading && totalMunition > 0) {
            Game.playSound("sounds/reload.wav");
            this.lastReload = Instant.now();
            this.isReloading = true;
        } else if (totalMunition <= 0) {
            ((Hero) owner).setActiveWeapon(1);
            ((Hero) owner).removeWeapon(this);
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
        graphics.setColor(new Color(255, 120, 0));
        graphics.setFont(new Font(Font.DIALOG, Font.BOLD, 64));
        if (this.owner.getId() == ID.Hero) {
            if (this == ((Hero) owner).getActiveWeapon()) {
                String name = this.type.toString();
                int height = graphics.getFontMetrics().getHeight();
                int width = graphics.getFontMetrics().stringWidth(name);
                graphics.drawString(name, 10, (int) (3d / 4 * height));
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

    public enum Type {
        Pistol, Rifle, Shotgun, Health
    }
}
