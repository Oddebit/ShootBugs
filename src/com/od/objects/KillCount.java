package com.od.objects;

import com.od.game.Game;
import com.od.game.ID;
import com.od.game.ObjectHandler;

import java.awt.*;

public class KillCount extends GameObject {

    protected ObjectHandler objectHandler;
    protected Hero hero;

    public KillCount(ObjectHandler objectHandler) {
        super(0, 0, 0, 0, ID.KillCount);
        this.objectHandler = objectHandler;
        for (int i = 0; i < objectHandler.objects.size(); i++) {
            GameObject tempObject = objectHandler.objects.get(i);
            if (tempObject.getId() == ID.Hero) hero = (Hero) tempObject;
        }
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics graphics) {
        graphics.setColor(new Color(0, 95, 95));
        graphics.setFont(new Font(Font.DIALOG, Font.BOLD, 32));
        String name = String.valueOf(hero.getKillCount());
        int height = graphics.getFontMetrics().getHeight();
        int width = graphics.getFontMetrics().stringWidth(name);
        graphics.drawString(name, Game.REAL_WIDTH - 10 - width, (int) (3d / 4 * height));
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(-1000,-1000,0,0);
    }
}
