package com.od.game.page.objects;

import com.od.game.page.alignments.HorizontalAlignment;
import com.od.game.page.alignments.VerticalAlignment;
import com.od.game.util.PageUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class Container<D extends Div> extends Div {

    VerticalAlignment verticalAlignment = VerticalAlignment.JUSTIFY;
    HorizontalAlignment horizontalAlignment = HorizontalAlignment.CENTER;

    private final LinkedList<D> contained = new LinkedList<>();

    public Container(double w, double h) {
        this(0, 0, w, h);
    }

    public Container(double x, double y, double w, double h) {
        setPosition(x, y);
        setDimension(w, h);
    }

    @Override
    public void render(Graphics2D graphics) {
        super.render(graphics);
        if (!contained.isEmpty())
            contained.forEach(subdiv -> subdiv.render(graphics));
    }

    public void addSubdiv(D subdiv) {
        List<D> subdivAsList = new LinkedList<>();
        subdivAsList.add(subdiv);
        addSubdivs(subdivAsList);
    }

    @SneakyThrows
    public void addSubdivs(List<D> subdivs) {
        if (!canContain(subdivs)) throw new Exception(this + " cannot contain those divs.");

        contained.addAll(subdivs);
        repositionSubdivs();
    }

    public boolean canContain(List<D> subdivs) {
        LinkedList<Div> toContain = new LinkedList<>(this.contained);
        toContain.addAll(subdivs);

        Point2D dimension = PageUtil.getDimension(toContain);
        Rectangle2D frame =
                new Rectangle2D.Double(getMargined().getX(), getMargined().getY(), dimension.getX(), dimension.getY());

        return getMargined().contains(frame);
    }

    public boolean canContain() {
        Point2D dimension = PageUtil.getDimension(contained);
        Rectangle2D frame =
                new Rectangle2D.Double(getMargined().getX(), getMargined().getY(), dimension.getX(), dimension.getY());

        return getMargined().contains(frame);
    }

    protected void repositionSubdivs() {
        double space = getSpaceBetweenSubdivY();
        double y = getContainedY() + space;

        for (Div subdiv : contained) {
            double x = getSubdivX(subdiv);
            y += subdiv.getPaddingTop();
            subdiv.setPosition(x, y);
            y += subdiv.getH() + subdiv.getPaddingBottom() + space;
        }
    }

    private double getSubdivX(Div subdiv) {
        double x;
        switch (horizontalAlignment) {
            case LEFT:
                x = getMarginLeft();
                break;
            case CENTER:
            default:
                x = getMargined().getCenterX() - subdiv.getPadded().getWidth() / 2;
                break;
            case RIGHT:
                x = getX() + getW() - getMarginRight() - subdiv.getPadded().getWidth();
                break;
        }
        return x + subdiv.getPaddingLeft();
    }

    private double getContainedY() {
        Point2D dimension = getContainedFrame();

        double y;
        switch (verticalAlignment) {
            case TOP:
            case JUSTIFY:
            default:
                y = getMarginTop();
                break;
            case CENTER:
                y = getMargined().getCenterY() - dimension.getY() / 2;
                break;
            case BOTTOM:
                y = getY() + getH() - getMarginBottom() - dimension.getY();
                break;
        }

        return y;
    }

    private double getSpaceBetweenSubdivY() {
        return (getMargined().getHeight() - getContainedFrame().getY()) / (contained.size() + 1);
    }

    @SneakyThrows
    public void setContained(double w, double h, double padding, double margin, Color backgroundColor, Color outlineColor) {
        contained.forEach(subdiv -> {
            subdiv.setDimension(w, h);
            subdiv.setPadding(padding);
            subdiv.setMargin(margin);
            subdiv.setBackGroundColor(backgroundColor);
            subdiv.setOutlineColor(outlineColor);
        });
        repositionSubdivs();
        System.out.println(getContainedFrame());
        if (!canContain()) throw new Exception("This container is to small to contain all subdivs with those settings.");
    }

    public Point2D getContainedFrame() {
        return PageUtil.getDimension(contained);
    }

}
