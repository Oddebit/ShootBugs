package com.od.game;

import com.od.game.handlers.GeneralHandler;
import com.od.game.handlers.ObjectHandler;
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

    private final GeneralHandler generalHandler;
    private final Hero hero;

    private final Instant start;
    private long timeInGame;
    private int level;

    private final Random random = new Random();

    public Spawner(GeneralHandler generalHandler, Hero hero) {
        super(-1000, -1000, 0, 0, ID.SPAWNER);

        this.generalHandler = generalHandler;
        this.hero = hero;

        this.start = Instant.now();
        this.level = 0;
    }

    @Override
    public void tick() {

        timeInGame = ChronoUnit.SECONDS.between(start, Instant.now());

        askLevelUp();
        askSpawnEnemy();
        askSpawnBonus();
    }

    private void askLevelUp() {
        if (timeInGame % 60 == 0) {
            level++;
        }
    }

    private void askSpawnEnemy() {
        if (random.nextInt(100) < 30 + level * 8) {
            generalHandler.getHandler(ID.ENEMY).add(new Bug(hero));
        }
        if (random.nextInt(100) < 2 + level) {
            generalHandler.getHandler(ID.ENEMY).add(new Spider(hero));
        }
    }

    private void askSpawnBonus() {
        if (timeInGame % 30 == 0) {
            spawnBonus();
        }
    }

    private void spawnBonus() {
        int len = Weapon.Type.values().length;
        int rnd = random.nextInt(len + 1);
        if (rnd == len) generalHandler.getHandler(ID.BONUS).add(new HealthBonus(generalHandler));
        else generalHandler.getHandler(ID.BONUS).add(new WeaponBonus(generalHandler, Weapon.Type.values()[rnd]));
    }

    @Override
    public void render(Graphics graphics) {

    }
}
