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
    protected float diameter;
    protected int maxHP;

    public Enemy(EnemyType type) {
        super(0, 0, 40, 40, ID.ENEMY);
        this.diameter = 40;
        this.type = type;
        setStartingPosition();
        this.shape = new Ellipse2D.Float(x, y, diameter, diameter);
    }

    @Override
    public void tick() {
        super.tick();
        updateColor();
    }

    private void updateColor() {
        int shade = (int) GeomUtil.clamp((int) ((double) (getHp() * 255) / maxHP), 0, 255);
        color = new Color(0, shade, shade);
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

    public enum EnemyType {
        BUG,
        SPIDER,
        BABY_SPIDER
    }
}
