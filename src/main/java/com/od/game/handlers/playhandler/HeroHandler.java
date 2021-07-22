package com.od.game.handlers.playhandler;

import com.od.game.ID;
import com.od.game.data.DimensionData;
import com.od.game.objects.creatures.hero.Hero;
import com.od.game.util.GeomUtil;

import java.awt.*;
import java.awt.geom.Point2D;

public class HeroHandler extends PlayHandler<Hero> {
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
        hero.setVelX(hero.getSpeed() * movement);
    }

    public void setMovementY(int movement) {
        hero.setVelY(hero.getSpeed() * movement);
    }

    public double heroGetX() {
        return hero.getX();
    }

    public double heroGetY() {
        return hero.getY();
    }

    public Point2D heroGetPosition() {
        return hero.getPosition();
    }

    public boolean heroIsUntouchable() {
        return hero.isUntouchable();
    }

    public boolean heroIsDead() {
        return hero.isDead();
    }

    public void heroRemoveHp(int hp) {
        hero.removeHp(hp);
        hero.setUntouchable();
    }

    @Override
    public void render(Graphics2D graphics) {
        super.render(graphics);

        if (heroIsUntouchable() && hero.getUntouchableThread().getTimeMillis() % 500 < 250){
        } else {

            int hp = hero.getHp();
            int red = (int) GeomUtil.clamp(360 - 3.6f * hp, 0, 255);
            int green = (int) GeomUtil.clamp((int) (-1.8 + 3.6f * hp), 0, 255);
            int blue = (int) GeomUtil.clamp(1.8f * hp, 0, 255);
            graphics.setColor(new Color(red, green, blue));

            graphics.fillRect(DimensionData.WIDTH_CENTER - hero.getMaxHp() / 2, 20, hp, 3);
        }
    }
}
