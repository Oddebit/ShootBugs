package com.od.game.handlers;

import com.od.game.ID;
import com.od.game.handlers.threads.StartedFinishedThread;
import com.od.game.objects.bonus.Bonus;
import com.od.game.objects.bonus.HealthBonus;
import com.od.game.objects.bonus.WeaponBonus;
import com.od.game.objects.weapons.Weapon;

import java.util.Random;

public class BonusesHandler extends Handler<Bonus> {

    private final BonusSpawnThread bonusSpawnThread;
    private final long spawnTimeMillis = 5_000;

    public BonusesHandler() {
        super(ID.BONUS);
        this.bonusSpawnThread = new BonusSpawnThread(spawnTimeMillis);
    }

    @Override
    public void tick() {
        super.tick();
        bonusSpawnThread.tick();
    }

    public void askSpawn() {
        if (bonusSpawnThread.isFinished()) {
            spawn();
            bonusSpawnThread.start();
        }
    }

    private void spawn() {
        int len = Weapon.WeaponType.values().length;
        int rnd = new Random().nextInt(len + 1);

        if (rnd == len)
            handled.add(new HealthBonus());
        else
            handled.add(new WeaponBonus(Weapon.WeaponType.values()[rnd]));
    }

    public void checkOver() {
        handled.removeIf(Bonus::isOver);
    }

    public static class BonusSpawnThread extends StartedFinishedThread {

        public BonusSpawnThread(long spawnTimeMillis) {
            super(spawnTimeMillis);
        }
    }
}
