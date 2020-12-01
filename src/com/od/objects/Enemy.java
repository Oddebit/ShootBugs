package com.od.objects;

import com.od.game.Handler;

import java.awt.*;

public class Enemy extends GameObject {

    Handler handler;
    GameObject hero;
    private static final float diameter = 16;
    private static final float speed = 3;

    public Enemy(float x, float y, Handler handler) {
        super(x, y, diameter, diameter, ID.Enemy);
        this.handler = handler;

        for (int i = 0; i < handler.objects.size(); i++) {
            GameObject tempObject = handler.objects.get(i);
            if (tempObject.getId() == ID.Hero) hero = tempObject;
        }
    }

    @Override
    public void tick() {
        float deltaX = x + diameter/2 - hero.getX() - hero.getW()/2;
        float deltaY = y + diameter/2 - hero.getY() - hero.getH()/2;
        float distance = (float) Math.sqrt(Math.pow(deltaX,2) + Math.pow(deltaY,2));

        velocityX = -speed * deltaX/distance;
        velocityY = -speed * deltaY/distance;

        x += velocityX;
        y += velocityY;
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(new Color(0, 120, 120));
        graphics.fillOval((int)x, (int)y, (int)diameter, (int)diameter);
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }

    public static float getDiameter() {
        return diameter;
    }

    public static float getSpeed() {
        return speed;
    }
}
