package com.od.game.handlers;

import com.od.game.ID;
import com.od.game.objects.creatures.Hero;
import com.od.game.objects.creatures.enemies.Enemy;

public class EnemiesHandler extends Handler<Enemy> {
    public EnemiesHandler(GeneralHandler generalHandler) {
        super(generalHandler, ID.ENEMY);
    }

    @Override
    public void check() {
        Hero hero = getHero();

        toHandle.removeIf(Enemy::isDead);

        if (!hero.isUntouchable()) return;
        toHandle.stream()
                .filter(enemy -> enemy.intersects(hero))
                .forEach(enemy -> {
                    hero.removeHp(enemy.getHp());
                    hero.setUntouchable();
                });
    }
}
