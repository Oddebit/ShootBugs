package com.od.objects;

import com.od.game.Game;
import com.od.game.ID;
import com.od.game.ObjectHandler;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.time.Instant;

public class ToxicArea extends GameObject {

    ObjectHandler objectHandler;
    private Instant start;
    private long timeInGame;
    private float diameter;
    private int damage = 5;
    private Area toxicArea = new Area(new Rectangle(0, 0, Game.WIDTH, Game.HEIGHT));
    private Ellipse2D.Float cleanArea;
    private Hero hero;
    private boolean isPoisoning;
    Instant lastTime;

    public ToxicArea(ObjectHandler objectHandler) {
        super(0, 0, 0, 0, ID.ToxicArea);
        start = Instant.now();
        lastTime = Instant.now();
        diameter = Game.REAL_WIDTH - 700;
        cleanArea = new Ellipse2D.Float((float)Game.WIDTH_CENTER - diameter / 2f, (float)Game.HEIGHT_CENTER - diameter / 2f, diameter, diameter);

        for (int i = 0; i < objectHandler.objects.size(); i++) {
            GameObject tempObject = objectHandler.objects.get(i);
            if (tempObject.getId() == ID.Hero) hero = (Hero) tempObject;
        }
    }

    @Override
    public void tick() {
        if (lastTime.plusSeconds(1).isBefore(Instant.now())) {
            timeInGame++;
            diameter -= 10;
            lastTime = lastTime.plusSeconds(1);
        }
        cleanArea = new Ellipse2D.Float((float)Game.WIDTH_CENTER - diameter / 2f, (float)Game.HEIGHT_CENTER - diameter / 2f, diameter, diameter);
        toxicArea.subtract(new Area(cleanArea));
        collision();
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(new Color(0, 255, 0, 120));
        ((Graphics2D) graphics).fill(toxicArea);
    }

    public boolean isPoisoning() {
        float deltaX = Game.WIDTH_CENTER - hero.getX() - hero.getH() / 2;
        float deltaY = Game.HEIGHT_CENTER - hero.getY() - hero.getH() / 2;
        return (float) Math.pow(deltaX, 2) + Math.pow(deltaY, 2) > Math.pow(diameter, 2);
    }

    public void collision() {
        if(isPoisoning()) {
            if(!isPoisoning) {
                lastTime = Instant.now();
            } else if (lastTime.plusSeconds(1).isBefore(Instant.now())) {
                isPoisoning = true;
                lastTime = lastTime.plusSeconds(1);
                hero.setHP(hero.getHP() - damage);
            }
        } else {
            isPoisoning = false;
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(0, 0, 0, 0);
    }
}
