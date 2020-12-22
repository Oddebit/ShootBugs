package com.od.game;

import com.od.objects.*;

import java.awt.*;
import java.time.Instant;
import java.util.Random;

public class Spawner extends GameObject {

    Random random = new Random();

    ObjectHandler objectHandler;
    SurroundingsHandler surroundingsHandler;
    DashBoard dashBoard;
    Game game;
    Hero hero;

    private Instant lastTime;
    private long timeInGame;

    private int level;

    public Spawner(ObjectHandler oHandler, SurroundingsHandler sHandler, DashBoard dashBoard, Game game, Hero hero) {
        super(-1000, -1000, 0, 0, ID.Spawner);
        this.lastTime = Instant.now();
        this.objectHandler = oHandler;
        this.surroundingsHandler = sHandler;
        this.dashBoard = dashBoard;
        this.game = game;
        this.hero = hero;
        this.level = 0;
    }

    @Override
    public void tick() {
        if (lastTime.plusSeconds(1).isBefore(Instant.now())) {
            lastTime = lastTime.plusSeconds(1);
            timeInGame++;

            if (timeInGame % 45 == 0) {
                spawnBonus();
                level++;
            }

            if (random.nextInt(100) < 30 + level * 8) {
                objectHandler.addObject(new Bugs(objectHandler, surroundingsHandler, dashBoard, hero));
            }
            if (random.nextInt(100) < 2 + level) {
                objectHandler.addObject(new Spider(objectHandler, surroundingsHandler, dashBoard, hero));
            }

            if (timeInGame == 495) {
                this.game.state = Game.State.Win;
            }
        }
    }

    @Override
    public void render(Graphics graphics) {
    }

    public void spawnBonus() {
        int proba = random.nextInt(5);
        objectHandler.addObject(new Bonus(objectHandler, Weapon.Type.values()[proba]));
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) w, (int) h);
    }
}
