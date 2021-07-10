package com.od.game.objects.creatures;

import com.od.game.ID;
import com.od.game.objects.GameObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Creature extends GameObject {

    protected int HP;
    protected int maxHP;

    public Creature(float x, float y, float w, float h, ID id) {
        super(x, y, w, h, id);
    }

    public abstract void move();

    public void askDie() {
        if(HP <= 0) die();
    }

    public abstract void die();

    public void resetHP() {
        setHP(maxHP);
    }
}
