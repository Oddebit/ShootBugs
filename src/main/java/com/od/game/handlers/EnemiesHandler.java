package com.od.game.handlers;

import com.od.game.Game;
import com.od.game.ID;
import com.od.game.data.ColorData;
import com.od.game.data.FontData;
import com.od.game.threads.StartedFinishedThread;
import com.od.game.objects.creatures.Creature;
import com.od.game.objects.creatures.enemies.BabySpider;
import com.od.game.objects.creatures.enemies.Bug;
import com.od.game.objects.creatures.enemies.Enemy;
import com.od.game.objects.creatures.enemies.Spider;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EnemiesHandler extends Handler<Enemy> {

    private int killCount;
    private final int miniSpiders = 4;

    private final EnemySpawnThread enemySpawnThread;
    private final int spawnTimeMillis = 1_000;

    public EnemiesHandler() {
        super(ID.ENEMY);
        this.enemySpawnThread = new EnemySpawnThread(spawnTimeMillis);
        this.killCount = 0;
    }

    @Override
    public void tick() {
        super.tick();
        enemySpawnThread.tick();
    }

    public void askSpawn(int level) {
        if(enemySpawnThread.isFinished()) {
            spawn(level);
            enemySpawnThread.start();
        }
    }

    private void spawn(int level) {
        int random = new Random().nextInt(100);

        if (random < 30 + level * 8) {
            handled.add(new Bug());
        }
        if (random < 2 + level) {
            handled.add(new Spider());
        }
    }

    public void checkDead() {
        List<BabySpider> babySpiders = new LinkedList<>();

        handled.stream()
                .filter(enemy -> enemy.isDead() && enemy.getType() == Enemy.EnemyType.SPIDER)
                .forEach(spider -> babySpiders.addAll(generateMiniSpiders(spider.getX(), spider.getY())));
        handled.addAll(babySpiders);

        killCount += (int) handled.stream().filter(Creature::isDead).count();

        handled.removeIf(Enemy::isDead);
    }

    private List<BabySpider> generateMiniSpiders(double x, double y) {
        return IntStream.range(0, miniSpiders)
                        .mapToObj(n -> new BabySpider(Math.PI * 2 / miniSpiders * n, x, y))
                        .collect(Collectors.toList());
    }

    @Override
    public void render(Graphics2D graphics) {
        super.render(graphics);

        graphics.setColor(ColorData.KILLCOUNT_TURQUOISE);
        graphics.setFont(FontData.BOLD.getFont());

        String killCount = String.valueOf(this.killCount);
        int width = graphics.getFontMetrics().stringWidth(killCount);

        graphics.drawString(killCount, Game.REAL_WIDTH - 20 - width, 30);
    }

    public static class EnemySpawnThread extends StartedFinishedThread {

        public EnemySpawnThread(long spawnTimeMillis) {
            super(spawnTimeMillis);
        }
    }
}
