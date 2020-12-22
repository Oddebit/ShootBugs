package com.od.objects;

import com.od.game.Game;
import com.od.game.ID;
import com.od.game.ObjectHandler;
import com.od.game.SurroundingsHandler;

import java.awt.*;
import java.util.Random;

public class Enemy extends GameObject {

    Random random = new Random();

    ObjectHandler objectHandler;
    SurroundingsHandler surroundingsHandler;
    DashBoard dashBoard;

    protected float diameter;
    protected float speed;
    protected int maxHP;
    protected int HP;

    Hero hero;
    protected boolean isHittingHero = false;

    public Enemy(ObjectHandler objectHandler, SurroundingsHandler surroundingsHandler, DashBoard dashBoard, Hero hero) {
        super(0, 0, 40, 40, ID.Enemy);
        this.diameter = 40;
        this.objectHandler = objectHandler;
        this.surroundingsHandler = surroundingsHandler;
        this.dashBoard = dashBoard;
        this.hero = hero;

        setStartingPosition();
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics graphics) {
        int shade = (int) Game.clamp((int)((double) (HP * 255) / maxHP), 0, 255);
        graphics.setColor(new Color(0, shade, shade));
        graphics.fillOval((int) x, (int) y, (int) diameter, (int) diameter);
    }

    public void collision() {
        boolean hitsHero = false;
        for (int i = 0; i < objectHandler.objects.size(); i++) {
            GameObject tempObject = objectHandler.objects.get(i);

            if (this.intersects(tempObject)) {
                if (tempObject.getId() == ID.Hero) {
                    if (!isHittingHero) {
                        hero.setHP(hero.getHP() - HP);
                    }
                    hitsHero = true;
                }
            }
        }
        isHittingHero = hitsHero;
    }

    public void setStartingPosition() {
        x = random.nextInt(Game.REAL_WIDTH + (int) this.getDiameter());
        y = random.nextInt(Game.REAL_HEIGHT + (int) this.getDiameter());

        int proba = random.nextInt(4);
        switch (proba) {
            case 0:
                this.y = 0;
                break;
            case 1:
                this.x = 0;
                break;
            case 2:
                this.y = Game.REAL_HEIGHT - (int) this.getDiameter();
                break;
            case 3:
                this.x = Game.REAL_WIDTH - (int) this.getDiameter();
                break;
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) diameter, (int) diameter);
    }

    public float getDiameter() {
        return diameter;
    }

    public float getSpeed() {
        return speed;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }
}
