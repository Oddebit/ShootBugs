package com.od.objects;

import com.od.game.ID;

import java.awt.*;

public class Bonus extends GameObject{
    public Bonus(float x, float y, float w, float h, ID id) {
        super(x, y, w, h, id);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics graphics) {

    }

    @Override
    public Rectangle getBounds() {
        return null;
    }

    public enum Type {
        Health, Weapon
    }
}
