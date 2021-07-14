package com.od.game.objects.creatures;

import com.od.game.Game;
import com.od.game.ID;
import com.od.game.data.ColorData;
import com.od.game.data.SoundData;
import com.od.game.objects.threads.UntouchableThread;
import com.od.game.objects.weapons.*;
import com.od.game.util.GeomUtil;
import com.od.output.SoundPlayer;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.ArrayList;

import static com.od.game.Game.*;

@Getter
@Setter
public class Hero extends Creature {

    private Game game;

    private static final float diameter = 51;

    private int maxHP = 100;
    private int HP;

    private ArrayList<Weapon> weapons = new ArrayList<>();
    private Weapon activeWeapon;

    private int untouchableTimeMillis = 2500;
    private Thread untouchableThread;

    private Color color = ColorData.HERO_ORANGE;

    public Hero() {
        super(WIDTH_CENTER, HEIGHT_CENTER, diameter, diameter, ID.HERO);
        this.HP = maxHP;
        this.speed = 5;

        this.untouchableThread = new UntouchableThread(this);
        initWeapons();
    }

    private void initWeapons() {
        weapons.add(new Pistol(game));
        weapons.add(new Rifle(game));
        weapons.add(new Shotgun(game));
        weapons.add(new Sniper(game));
        weapons.add(new AirStrike(game));
    }

    @Override
    public void tick() {

        askDie();
        move();
    }

    public void askDie() {
        if (HP <= 0) die();
    }

    public void die() {
        dieSound();
        game.setState(State.GameOver);
    }


    public void dieSound() {
        SoundData.getSoundLocation(this.getClass(), SoundData.Action.DIE).ifPresent(SoundPlayer::playSound);
    }

    @Override
    public void move() {

        x += velocityX;
        y += velocityY;

        x = GeomUtil.clamp(x, 0, REAL_WIDTH - diameter);
        y = GeomUtil.clamp(y, 0, REAL_HEIGHT - diameter);
    }

    @Override
    public void render(Graphics graphics) {

        graphics.setColor(color);
        graphics.fillOval((int) x, (int) y, (int) diameter, (int) diameter);
    }

    public void resetActiveWeapon() {
        setActiveWeapon(Weapon.Type.Pistol);
    }

    private void setActiveWeapon(Weapon.Type type) {
        activeWeapon = weapons.stream()
                .filter(weapon -> weapon.getType().equals(type))
                .findFirst().orElseThrow();
    }

    public void setNextActiveWeapon(int increment) {

        for (int i = 0; i < weapons.size(); i++) {

            Weapon tempWeapon = weapons.get(i);
            if (tempWeapon == activeWeapon) {

                int index = i + increment;
                while (index >= weapons.size()) index -= weapons.size();
                while (index < 0) index += weapons.size();
                activeWeapon = weapons.get(index);
                break;
            }
        }
    }

    private int getNextWeaponIndex(int start){
        return (start+1) % weapons.size();
    }

    public void refillWeapon(Weapon.Type weaponType) {

        weapons.stream()
                .filter(w -> w.getType().equals(weaponType)).findFirst()
                .orElseThrow().refillMunition();
    }

    public boolean isUntouchable() {
        return untouchableThread.isAlive();
    }

    public void setUntouchable() {
        untouchableThread.start();
    }
}
