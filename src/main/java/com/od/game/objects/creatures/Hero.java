package com.od.game.objects.creatures;

import com.od.game.Game;
import com.od.game.ID;
import com.od.game.data.ColorData;
import com.od.game.data.SoundData;
import com.od.game.handlers.ObjectHandler;
import com.od.game.handlers.SurroundingsHandler;
import com.od.game.objects.GameObject;
import com.od.game.objects.bonus.Bonus;
import com.od.game.objects.creatures.enemies.Enemy;
import com.od.game.objects.weapons.*;
import com.od.game.util.GeomUtil;
import com.od.output.SoundPlayer;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.time.Instant;
import java.util.ArrayList;

import static com.od.game.Game.*;

@Getter
@Setter
public class Hero extends Creature {

    private Game game;

    private static final float diameter = 51;
    private final float speed = 5;
    private final float range = 500;

    private ArrayList<Weapon> weapons = new ArrayList<>();
    private Weapon activeWeapon;

    private int maxHP = 100;
    private int HP;

    private boolean untouchable;
    private Instant lastTouch;
    private int untouchableTimeMillis = 2500;

    private Color color = ColorData.HERO_ORANGE;

    public Hero(ObjectHandler objectHandler, SurroundingsHandler surroundingsHandler, Game game) {
        super(WIDTH_CENTER, HEIGHT_CENTER, diameter, diameter, ID.Hero);
        this.game = game;
        this.HP = maxHP;

        untouchable = false;
        lastTouch = Instant.now();

        initWeapons();
    }

    public void initWeapons() {
        weapons.add(new Pistol(game));
        weapons.add(new Rifle(game));
        weapons.add(new Shotgun(game));
        weapons.add(new Sniper(game));
        weapons.add(new AirStrike(game));
    }

    @Override
    public void tick() {

        askDie();
        askTouchable();
        move();
        collision();
    }

    public void askDie() {
        if (HP <= 0) die();
    }

    public void die() {
        dieSound();
        game.setState(State.GameOver);
    }

    public void askTouchable() {
        if(untouchable && lastTouch.plusMillis(untouchableTimeMillis).isBefore(Instant.now()))
            untouchable = false;
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

    public void collision() {
        ObjectHandler objectHandler = game.getObjectHandler();

        for (int i = 0; i < objectHandler.objects.size(); i++) {
            GameObject colliding = objectHandler.objects.get(i);

            if (this.intersects(colliding)) {

                if (colliding.getId() == ID.Bonus) {
                    ((Bonus) colliding).getBonus(this);
                    objectHandler.removeObject(colliding);
                }

                if(colliding.getId() == ID.Enemy && !untouchable) {
                    HP -= ((Enemy) colliding).getHP();
                    untouchable = true;
                    lastTouch = Instant.now();
                }
            }
        }
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
}
