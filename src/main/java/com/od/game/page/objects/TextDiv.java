package com.od.game.page.objects;

import com.od.game.page.alignments.HorizontalAlignment;
import com.od.game.page.alignments.VerticalAlignment;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.geom.Point2D;

@Getter
@Setter
public class TextDiv extends Div{

    private VerticalAlignment verticalAlignment = VerticalAlignment.CENTER;
    private HorizontalAlignment horizontalAlignment = HorizontalAlignment.CENTER;

    private String text = "";
    private Font font = new Font(Font.DIALOG, Font.PLAIN, (int)(getH()/2));
    private Color fontColor = Color.BLACK;

    public TextDiv(String text) {
        setText(text);
    }

    @Override
    public void render(Graphics2D graphics) {
        super.render(graphics);

        graphics.setColor(fontColor);
        graphics.setFont(font);

        String text = getAdaptedText(graphics);
        Point2D textPosition = getTextPosition(graphics, text);

        graphics.drawString(text, (int) textPosition.getX(), (int) textPosition.getY());
    }

    private String getAdaptedText(Graphics2D graphics) {
        String text = this.text;

        while (getTextWidth(graphics, text) > getMargined().getWidth()) {
            text = text.substring(0, text.length() - 1);
        }
        return text;
    }

    private Point2D getTextPosition(Graphics2D graphics, String text) {

        double width = getTextWidth(graphics, text);
        double height = getTextHeight(graphics);

        double x;
        switch (horizontalAlignment) {
            case LEFT:
                x = getMargined().getX();
                break;
            case CENTER:
            default:
                x = getMargined().getCenterX() - width / 2;
                break;
            case RIGHT:
                x = getX() + getW() - getMarginRight() - width;
                break;
        }

        double y;
        switch (verticalAlignment) {
            case TOP:
                y = getMargined().getY() + height;
                break;
            case CENTER:
            default:
                y = getMargined().getCenterY() + height / 2;
                break;
            case BOTTOM:
                y = getY() + getH() - getMarginBottom();
                break;
        }

        return new Point2D.Double(x, y);
    }

    private double getTextWidth(Graphics2D graphics, String text) {
        return graphics.getFontMetrics(font).stringWidth(text);
    }

    private double getTextHeight(Graphics2D graphics) {
        return graphics.getFontMetrics(font).getHeight();
    }
}
