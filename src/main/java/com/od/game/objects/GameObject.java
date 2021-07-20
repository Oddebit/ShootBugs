package com.od.game.objects;

import com.od.game.ID;
import com.od.game.util.GeomUtil;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

@Getter
@Setter
public abstract class GameObject {
    protected ID id;

    protected RectangularShape shape;
    protected Color color;

    //todo : Point
    protected Point2D.Double position;
    protected Point2D.Double dimension;
    protected Point2D.Double velocity;

    public GameObject(double x, double y, double w, double h, ID id) {
        this.id = id;

        this.shape = new Ellipse2D.Double();

        this.position = new Point2D.Double(x, y);
        this.dimension = new Point2D.Double(w, h);
        this.velocity = new Point2D.Double();
    }

    public void tick() {
        shape.setFrame(getFrame());
    }

    public void render(Graphics2D graphics) {
        graphics.setColor(color);
        graphics.fill(shape);
    }


    public boolean intersects(GameObject gameObject){
         return GeomUtil.intersects(this, gameObject);
    }

    public double getX() {
        return position.getX();
    }

    public double getY() {
        return position.getY();
    }

    public double getW() {
        return dimension.getX();
    }

    public double getH() {
        return dimension.getY();
    }

    public double getVelX() {
        return velocity.getX();
    }

    public double getVelY() {
        return velocity.getY();
    }

    public Rectangle2D.Double getFrame() {
        return new Rectangle2D.Double(position.getX() - dimension.getX() / 2,
                position.getY() - dimension.getY() / 2,
                dimension.getX(), dimension.getY());
    }


    public void setX(double x) {
        setPosition(x, getY());
    }

    public void setY(double y) {
        setPosition(getX(), y);
    }

    public void setPosition(double x, double y) {
        position.setLocation(x, y);
    }

    public void setW(double w) {
        setDimension(w, getH());
    }

    public void setH(double h) {
        setDimension(getW(), h);
    }

    public void setDimension(double w, double h) {
        dimension.setLocation(w, h);
    }

    public void setVelX(double velX) {
        setVelocity(velX, getVelY());
    }

    public void setVelY(double velY) {
        setVelocity(getVelX(), velY);
    }

    public void setVelocity(double velX, double velY) {
        velocity.setLocation(velX, velY);
    }
}
