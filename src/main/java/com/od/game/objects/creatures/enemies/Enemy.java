package com.od.game.objects.creatures.enemies;

import com.od.game.Game;
import com.od.game.ID;
import com.od.game.objects.creatures.Creature;
import com.od.game.util.GeomUtil;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.Random;

@Getter
@Setter
public abstract class Enemy extends Creature {

    Random random = new Random();

    private EnemyType type;

    public Enemy(EnemyType type, int diameter, double speed, int hp) {
        super(0, 0, diameter, diameter, hp, ID.ENEMY);

        this.type = type;

        setStartingPosition();
        this.diameter = diameter;
        this.speed = speed;

        this.shape = new Ellipse2D.Double();
        shape.setFrame(getFrame());
    }

    @Override
    public void tick() {
        super.tick();
        updateColor();
    }

    private void updateColor() {
        int shade = (int) GeomUtil.clamp((int) ((double) (getHp() * 255) / getMaxHp()), 0, 255);
        color = new Color(0, shade, shade);
    }

    public void setStartingPosition() {
        setPosition(GeomUtil.randomPositionWithDimension(dimension));

        int proba = random.nextInt(4);
        switch (proba) {
            case 0:
                setX(0);
                break;
            case 1:
                setY(0);
                break;
            case 2:
                setX(Game.REAL_WIDTH - (int) this.getDiameter());
                break;
            case 3:
                setY(Game.REAL_HEIGHT - (int) this.getDiameter());
                break;
        }
    }

    public enum EnemyType {
        BUG,
        SPIDER,
        BABY_SPIDER
    }
}
