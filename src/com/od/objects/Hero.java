package com.od.objects;

import com.od.game.Game;
import com.od.game.Handler;

import java.awt.*;

import static com.od.game.Game.*;

public class Hero extends GameObject {

    private Handler handler;
    private static final float diameter = 24;
    private static final float speed = 5;
    private static final float range = 500;

    private static final int hp = 100;



    public Hero(Handler handler) {
        super(WIDTH_CENTER, HEIGHT_CENTER, diameter, diameter, ID.Hero);
        this.handler = handler;
    }

    @Override
    public void tick() {

        x += velocityX;
        y += velocityY;

        x = Game.clamp(x, 0, REAL_WIDTH - diameter);
        y = Game.clamp(y, 0, REAL_HEIGHT - diameter);
    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(new Color(255, 120, 0));
        graphics.fillOval((int)x, (int)y, (int)diameter, (int)diameter);
    }

    public void collision() {
        for (int i = 0; i < handler.objects.size(); i++) {
            GameObject tempObject = handler.objects.get(i);

            if (getBounds().intersects(tempObject.getBounds())) {
                //HUD GOES DOWN
                if (tempObject.getId() == ID.Enemy) {
                }
                if (tempObject.getId() == ID.Projectile) {

                }
            }
        }
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x, (int)y, (int)diameter, (int)diameter);
    }

    public static float getDiameter() {
        return diameter;
    }

    public static float getSpeed() {
        return speed;
    }

    public static float getRange() {
        return range;
    }

    public int getHP() {
        return hp;
    }
}
