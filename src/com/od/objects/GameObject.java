package com.od.objects;

import java.awt.*;

public abstract class GameObject {

    protected float x, y;
    protected float w, h;
    protected ID id;
    protected float velocityX, velocityY;

    private static final float diameter = 24;
    private static final float speed = 5;
    private static final float range = 500;

    public GameObject(float x, float y, float w, float h, ID id) {
        this.x = x; this.y = y;
        this.w = w; this.h = h;
        this.id = id;
    }

    public abstract void tick();
    public abstract void render(Graphics graphics);
    public abstract Rectangle getBounds();

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
}