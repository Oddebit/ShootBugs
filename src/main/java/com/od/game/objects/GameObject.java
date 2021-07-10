package com.od.game.objects;

import com.od.game.ID;

import java.awt.*;

public abstract class GameObject {

    protected float x, y;
    protected float w, h;
    protected ID id;
    protected float velocityX, velocityY;

    private final float diameter = 24;
    private final float speed = 5;
    private final float range = 0;

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

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public float getH() {
        return h;
    }

    public float getW() {
        return w;
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public float getRange() {
        return range;
    }

    public boolean intersects(GameObject gameObject){
        float deltaX = (gameObject.getX() - gameObject.getH()/2) - (this.x - h);
        float deltaY = (gameObject.getY() - gameObject.getH()/2) - (this.y - h);

        return Math.pow(deltaX, 2) + Math.pow(deltaY, 2) < Math.pow(gameObject.getH()/2 + h/2, 2);
    }
}
