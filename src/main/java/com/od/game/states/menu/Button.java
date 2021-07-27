package com.od.game.states.menu;

import com.od.game.data.ColorData;
import com.od.game.data.FontData;
import com.od.game.states.play.objects.GameObject;
import com.od.game.util.GeomUtil;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

@Getter
@Setter
public class Button extends GameObject {

    private String name;

    public Button(String name, Point2D position, Point2D dimension) {
        super(position, dimension, ID.BUTTON);
        setName(name);
        setShape(new Rectangle2D.Double(getX(), getY(), getW(), getH()));
        setColor(ColorData.KILLCOUNT_TURQUOISE);
    }

    @Override
    public void render(Graphics2D graphics) {
        graphics.setColor(color);
        graphics.draw(shape);
        graphics.setFont(FontData.BOLD);
        int x = (int) GeomUtil.centerX(graphics.getFontMetrics().stringWidth(name));
        int y = (int) (getY() + getH() * 3/4);
        graphics.drawString(name, x, y);
    }
}
