package com.od.game.objects.creatures;

import com.od.game.ID;
import com.od.game.objects.GameObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Creature extends GameObject {

    protected int hp;
    protected int maxHp;

    protected float diameter;
    protected float speed;
    protected float velocityX, velocityY;


    public Creature(float x, float y, float w, float h, ID id) {
        super(x, y, w, h, id);
    }

    public abstract void move();

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
