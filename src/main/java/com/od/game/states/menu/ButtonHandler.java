package com.od.game.states.menu;

import com.od.game.data.ColorData;
import com.od.game.data.FontData;
import com.od.game.util.GeomUtil;
import lombok.Getter;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.LinkedList;

@Getter
public class ButtonHandler {

    private Button play;
    private Button options;
    private Button highScores;
    private Button exit;

    private Button selectedButton;

    private LinkedList<Button> buttons = new LinkedList<>();

    public ButtonHandler() {

        double w = 300;
        double h = 250;
        double x = GeomUtil.centerX(w);
        double y = 300;
        double space = 20;
        int size = 4;

        Point2D dimension = new Point2D.Double(w, h / size - space);
        play = new Button("Play", new Point2D.Double(x, y + h * 1 / size), dimension);
        options = new Button("Options", new Point2D.Double(x, y + h * 2 / size), dimension);
        highScores = new Button("High Scores", new Point2D.Double(x, y + h * 3 / size), dimension);
        exit = new Button("Exit", new Point2D.Double(x, y + h * 4 / size), dimension);

        buttons.add(play);
        buttons.add(options);
        buttons.add(highScores);
        buttons.add(exit);

        select(0);
    }

    private void select(int index) {
        if (selectedButton != null)
            selectedButton.setColor(ColorData.KILLCOUNT_TURQUOISE);
        selectedButton = buttons.get(index);
        selectedButton.setColor(ColorData.HEALTH_TURQUOISE);
    }

    public void selectNext(int increment) {
        int nextIndex = (int) GeomUtil.loop(getSelectedIndex() + increment, 0, buttons.size() - 1, 1);
        select(nextIndex);
    }

    private int getSelectedIndex() {
        return buttons.indexOf(selectedButton);
    }

    public void render(Graphics2D graphics) {
        graphics.setColor(ColorData.SHOOTBUGS_RED);
        graphics.setFont(FontData.TITLE);
        String title = "SHOOT";
        int x = (int) GeomUtil.centerX(graphics.getFontMetrics().stringWidth(title));
        graphics.drawString(title, x, 150);
        title = "BUGS";
        x = (int) GeomUtil.centerX(graphics.getFontMetrics().stringWidth(title));
        graphics.drawString(title, x, 230);

        buttons.forEach(button -> button.render(graphics));
    }
}
