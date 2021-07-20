package com.od.game.objects.weapons;

import com.od.game.ID;
import com.od.game.data.SoundData;
import com.od.game.objects.GameObject;
import com.od.game.objects.weapons.threads.ReloadThread;
import com.od.game.objects.weapons.threads.ShotThread;
import com.od.output.SoundPlayer;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

@Getter
@Setter
public abstract class Weapon extends GameObject {

    private WeaponType weaponType;

    protected float range;
    protected int damage;
    protected int speed;
    protected float calibre;

    protected int magMunition;
    protected int maxMagMunition;
    protected int totalMunition;
    protected int refillMunition;

    protected long beforeShotMillis;
    protected long afterShotMillis;
    protected long reloadTimeMillis;

    protected ShotThread shotThread;
    protected ReloadThread reloadThread;

    protected Optional<String> shootSoundLocation;
    protected Optional<String> reloadSoundLocation;


    public Weapon(WeaponType weaponType,
                  float range, int damage, int speed, float calibre,
                  int maxMagMunition, int refillMunition,
                  long beforeShotMillis, long afterShotMillis, long reloadTimeMillis) {

        super(-1000, -1000, 1, 1, ID.WEAPON);

        this.weaponType = weaponType;

        this.range = range;
        this.damage = damage;
        this.speed = speed;
        this.calibre = calibre;

        this.magMunition = maxMagMunition;
        this.maxMagMunition = maxMagMunition;
        this.totalMunition = 0;
        this.refillMunition = refillMunition;

        this.beforeShotMillis = beforeShotMillis;
        this.afterShotMillis = afterShotMillis;
        this.reloadTimeMillis = reloadTimeMillis;

        this.reloadThread = new ReloadThread(reloadTimeMillis);
        this.shotThread = new ShotThread(beforeShotMillis, afterShotMillis);

        this.shootSoundLocation = SoundData.getSoundLocation(this.getClass(), SoundData.Action.SHOOT);
        this.reloadSoundLocation = SoundData.getSoundLocation(this.getClass(), SoundData.Action.RELOAD);
    }

    @Override
    public void tick() {
        reloadThread.tick();
        shotThread.tick();
        askReload();
    }

    public void askInitShot(float targetX, float targetY) {

        if (hasMagMunitionLeft() && !isReloading() && !isShooting()) {

            shotThread.setTarget(targetX, targetY);
            shotThread.start();
        }
    }

    public void shoot() {

        shootSound();
        magMunition--;
        totalMunition--;
        shotThread.done();
    }

    public void askInitReload() {

        if (!isReloading()) {
            initReload();
        }
    }

    private void initReload() {

        reloadSound();
        reloadThread.start();
    }

    private void askReload() {
        if(isAskingToReload()) reload();
    }

    private void reload() {
        magMunition = Math.min(maxMagMunition, totalMunition);
        reloadThread.done();
    }

    public boolean isAskingToShoot() {
        return shotThread.isReady();
    }

    public boolean isShooting() {
        return !shotThread.isFinished();
    }

    public boolean isAskingToReload() {
        return reloadThread.isReady();
    }

    public boolean isReloading() {
        return !reloadThread.isFinished();
    }

    public void stopReloading() {
//        reloadThread.done();
    }

    public boolean hasMagMunitionLeft() {
        return magMunition > 0;
    }

    public boolean hasTotalMunitionLeft() {
        return totalMunition > 0;
    }

    private int getMagMunitions() {
        return totalMunition % maxMagMunition;
    }

    public double getMagState() {
        return getMagMunitions() / (double) maxMagMunition;
    }

    public double getReloadState() {
        return (float) reloadThread.getTimeMillis() / reloadTimeMillis;
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


    public enum WeaponType {
        PISTOL,
        RIFLE,
        SHOTGUN,
        SNIPER,
        AIR_STRIKE;
    }
}
