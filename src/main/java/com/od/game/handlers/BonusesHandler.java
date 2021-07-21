package com.od.game.handlers;

import com.od.game.ID;
import com.od.game.objects.bonus.Bonus;
import com.od.game.objects.bonus.HealthBonus;
import com.od.game.objects.bonus.WeaponBonus;
import com.od.game.objects.weapons.Weapon;
import com.od.game.threads.StartedFinishedThread;

import java.util.Optional;
import java.util.Random;

public class BonusesHandler extends Handler<Bonus> {

    private final BonusSpawnThread bonusSpawnThread;
    private final long spawnTimeMillis = 30_000;

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
        int rnd = new Random().nextInt(len);

        if (rnd == Weapon.WeaponType.PISTOL.ordinal())
            handled.add(new HealthBonus());
        else
            handled.add(new WeaponBonus(Weapon.WeaponType.values()[rnd]));
    }

    public Optional<Bonus> getBonus() {
        return handled.stream().findFirst();
    }

    public void checkOver() {
        handled.removeIf(Bonus::isOver);
    }

    public void clear() {
        handled.clear();
    }

    public static class BonusSpawnThread extends StartedFinishedThread {

        public BonusSpawnThread(long spawnTimeMillis) {
            super(spawnTimeMillis);
        }
    }
}
