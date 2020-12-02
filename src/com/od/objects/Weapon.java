package com.od.objects;

import com.od.game.Handler;

import java.awt.*;
import java.time.Instant;

public class Weapon extends GameObject{

    Handler handler;
    GameObject owner;
    private Type type;

    protected float range;
    protected int damage;
    protected int magazine;
    protected int munitionLeft;
    protected int reloadTime;
    protected boolean isReloading;

    protected Instant lastReload;


    public Weapon(Type type, GameObject owner, Handler handler) {
        super(-1000, -1000, 1,1, ID.Weapon);
        this.type = type;
        this.owner = owner;
        this.handler = handler;
        this.isReloading = false;
        this.lastReload = Instant.now();
    }

    public void shoot(float mouseX, float mouseY) {
        if (munitionLeft > 0) {
            handler.addObject(new Projectile(
                    owner.getX() + owner.getW() / 2,
                    owner.getY() + owner.getH() / 2,
                    mouseX, mouseY,
                    owner, damage, handler));
            this.munitionLeft -= 1;
        } else {
            reload();
        }
    }

    public void reload() {
        if (!isReloading) {
            this.lastReload = Instant.now();
            this.isReloading = true;
        }
    }

    private void effectiveReload() {
        this.munitionLeft = magazine;
    }

    @Override
    public void tick() {
        if (lastReload.plusSeconds(reloadTime).isBefore(Instant.now()) && isReloading) {
            effectiveReload();
            isReloading = false;
        }
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(new Color(255,120, 0));
        if(this.owner.getId() == ID.Hero) {
            if (this == ((Hero)owner).getActiveWeapon()) {
                graphics.drawString(this.type.toString(), 10, 20);
            }
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, (int)w, (int)h);
    }

    public int getMunitionLeft() {
        return munitionLeft;
    }

    public int getMagazine() {
        return magazine;
    }

    public enum Type {
        Pistol, Rifle, Shotgun
    }
}
