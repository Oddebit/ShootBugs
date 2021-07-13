package com.od.game.objects;

import com.od.game.ID;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public abstract class GameObject {

    protected float x, y;
    protected float w, h;
    protected ID id;
    protected float velocityX, velocityY;

    public GameObject(float x, float y, float w, float h, ID id) {
        this.x = x; this.y = y;
        this.w = w; this.h = h;
        this.id = id;
    }

    public abstract void tick();
    public abstract void render(Graphics graphics);

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, (int) w, (int) h);
    }


    public boolean intersects(GameObject gameObject){
        float deltaX = (gameObject.getX() - gameObject.getW()/2) - (this.x - w/2);
        float deltaY = (gameObject.getY() - gameObject.getH()/2) - (this.y - h/2);

        return Math.pow(deltaX, 2) + Math.pow(deltaY, 2) < Math.pow(gameObject.getH()/2 + h/2, 2);
    }
}
