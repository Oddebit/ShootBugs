package com.od.objects;

import com.od.game.Game;
import com.od.game.ID;
import com.od.game.ObjectHandler;

import java.awt.*;
import java.time.Instant;
import java.util.Random;

public class Bonus extends GameObject {

    Random random = new Random();

    ObjectHandler oHandler;
    Type type;
    Hero hero;

    private long timeLeft = 10;
    Instant lastTime;

    public Bonus(ObjectHandler oHandler, Type type) {
        super(0, 0, 0, 0, ID.Bonus);
        this.oHandler = oHandler;
        this.lastTime = Instant.now();
        this.type = type;
        this.h = 24;
        this.w = 24;

        for (int i = 0; i < oHandler.objects.size(); i++) {
            GameObject tempObject = oHandler.objects.get(i);
            if (tempObject.getId() == ID.Hero) hero = (Hero) tempObject;
        }

        setStartingPosition();
    }

    @Override
    public void tick() {
        if (timeLeft <= 0) {
            oHandler.removeObject(this);
        }

        if (lastTime.plusSeconds(1).isBefore(Instant.now())) {
            lastTime = lastTime.plusSeconds(1);
            timeLeft -= 1;
        }
        collision();
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setFont(new Font(Font.DIALOG, 0, 12));
        String display = "";
        if (type == Type.Health) {
            graphics.setColor(new Color(0, 255, 50));
            display = "Health";
        } else if (type == Type.Weapon) {
            graphics.setColor(new Color(255, 120, 50));
            display = "Rifle";
        }
        graphics.drawString(display, (int)(x + w + 3), (int)y);
        graphics.fillRect((int)x, (int)y, (int)w, (int)h);
    }

    public void collision() {
        if (getBounds().intersects(hero.getBounds())) {
            if (type == Type.Health) {
                hero.setHP(hero.maxHP);
            }
            if (type == Type.Weapon) {
                boolean isThisWeapon = false;
                for (int i = 0; i < hero.arsenal.size(); i++) {
                    Weapon tempWeapon = hero.arsenal.get(i);
                    if (tempWeapon.getType() == Weapon.Type.Rifle) {
                        tempWeapon.setTotalMunitions(tempWeapon.getMaxTotalMunition());
                        isThisWeapon = true;
                        break;
                    }
                }
                if (!isThisWeapon) hero.addWeapon(new Rifle(hero, oHandler));
            }

            oHandler.removeObject(this);
        }
    }

    public void setStartingPosition() {
        this.x = random.nextInt(Game.REAL_WIDTH - (int)w);
        this.y = random.nextInt(Game.REAL_HEIGHT - (int)h);
    }


    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) w, (int) h);
    }


    public enum Type {
        Health, Weapon
    }
}
