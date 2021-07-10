package com.od.game.objects.creatures.enemies;

import com.od.game.Game;
import com.od.game.ID;
import com.od.game.handlers.ObjectHandler;
import com.od.game.handlers.SurroundingsHandler;
import com.od.game.objects.DashBoard;
import com.od.game.objects.creatures.Creature;
import com.od.game.objects.creatures.Hero;
import com.od.game.util.GeomUtil;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.Random;

@Getter
@Setter
public abstract class Enemy extends Creature {

    Random random = new Random();

    ObjectHandler objectHandler;
    SurroundingsHandler surroundingsHandler;
    DashBoard dashBoard;

    protected float diameter;
    protected float speed;
    protected int maxHP;

    Hero hero;

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
    public void render(Graphics graphics) {
        int shade = (int) GeomUtil.clamp((int) ((double) (getHP() * 255) / maxHP), 0, 255);
        graphics.setColor(new Color(0, shade, shade));
        graphics.fillOval((int) x, (int) y, (int) diameter, (int) diameter);
    }

    @Override
    public void die() {
        dashBoard.addKill();
        objectHandler.removeObject(this);
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
}
