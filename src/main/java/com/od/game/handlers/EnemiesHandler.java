package com.od.game.handlers;

import com.od.game.ID;
import com.od.game.handlers.threads.StartedFinishedThread;
import com.od.game.objects.creatures.enemies.BabySpider;
import com.od.game.objects.creatures.enemies.Bug;
import com.od.game.objects.creatures.enemies.Enemy;
import com.od.game.objects.creatures.enemies.Spider;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EnemiesHandler extends Handler<Enemy> {

    private final int miniSpiders = 4;

    private final EnemySpawnThread enemySpawnThread;
    private final int spawnTimeMillis = 1_000;

    public EnemiesHandler() {
        super(ID.ENEMY);
        this.enemySpawnThread = new EnemySpawnThread(spawnTimeMillis);
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
        handled.removeIf(Enemy::isDead);
    }

    private List<BabySpider> generateMiniSpiders(float x, float y) {
        return IntStream.range(0, miniSpiders)
                        .mapToObj(n -> new BabySpider(Math.PI * 2 / miniSpiders * n, x, y))
                        .collect(Collectors.toList());
    }


    public static class EnemySpawnThread extends StartedFinishedThread {

        public EnemySpawnThread(long spawnTimeMillis) {
            super(spawnTimeMillis);
        }
    }
}
