package com.od.game.states.play.objects.handlers;

import com.od.game.data.ColorData;
import com.od.game.data.DimensionData;
import com.od.game.data.FontData;
import com.od.game.states.play.objects.GameObject;
import com.od.game.states.play.objects.creatures.Creature;
import com.od.game.states.play.objects.creatures.enemies.*;
import com.od.game.states.play.threads.StartedFinishedThread;
import lombok.Getter;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class EnemiesHandler extends PlayHandler<Enemy> {

    private int killCount;
    private final int miniSpiders = 5;

    private final EnemySpawnThread enemySpawnThread;
    private final int spawnTimeMillis = 1_000;

    public EnemiesHandler() {
        super(GameObject.ID.ENEMY);
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
        Random random = new Random();

        if (random.nextInt(100) < 25 + level * 3) {
            handled.add(new Bug());
        }
        if (random.nextInt(100) < 10 + level * 2) {
            handled.add(new Fly());
        }
        if (random.nextInt(100) < 2 + level * 1) {
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
                        .mapToObj(n -> new BabySpider(x, y))
                        .collect(Collectors.toList());
    }

    @Override
    public void render(Graphics2D graphics) {
        super.render(graphics);

        graphics.setColor(ColorData.KILLCOUNT_TURQUOISE);
        graphics.setFont(FontData.BOLD);

        String killCount = String.valueOf(this.killCount);
        int width = graphics.getFontMetrics().stringWidth(killCount);

        graphics.drawString(killCount, DimensionData.REAL_WIDTH - 20 - width, 30);
    }

    public static class EnemySpawnThread extends StartedFinishedThread {

        public EnemySpawnThread(long spawnTimeMillis) {
            super(spawnTimeMillis);
        }
    }
}
