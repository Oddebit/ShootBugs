package com.od.game.objects.weapons;

import com.od.game.Game;
import com.od.game.ID;
import com.od.game.data.SoundData;
import com.od.game.handlers.ObjectHandler;
import com.od.game.handlers.SurroundingsHandler;
import com.od.game.objects.GameObject;
import com.od.game.objects.creatures.Hero;
import com.od.game.objects.projectiles.Projectile;
import com.od.output.SoundPlayer;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Getter
@Setter
public abstract class Weapon extends GameObject {

    protected Game game;

    protected ObjectHandler objectHandler;
    protected SurroundingsHandler surroundingsHandler;
    protected GameObject owner;
    private Type type;

    protected float range;
    protected int damage;
    protected int speed = 8;

    protected int maxMagMunition;
    protected int totalMunition;
    protected int refillMunition;

    protected int shotTimeMillis;
    protected int reshotTimeMillis;
    protected Instant lastInitShot;
    protected float xShot;
    protected float yShot;
    protected boolean isShooting;
    protected Instant lastShot;

    protected int reloadTime;
    protected Instant lastInitReload;
    protected boolean isReloading;

    protected Optional<String> shootSoundLocation;
    protected Optional<String> reloadSoundLocation;


    public Weapon(Type type, Game game) {
        super(-1000, -1000, 1, 1, ID.Weapon);
        this.type = type;
        this.owner = game.getHero();

        this.game = game;
        this.objectHandler = game.getObjectHandler();
        this.surroundingsHandler = game.getSurroundingsHandler();

        this.totalMunition = 0;
        this.lastShot = Instant.now();
        this.lastInitReload = Instant.now();
        this.isReloading = false;

        this.shootSoundLocation = SoundData.getSoundLocation(this.getClass(), SoundData.Action.SHOOT);
        this.reloadSoundLocation = SoundData.getSoundLocation(this.getClass(), SoundData.Action.RELOAD);
    }

    @Override
    public void tick() {
        autoAskInitReload();
        askReload();
        askShot();
    }

    @Override
    public void render(Graphics graphics) {
    }

    public void askInitShot(float xShot, float yShot) {

        if (!isMagEmpty()
                && !isReloading
                && lastShot.plusMillis(reshotTimeMillis).isBefore(Instant.now()))

            initShot(xShot, yShot);
    }

    protected void initShot(float xShot, float yShot) {

        this.isShooting = true;
        this.isReloading = false;

        this.xShot = xShot;
        this.yShot = yShot;

        this.lastInitShot = Instant.now();
    }

    private void askShot() {
        if (isShooting && lastInitShot.plusMillis(shotTimeMillis).isBefore(Instant.now()))
            shoot();
    }

    protected void shoot() {

        shootSound();

        objectHandler.addObject(new Projectile(xShot, yShot, owner, objectHandler, surroundingsHandler));

        this.totalMunition--;
        this.lastShot = Instant.now();
        this.isShooting = false;
    }

    private void autoAskInitReload() {
        if (isMagEmpty()) askInitReload();
    }

    public void askInitReload() {
        if (!hasMunitionLeft()) {
            ((Hero) owner).resetActiveWeapon();
        } else if (!isReloading()) {
            initReload();
        }
    }

    private void initReload() {
        reloadSound();
        this.isReloading = true;
        this.lastInitReload = Instant.now();
    }

    private void askReload() {
        if (isReloading && lastInitReload.plusMillis(reloadTime).isBefore(Instant.now()))
            reload();
    }

    private void reload() {
        isReloading = false;
    }

    protected boolean isMagEmpty() {
        return totalMunition % maxMagMunition == 0;
    }

    public boolean hasMunitionLeft() {
        return totalMunition < 0;
    }

    private int getMagMunitions() {
        return totalMunition % maxMagMunition;
    }

    public double getMagState() {
        return getMagMunitions() / (double) maxMagMunition;
    }

    public double getReloadState() {
        return ChronoUnit.SECONDS.between(lastInitReload, Instant.now()) / (float) reloadTime;
    }

    public void reloadSound() {
        reloadSoundLocation.ifPresent(SoundPlayer::playSound);
    }

    public void shootSound() {
        shootSoundLocation.ifPresent(SoundPlayer::playSound);
    }

    public void refillMunition() {
        this.totalMunition += refillMunition;
    }

    public enum Type {
        Pistol, Rifle, Shotgun, Sniper, AirStrike
    }
}
