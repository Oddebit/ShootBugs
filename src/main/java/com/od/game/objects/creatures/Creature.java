package com.od.game.objects.creatures;

import com.od.game.ID;
import com.od.game.objects.GameObject;
import com.od.game.util.GeomUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Creature extends GameObject {

    protected int hp;
    protected int maxHp;

    protected float diameter;
    protected double speed;

    public Creature(double x, double y, double w, double h, int hp, ID id) {
        super(x, y, w, h, id);
        this.maxHp = hp;
        this.hp = hp;
    }

    @Override
    public void tick() {
        super.tick();
        move();
    }

    public void move() {
        GeomUtil.translateAndClamp(position, velocity);
    }

    public boolean isDead() {
        return hp <= 0;
    }

    public void resetHP() {
        setHp(maxHp);
    }

    public void removeHp(int amount) {
        hp = Math.max(0, hp - amount);
    }
}
