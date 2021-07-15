package com.od.game.handlers;

import com.od.game.ID;
import com.od.game.objects.creatures.hero.Hero;

public class HeroHandler extends Handler<Hero>{
    private final Hero hero;

    public HeroHandler() {
        super(ID.HERO);
        this.hero = new Hero();
        handled.add(hero);
    }

    public Hero getHero() {
        return hero;
    }

    public void setMovementX(int movement) {
        hero.setVelocityX(hero.getSpeed() * movement);
    }

    public void setMovementY(int movement) {
        hero.setVelocityY(hero.getSpeed() * movement);
    }

    public float heroGetX() {
        return hero.getX();
    }

    public float heroGetY() {
        return hero.getY();
    }

    public boolean heroIsDead() {
        return hero.isDead();
    }
}
