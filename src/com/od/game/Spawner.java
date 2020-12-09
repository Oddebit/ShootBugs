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

    private Instant lastTime;
    private long timeInGame;

    public Spawner(ObjectHandler oHandler, SurroundingsHandler sHandler, DashBoard dashBoard, Game game) {
        super(-1000, -1000, 0, 0, ID.Spawner);
        this.lastTime = Instant.now();
        this.objectHandler = oHandler;
        this.surroundingsHandler = sHandler;
        this. dashBoard = dashBoard;
        this.game = game;
    }

    @Override
    public void tick() {
        if (lastTime.plusSeconds(1).isBefore(Instant.now())) {
            lastTime = lastTime.plusSeconds(1);
            timeInGame++;

            if (timeInGame < 45) {
                if (random.nextInt(100) < 30) {
                    objectHandler.addObject(new Enemy(objectHandler, surroundingsHandler, dashBoard));
                }
            } else if (timeInGame == 45) {
                spawnBonus();
            } else if (timeInGame < 90) {
                if ((random.nextInt(100) < 40)) {
                    objectHandler.addObject(new Enemy(objectHandler, surroundingsHandler, dashBoard));
                }
            } else if (timeInGame == 90) {
                spawnBonus();
            } else if (timeInGame < 135) {
                if ((random.nextInt(100) < 50)) {
                    objectHandler.addObject(new Enemy(objectHandler, surroundingsHandler, dashBoard));
                }
            } else if (timeInGame == 135) {
                spawnBonus();
            } else if (timeInGame < 180) {
                if ((random.nextInt(100) < 60)) {
                    objectHandler.addObject(new Enemy(objectHandler, surroundingsHandler, dashBoard));
                }
            } else if (timeInGame == 180) {
                spawnBonus();
            } else if (timeInGame < 225) {
                if ((random.nextInt(100) < 50)) {
                    objectHandler.addObject(new Enemy(objectHandler, surroundingsHandler, dashBoard));
                }
            } else if (timeInGame == 225) {
                spawnBonus();
            } else if (timeInGame < 270) {
                if ((random.nextInt(100) < 60)) {
                    objectHandler.addObject(new Enemy(objectHandler, surroundingsHandler, dashBoard));
                }
            } else if (timeInGame == 270) {
                spawnBonus();
            } else if (timeInGame < 315) {
                if ((random.nextInt(100) < 70)) {
                    objectHandler.addObject(new Enemy(objectHandler, surroundingsHandler, dashBoard));
                }
            } else if (timeInGame == 315) {
                spawnBonus();
            } else if (timeInGame < 360) {
                if ((random.nextInt(100) < 80)) {
                    objectHandler.addObject(new Enemy(objectHandler, surroundingsHandler, dashBoard));
                }
            } else if (timeInGame == 360) {
                spawnBonus();
            } else if (timeInGame < 405) {
                if ((random.nextInt(100) < 90)) {
                    objectHandler.addObject(new Enemy(objectHandler, surroundingsHandler, dashBoard));
                }
            } else if (timeInGame == 405) {
                spawnBonus();
            } else if (timeInGame < 450) {
                objectHandler.addObject(new Enemy(objectHandler, surroundingsHandler, dashBoard));
            } else if (timeInGame == 495) {
                this.game.state = Game.State.Win;
            }
        }
    }

    @Override
    public void render(Graphics graphics) {
    }

    public void spawnBonus() {
        int proba = random.nextInt(4);

        switch (proba) {
            case 0:
                objectHandler.addObject(new Bonus(objectHandler, Weapon.Type.Health));
                break;
            case 1:
                objectHandler.addObject(new Bonus(objectHandler, Weapon.Type.Rifle));
                break;
            case 2:
                objectHandler.addObject(new Bonus(objectHandler, Weapon.Type.Shotgun));
                break;
            case 3:
                objectHandler.addObject(new Bonus(objectHandler, Weapon.Type.Sniper));
                break;
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) w, (int) h);
    }
}
