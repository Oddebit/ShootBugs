package com.od.game;

import com.od.objects.Bonus;
import com.od.objects.Enemy;
import com.od.objects.GameObject;
import com.od.objects.Weapon;

import java.awt.*;
import java.time.Instant;
import java.util.Random;

public class Spawner extends GameObject {

    Random random = new Random();

    ObjectHandler oHandler;
    SurroundingsHandler sHandler;
    Game game;

    private Instant lastTime;
    private long timeInGame;

    public Spawner(ObjectHandler oHandler, SurroundingsHandler sHandler, Game game) {
        super(-1000, -1000, 0, 0, ID.Spawner);
        this.lastTime = Instant.now();
        this.oHandler = oHandler;
        this.sHandler = sHandler;
        this.game = game;
    }

    @Override
    public void tick() {
        if (lastTime.plusSeconds(1).isBefore(Instant.now())){
            lastTime = lastTime.plusSeconds(1);
            timeInGame++;

            if(timeInGame < 45) {
                if(random.nextInt(100) < 30) {
                    int enemyX = random.nextInt(Game.REAL_WIDTH + (int) Enemy.getDiameter());
                    int enemyY = random.nextInt(Game.REAL_HEIGHT + (int) Enemy.getDiameter());
                    oHandler.addObject(new Enemy(enemyX, enemyY, oHandler, sHandler));
                }
            } else if(timeInGame == 45) {
                spawnBonus();
            } else if(timeInGame < 90) {
                if((random.nextInt(100) < 40)) {
                    int enemyX = random.nextInt(Game.REAL_WIDTH + (int) Enemy.getDiameter());
                    int enemyY = random.nextInt(Game.REAL_HEIGHT + (int) Enemy.getDiameter());
                    oHandler.addObject(new Enemy(enemyX, enemyY, oHandler, sHandler));
                }
            } else if(timeInGame == 90) {
                spawnBonus();
            } else if(timeInGame < 135) {
                if((random.nextInt(100) < 50)) {
                    int enemyX = random.nextInt(Game.REAL_WIDTH + (int) Enemy.getDiameter());
                    int enemyY = random.nextInt(Game.REAL_HEIGHT + (int) Enemy.getDiameter());
                    oHandler.addObject(new Enemy(enemyX, enemyY, oHandler, sHandler));
                }
            } else if(timeInGame == 135) {
                spawnBonus();
            } else if(timeInGame < 180) {
                if((random.nextInt(100) < 60)) {
                    int enemyX = random.nextInt(Game.REAL_WIDTH + (int) Enemy.getDiameter());
                    int enemyY = random.nextInt(Game.REAL_HEIGHT + (int) Enemy.getDiameter());
                    oHandler.addObject(new Enemy(enemyX, enemyY, oHandler, sHandler));
                }
            } else if(timeInGame == 180) {
                spawnBonus();
            } else if(timeInGame < 225) {
                if((random.nextInt(100) < 50)) {
                    int enemyX = random.nextInt(Game.REAL_WIDTH + (int) Enemy.getDiameter());
                    int enemyY = random.nextInt(Game.REAL_HEIGHT + (int) Enemy.getDiameter());
                    oHandler.addObject(new Enemy(enemyX, enemyY, oHandler, sHandler));
                }
            } else if(timeInGame == 225) {
                spawnBonus();
            } else if(timeInGame < 270) {
                if((random.nextInt(100) < 60)) {
                    int enemyX = random.nextInt(Game.REAL_WIDTH + (int) Enemy.getDiameter());
                    int enemyY = random.nextInt(Game.REAL_HEIGHT + (int) Enemy.getDiameter());
                    oHandler.addObject(new Enemy(enemyX, enemyY, oHandler, sHandler));
                }
            } else if(timeInGame == 270) {
                spawnBonus();
            } else if(timeInGame < 315) {
                if((random.nextInt(100) < 70)) {
                    int enemyX = random.nextInt(Game.REAL_WIDTH + (int) Enemy.getDiameter());
                    int enemyY = random.nextInt(Game.REAL_HEIGHT + (int) Enemy.getDiameter());
                    oHandler.addObject(new Enemy(enemyX, enemyY, oHandler, sHandler));
                }
            } else if(timeInGame == 315) {
                spawnBonus();
            } else if(timeInGame < 360) {
                if((random.nextInt(100) < 80)) {
                    int enemyX = random.nextInt(Game.REAL_WIDTH + (int) Enemy.getDiameter());
                    int enemyY = random.nextInt(Game.REAL_HEIGHT + (int) Enemy.getDiameter());
                    oHandler.addObject(new Enemy(enemyX, enemyY, oHandler, sHandler));
                }
            } else if(timeInGame == 390) {
                this.game.state = Game.State.Win;
            }
        }
    }

    @Override
    public void render(Graphics graphics) {}

    public void spawnBonus() {
        int proba = random.nextInt(4);

        switch (proba) {
            case 0:
                oHandler.addObject(new Bonus(oHandler, Weapon.Type.Health));
                break;
            case 1:
                oHandler.addObject(new Bonus(oHandler, Weapon.Type.Rifle));
                break;
            case 2:
                oHandler.addObject(new Bonus(oHandler, Weapon.Type.Shotgun));
                break;
            case 3:
                oHandler.addObject(new Bonus(oHandler, Weapon.Type.Sniper));
                break;
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x,(int)y,(int)w,(int)h);
    }
}
