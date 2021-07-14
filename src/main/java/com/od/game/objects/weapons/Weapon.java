package com.od.game.objects.weapons;

import com.od.game.Game;
import com.od.game.ID;
import com.od.game.data.SoundData;
import com.od.game.handlers.GeneralHandler;
import com.od.game.handlers.ObjectHandler;
import com.od.game.objects.GameObject;
import com.od.game.objects.creatures.Hero;
import com.od.game.objects.projectiles.Projectile;
import com.od.game.objects.threads.ReloadThread;
import com.od.game.objects.threads.ShotThread;
import com.od.output.SoundPlayer;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.Optional;

@Getter
@Setter
public abstract class Weapon extends GameObject {

    protected GeneralHandler generalHandler;

    protected GameObject owner;
    private Type type;

    protected float range;
    protected int damage;
    protected int speed = 8;

    protected int maxMagMunition;
    protected int totalMunition;
    protected int refillMunition;

    protected long shotTimeMillis;
    protected long reshotTimeMillis;
    protected long reloadTimeMillis;

    protected float xShot;
    protected float yShot;

    protected ShotThread shotThread;
    protected ReloadThread reloadThread;

    protected Optional<String> shootSoundLocation;
    protected Optional<String> reloadSoundLocation;


    public Weapon(Type type, GeneralHandler generalHandler) {
        super(-1000, -1000, 1, 1, ID.WEAPON);
        this.type = type;

        this.owner = generalHandler.getHero();

        this.totalMunition = 0;


        this.shotThread = new ShotThread(this);
        this.reloadThread = new ReloadThread(this);

        this.shootSoundLocation = SoundData.getSoundLocation(this.getClass(), SoundData.Action.SHOOT);
        this.reloadSoundLocation = SoundData.getSoundLocation(this.getClass(), SoundData.Action.RELOAD);
    }

    @Override
    public void tick() {
        autoAskInitReload();
    }

    @Override
    public void render(Graphics graphics) {
    }

    public void askInitShot(float xShot, float yShot) {

        if (!isMagEmpty() && !isReloading() && !isShooting()) {

            initShot(xShot, yShot);
        }
    }

    protected void initShot(float xShot, float yShot) {

        this.xShot = xShot;
        this.yShot = yShot;
        this.shotThread.start();
    }

    public void shoot() {

        shootSound();
        generalHandler.getHandler(ID.PROJECTILE).add(new Projectile(xShot, yShot, owner, generalHandler));
        this.totalMunition--;
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
        reloadThread.start();
    }

    public boolean isShooting() {
        return shotThread.isAlive();
    }

    public boolean isReloading() {
        return reloadThread.isAlive();
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
        return 0;
//        return reloadThread.blabla / (float) reloadTime;
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
