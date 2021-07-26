//package com.od.game.objects;
//
//import com.od.game.Game;
//import com.od.game.ID;
//import com.od.game.handlers.GeneralHandler;
//import com.od.game.data.ColorData;
//import com.od.game.util.GeomUtil;
//import com.od.game.objects.creatures.hero.Hero;
//import com.od.game.objects.weapons.Weapon;
//
//import java.awt.*;
//import java.time.Instant;
//import java.time.temporal.ChronoUnit;
//
//import static com.od.game.Game.WIDTH_CENTER;
//
//public class DashBoard extends GameObject {
//
//    private final Hero hero;
//    private Weapon weapon;
//    private Instant lastWeaponActivation;
//
//    private int killCount;
//
//    public DashBoard(GeneralHandler generalHandler) {
//        super(0, 0, 0, 0, ID.DASH_BOARD);
//        this.hero = generalHandler.getHero();
//    }
//
//    @Override
//    public void tick() {
//        if (weapon == null || weapon != hero.getActiveWeapon()) {
//            weapon = hero.getActiveWeapon();
//            lastWeaponActivation = Instant.now();
//        }
//    }
//
//    @Override
//    public void render(Graphics graphics) {
//        renderKillCount(graphics);
//        renderWeaponName(graphics);
//        renderMagState(graphics);
//        renderHP(graphics);
//    }
//
//    private void renderKillCount(Graphics graphics) {
//        graphics.setColor(ColorData.KILLCOUNT_TURQUOISE);
//        graphics.setFont(new Font(Font.DIALOG, Font.BOLD, 32));
//        String name = String.valueOf(killCount);
//        int height = graphics.getFontMetrics().getHeight();
//        int width = graphics.getFontMetrics().stringWidth(name);
//        graphics.drawString(name, Game.REAL_WIDTH - 10 - width, (int) (3d / 4 * height));
//    }
//
//    private void renderWeaponName(Graphics graphics) {
//        long period = ChronoUnit.MILLIS.between(lastWeaponActivation, Instant.now());
//        graphics.setColor(ColorData.HERO_ORANGE);
//        graphics.setFont(new Font(Font.DIALOG, Font.BOLD, 48));
//        String name = weapon.getWeaponType().toString();
//        int height = graphics.getFontMetrics().getHeight();
//        int width = graphics.getFontMetrics().stringWidth(name);
//        graphics.drawString(name, (int) (10 - Math.pow(period / 100d - Math.sqrt(width), 2)), (int) (3d / 4 * height));
//    }
//
//    private void renderMagState(Graphics graphics) {
//
//        double reloadState;
//
//        if (!weapon.isReloading()) {
//            reloadState = weapon.getMagState();
//
//        } else if (weapon.hasTotalMunitionLeft()) {
//            reloadState = weapon.getReloadState();
//
//        } else {
//            reloadState = 0;
//        }
//
//        int height = 10;
//        int width = 300;
//        reloadState *= width;
//
//        graphics.setColor(ColorData.HERO_ORANGE);
//        graphics.drawRect((int)(WIDTH_CENTER - width/2d), 25, width, height);
//        graphics.fillRect((int)(WIDTH_CENTER - width/2d), 25, (int)reloadState, height);
//    }
//
//    private void renderHP(Graphics graphics) {
//        int hp = hero.getHp();
//        int height = 10;
//        int width = 300;
//        int red = (int) GeomUtil.clamp(360 - 3.6f * hp, 0, 255);
//        int green = (int) GeomUtil.clamp((int)(-1.8 + 3.6f * hp), 0, 255);
//        int blue = (int) GeomUtil.clamp(1.8f * hp, 0, 255);
//        graphics.setColor(new Color(red, green, blue));
//        graphics.drawRect((int)(WIDTH_CENTER - width/2d), 10, width, height);
//        graphics.fillRect((int)(WIDTH_CENTER - width/2d), 10, hp * width/hero.getMaxHP(), height);
//    }
//
//    @Override
//    public Rectangle getBounds() {
//        return new Rectangle(-1000, -1000, 0, 0);
//    }
//
//    public void addKill() {
//        this.killCount++;
//    }
//
//    public int getKillCount() {
//        return killCount;
//    }
//}
