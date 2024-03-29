package com.od.game.states.play.objects.creatures.enemies;

import com.od.game.data.DimensionData;
import com.od.game.states.play.objects.creatures.Creature;
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
        super(diameter, speed, hp, ID.ENEMY);

        this.type = type;

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

    @Override
    public void setStartingPosition() {
        setPosition(GeomUtil.randomPosition(diameter));

        int proba = new Random().nextInt(4);
        switch (proba) {
            case 0:
                setX(0);
                break;
            case 1:
                setY(0);
                break;
            case 2:
                setX(DimensionData.REAL_WIDTH - (int) this.getDiameter());
                break;
            case 3:
                setY(DimensionData.REAL_HEIGHT - (int) this.getDiameter());
                break;
        }
    }

    public enum EnemyType {
        BUG,
        FLY,
        SPIDER,
        BABY_SPIDER
    }
}
