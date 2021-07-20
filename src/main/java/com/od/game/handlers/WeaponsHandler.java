package com.od.game.handlers;

import com.od.game.ID;
import com.od.game.data.ColorData;
import com.od.game.data.FontData;
import com.od.game.objects.weapons.*;
import lombok.Getter;

import java.awt.*;
import java.awt.geom.Point2D;


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
        if(!activeWeapon.hasTotalMunitionLeft()) {
            activeWeapon = pistol;
        }
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
        if(activeWeapon == pistol)
            munition =  String.format("%c | %d", '\u221E', activeWeapon.getMagMunition());
        else
            munition =  String.format("%d | %d", activeWeapon.getTotalMunition(), activeWeapon.getMagMunition());
        graphics.drawString(munition, 20, 50);
    }
}
