package com.od.game.states.play.handlers;

import com.od.game.states.play.objects.GameObject;
import com.od.game.states.play.objects.bonus.Bonus;
import com.od.game.states.play.objects.bonus.HealthBonus;
import com.od.game.states.play.objects.bonus.WeaponBonus;
import com.od.game.states.play.objects.weapons.Weapon;
import com.od.game.states.play.threads.StartedFinishedThread;

import java.util.Optional;
import java.util.Random;

public class BonusesHandler extends PlayHandler<Bonus> {

    private final BonusSpawnThread bonusSpawnThread;
    private final long spawnTimeMillis = 30_000;

    public BonusesHandler() {
        super(GameObject.ID.BONUS);
        this.bonusSpawnThread = new BonusSpawnThread(spawnTimeMillis);
        bonusSpawnThread.start();
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
//        handled.add(new WeaponBonus(Weapon.WeaponType.SHOTGUN));
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
