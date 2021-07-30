package com.od.game.page.objects;


import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

@Getter
@Setter
public abstract class Div {

    private Rectangle2D frame = new Rectangle2D.Double(1, 1, 1, 1);

    private Rectangle2D padding = new Rectangle2D.Double(0, 0, 0, 0);
    private Rectangle2D margin = new Rectangle2D.Double(0, 0, 0, 0);

    private Color backGroundColor = new Color(0, 0, 0, 0);
    private Color outlineColor = new Color(0, 0, 0, 0);


    public void render(Graphics2D graphics) {

        graphics.setColor(backGroundColor);
        graphics.fill(frame);

        graphics.setColor(outlineColor);
        graphics.draw(frame);
    }


    public Point2D getPosition() {
        return new Point2D.Double(frame.getX(), frame.getY());
    }

    public void setPosition(double x, double y) {
        frame.setFrame(x, y, getW(), getH());
    }

    public Point2D getDimension() {
        return new Point2D.Double(frame.getWidth(), frame.getHeight());
    }

    public void setDimension(Point2D dimension) {
        frame.setFrame(getX(), getY(), dimension.getX(), dimension.getY());
    }

    public void setDimension(double w, double h) {
        setDimension(new Point2D.Double(w, h));
    }

    public double getX() {
        return getPosition().getX();
    }

    public double getY() {
        return getPosition().getY();
    }

    public double getW() {
        return getDimension().getX();
    }

    public double getH() {
        return getDimension().getX();
    }

    public double getPaddingLeft() {
        return padding.getX();
    }

    public double getPaddingRight() {
        return padding.getWidth();
    }

    public double getPaddingTop() {
        return padding.getY();
    }

    public double getPaddingBottom() {
        return padding.getHeight();
    }

    public Rectangle2D getPadded() {
        return new Rectangle2D.Double(
                getX() - getPaddingLeft(),
                getY() - getPaddingTop(),
                getW() + getPaddingRight(),
                getH() + getPaddingBottom()
        );
    }

    public void setPadding(double padding) {
        this.padding = new Rectangle2D.Double(padding, padding, padding, padding);
    }

    public double getMarginLeft() {
        return margin.getX();
    }

    public double getMarginRight() {
        return margin.getWidth();
    }

    public double getMarginTop() {
        return margin.getY();
    }

    public double getMarginBottom() {
        return margin.getHeight();
    }

    public Rectangle2D getMargined() {
        return new Rectangle2D.Double(
                getX() + getMarginLeft(),
                getY() + getMarginTop(),
                getW() - getMarginRight(),
                getH() - getMarginBottom()
        );
    }

    public void setMargin(double margin) {
        this.margin = new Rectangle2D.Double(margin, margin, margin, margin);
    }
}
