package com.od.game.objects;

import com.od.game.Game;
import com.od.game.ID;
import com.od.game.handlers.ObjectHandler;
import com.od.game.data.ColorData;
import com.od.game.util.GeomUtil;
import com.od.game.objects.creatures.Hero;
import com.od.game.objects.weapons.Weapon;

import java.awt.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static com.od.game.Game.WIDTH_CENTER;

public class DashBoard extends GameObject {

    private ObjectHandler objectHandler;

    private Hero hero;
    private int HP;

    private Weapon weapon;
    private Instant lastWeaponActivation;

    private int killCount;

    public DashBoard(ObjectHandler objectHandler) {
        super(0, 0, 0, 0, ID.DashBoard);
        this.objectHandler = objectHandler;
        for (int i = 0; i < objectHandler.objects.size(); i++) {
            GameObject tempObject = objectHandler.objects.get(i);
            if (tempObject.getId() == ID.Hero) hero = (Hero) tempObject;
        }
    }

    @Override
    public void tick() {
        if (weapon == null || weapon != hero.getActiveWeapon()) {
            weapon = hero.getActiveWeapon();
            lastWeaponActivation = Instant.now();
        }
        HP = hero.getHP();
    }

    @Override
    public void render(Graphics graphics) {
        renderKillCount(graphics);
        renderWeaponName(graphics);
        renderMagState(graphics);
        renderHP(graphics);
    }

    private void renderKillCount(Graphics graphics) {
        graphics.setColor(new Color(0, 120, 120));
        graphics.setFont(new Font(Font.DIALOG, Font.BOLD, 32));
        String name = String.valueOf(killCount);
        int height = graphics.getFontMetrics().getHeight();
        int width = graphics.getFontMetrics().stringWidth(name);
        graphics.drawString(name, Game.REAL_WIDTH - 10 - width, (int) (3d / 4 * height));
    }

    private void renderWeaponName(Graphics graphics) {
        long period = ChronoUnit.MILLIS.between(lastWeaponActivation, Instant.now());
        graphics.setColor(ColorData.HERO_ORANGE);
        graphics.setFont(new Font(Font.DIALOG, Font.BOLD, 48));
        String name = weapon.getType().toString();
        int height = graphics.getFontMetrics().getHeight();
        int width = graphics.getFontMetrics().stringWidth(name);
        graphics.drawString(name, (int) (10 - Math.pow(period / 100d - Math.sqrt(width), 2)), (int) (3d / 4 * height));
    }

    private void renderMagState(Graphics graphics) {

        double reloadState;

        if (!weapon.isReloading()) {
            reloadState = weapon.getMagState();

        } else if (weapon.hasMunitionLeft()) {
            reloadState = weapon.getReloadState();

        } else {
            reloadState = 0;
        }

        int height = 10;
        int width = 300;
        reloadState *= width;

        graphics.setColor(ColorData.HERO_ORANGE);
        graphics.drawRect((int)(WIDTH_CENTER - width/2d), 25, width, height);
        graphics.fillRect((int)(WIDTH_CENTER - width/2d), 25, (int)reloadState, height);
    }

    private void renderHP(Graphics graphics) {
        int height = 10;
        int width = 300;
        int red = (int) GeomUtil.clamp(360 - 3.6f * HP, 0, 255);
        int green = (int) GeomUtil.clamp((int)(-1.8 + 3.6f * HP), 0, 255);
        int blue = (int) GeomUtil.clamp(1.8f * HP, 0, 255);
        graphics.setColor(new Color(red, green, blue));
        graphics.drawRect((int)(WIDTH_CENTER - width/2d), 10, width, height);
        graphics.fillRect((int)(WIDTH_CENTER - width/2d), 10, HP * width/hero.getMaxHP(), height);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(-1000, -1000, 0, 0);
    }

    public void addKill() {
        this.killCount++;
    }

    public int getKillCount() {
        return killCount;
    }
}