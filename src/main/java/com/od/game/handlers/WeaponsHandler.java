package com.od.game.handlers;

import com.od.game.ID;
import com.od.game.data.ColorData;
import com.od.game.data.FontData;
import com.od.game.objects.weapons.*;
import lombok.Getter;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.stream.IntStream;


public class WeaponsHandler extends Handler<Weapon> {

    @Getter
    private Weapon activeWeapon;
    private Pistol pistol;
    private Rifle rifle;
    private Shotgun shotgun;
    private Sniper sniper;
    private AirStrike airStrike;

    public WeaponsHandler() {
        super(ID.WEAPON);
        initWeapons();
    }

    private void initWeapons() {
        pistol = new Pistol();
        rifle = new Rifle();
        shotgun = new Shotgun();
        sniper = new Sniper();
        airStrike = new AirStrike();

        handled.add(pistol);
        handled.add(rifle);
        handled.add(shotgun);
        handled.add(sniper);
        handled.add(airStrike);

        activeWeapon = pistol;
    }

    @Override
    public void tick() {
        super.tick();
        activeWeaponAskAutoReload();
        activeWeaponAskAutoShot();
    }


    public void activeWeaponAskAutoReload() {
        if (!activeWeapon.hasMagMunitionLeft() && !activeWeapon.isReloading()) {
            activeWeaponAskInitReload();
        }
    }

    private void activeWeaponAskAutoShot() {
        if (activeWeapon.equals(rifle) && rifle.isBursting()) {
            activeWeaponAskInitShot();
        }
    }

    public void setNextActiveWeapon(int increment) {
        Weapon tempWeapon = activeWeapon;
        IntStream.range(0, increment)
                .forEach(n -> setNextActiveWeapon());

        if(!tempWeapon.equals(activeWeapon)) {
            tempWeapon.stopReloading();
            activeWeaponStopBurst();
        }
        setNextActiveWeapon();
    }

    public void setNextActiveWeapon() {
        int index = getActiveWeaponIndex();
        do {
            index = getNextWeaponIndex(index);
            activeWeapon = handled.get(index);
        } while (!activeWeapon.hasTotalMunitionLeft());
    }

    private int getActiveWeaponIndex() {
        return handled.indexOf(activeWeapon);
    }

    private int getNextWeaponIndex(int start) {
        return (start + 1) % handled.size();
    }

    public void refillWeapon(Weapon.WeaponType weaponType) {

        getWeapon(weaponType).refillMunition();
    }

    private Weapon getWeapon(Weapon.WeaponType weaponType) {
        return handled.stream()
                .filter(weapon -> weapon.getWeaponType() == weaponType)
                .findFirst().orElseThrow();
    }

    public void activeWeaponAskInitReload() {
        activeWeapon.askInitReload();
    }

    public void activeWeaponAskInitShot() {
        activeWeapon.askInitShot();
    }

    public boolean activeWeaponIsAskingToShoot() {
        return activeWeapon.isAskingToShoot();
    }


    public void activeWeaponShoot() {
        activeWeapon.shoot();
    }

    public double activeWeaponGetTargetX() {
        return activeWeapon.getShotThread().getTarget().getX();
    }

    public double activeWeaponGetTargetY() {
        return activeWeapon.getShotThread().getTarget().getY();
    }

    public Point2D activeWeaponGetTarget() {
        return activeWeapon.getShotThread().getTarget();
    }

    public void checkTotalMunitionLeft() {
        if (!activeWeapon.hasTotalMunitionLeft()) {
            activeWeapon = pistol;
        }
    }

    public void activeWeaponAskInitBurst() {
        if (activeWeapon.equals(rifle)) {
            rifle.askInitBurst();
        } else {
            activeWeapon.askInitShot();
        }
    }

    public void activeWeaponStopBurst() {
        if (activeWeapon.equals(rifle)) {
            rifle.stopBurst();
        }
    }

    public void activeWeaponRetarget(double x, double y) {
        activeWeapon.retarget(x, y);
    }


    @Override
    public void render(Graphics2D graphics) {
        super.render(graphics);
        graphics.setColor(ColorData.HERO_ORANGE);

        graphics.setFont(FontData.BOLD.getFont());
        String weaponName = activeWeapon.getWeaponType().toString();
        graphics.drawString(weaponName, 20, 30);

        graphics.setFont(FontData.NORMAL.getFont());
        String munition;
        if (activeWeapon == pistol)
            munition = String.format("%c | %d", '\u221E', activeWeapon.getMagMunition());
        else
            munition = String.format("%d | %d",
                    activeWeapon.getTotalMunition() - activeWeapon.getMagMunition(), activeWeapon.getMagMunition());
        graphics.drawString(munition, 20, 50);
    }
}
