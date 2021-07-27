package com.od.game.states.play.objects.creatures;

import com.od.game.states.play.objects.GameObject;
import com.od.game.util.GeomUtil;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Creature extends GameObject {

    protected int hp;
    protected int maxHp;

    protected double diameter;
    //todo : move to game object & /sec insteadof /tick
    protected double speed;

    public Creature(double diameter, double speed, int hp, ID id) {
        super(GeomUtil.getSquare(0), GeomUtil.getSquare(diameter), id);
        this.maxHp = hp;
        this.hp = hp;

        this.diameter = diameter;
        this.speed = speed;
        setStartingPosition();
    }

    public abstract void setStartingPosition();

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
