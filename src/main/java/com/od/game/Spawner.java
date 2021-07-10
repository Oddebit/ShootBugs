package com.od.game;

import com.od.game.handlers.ObjectHandler;
import com.od.game.handlers.SurroundingsHandler;
import com.od.game.objects.DashBoard;
import com.od.game.objects.GameObject;
import com.od.game.objects.bonus.HealthBonus;
import com.od.game.objects.bonus.WeaponBonus;
import com.od.game.objects.creatures.Hero;
import com.od.game.objects.creatures.enemies.Bug;
import com.od.game.objects.creatures.enemies.Spider;
import com.od.game.objects.weapons.Weapon;

import java.awt.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Random;

public class Spawner extends GameObject {

    Random random = new Random();

    ObjectHandler objectHandler;
    SurroundingsHandler surroundingsHandler;
    DashBoard dashBoard;
    Game game;
    Hero hero;

    private final Instant start;
    private long timeInGame;

    private int level;

    public Spawner(ObjectHandler oHandler, SurroundingsHandler sHandler, DashBoard dashBoard, Game game, Hero hero) {
        super(-1000, -1000, 0, 0, ID.Spawner);
        this.start = Instant.now();
        this.objectHandler = oHandler;
        this.surroundingsHandler = sHandler;
        this.dashBoard = dashBoard;
        this.game = game;
        this.hero = hero;
        this.level = 0;
    }

    @Override
    public void tick() {

        timeInGame = ChronoUnit.SECONDS.between(start, Instant.now());

        askLevelUp();
        askSpwanEnnemy();
        askSpawnBonus();
        askWin();
    }

    private void askLevelUp() {
        if (timeInGame % 60 == 0) {
            level++;
        }
    }

    private void askSpwanEnnemy() {
        if (random.nextInt(100) < 30 + level * 8) {
            objectHandler.addObject(new Bug(objectHandler, surroundingsHandler, dashBoard, hero));
        }
        if (random.nextInt(100) < 2 + level) {
            objectHandler.addObject(new Spider(objectHandler, surroundingsHandler, dashBoard, hero));
        }
    }

    private void askSpawnBonus() {
        if (timeInGame % 30 == 0) {
            spawnBonus();
        }
    }

    private void spawnBonus() {
        int rnd = random.nextInt(Weapon.Type.values().length + 1);
        if (rnd == 0) objectHandler.addObject(new HealthBonus(objectHandler));
        else objectHandler.addObject(new WeaponBonus(objectHandler, Weapon.Type.values()[rnd]));
    }

    private void askWin() {
        if (timeInGame == 495) {
            this.game.state = Game.State.Win;
        }
    }

    @Override
    public void render(Graphics graphics) {

    }
}
