package com.od.game.objects;

import com.od.game.ID;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RectangularShape;

@Getter
@Setter
public abstract class GameObjects {
    protected ID id;

    protected RectangularShape shape;
    protected Color color;

    //todo : Point
    protected float x, y;
    protected float w, h;
    protected float velocityX, velocityY;

    public GameObjects(float x, float y, float w, float h, ID id) {
        this.x = x; this.y = y;
        this.w = w; this.h = h;
        this.id = id;
        this.shape = new Ellipse2D.Float();
    }

    public void tick() {
        shape.setFrame(x - w/2, y - h/2, w, h);
    }

    public void render(Graphics2D graphics) {
        graphics.setColor(color);
        graphics.fill(shape);
    }

    public boolean intersects(GameObjects gameObjects){
        float deltaX = gameObjects.getX() - x;
        float deltaY = gameObjects.getY() - y;

        return Math.pow(deltaX, 2) + Math.pow(deltaY, 2) < Math.pow(gameObjects.getH()/2 + h/2, 2);
    }
}
