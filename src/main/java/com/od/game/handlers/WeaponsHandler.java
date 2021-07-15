package com.od.game.handlers;

import com.od.game.ID;
import com.od.game.objects.weapons.*;
import lombok.Getter;


public class WeaponsHandler extends Handler<Weapon> {

    @Getter
    private Weapon activeWeapon;
    private Weapon pistol;

    public WeaponsHandler() {
        super(ID.WEAPON);
        initWeapons();
    }

    private void initWeapons() {
        pistol = new Pistol();
        handled.add(pistol);
        handled.add(new Rifle());
        handled.add(new Shotgun());
        handled.add(new Sniper());
        handled.add(new AirStrike());
        activeWeapon = pistol;
    }

    @Override
    public void tick() {
        super.tick();
        activeWeaponAskAutoReload();
    }

    public void activeWeaponAskAutoReload() {
        if(!activeWeapon.hasMagMunitionLeft() && !activeWeapon.isReloading()) {
            activeWeaponAskInitReload();
        }
    }

    public void setNextActiveWeapon(int increment) {
//        Weapon tempWeapon = activeWeapon;
//        IntStream.range(0, increment)
//                .forEach(n -> setNextActiveWeapon());
//
//        if(!tempWeapon.equals(activeWeapon)) tempWeapon.stopReloading();
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

    private int getNextWeaponIndex(int start){
        return (start+1) % handled.size();
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

    public void activeWeaponAskInitShot(float x, float y) {
        activeWeapon.askInitShot(x, y);
    }

    public boolean activeWeaponIsAskingToShoot() {
        return activeWeapon.isAskingToShoot();
    }


    public void activeWeaponShoot() {
        activeWeapon.shoot();
    }

    public float activeWeaponGetTargetX() {
        return activeWeapon.getShotThread().getTargetX();
    }

    public float activeWeaponGetTargetY() {
        return activeWeapon.getShotThread().getTargetY();
    }

    public void checkTotalMunitionLeft() {
        if(!activeWeapon.hasTotalMunitionLeft()) {
            activeWeapon = pistol;
        }
    }
}
