package com.od.game.objects.threads;

import com.od.game.objects.weapons.Weapon;

public class ReloadThread extends Thread{

    private final Weapon weapon;


    public ReloadThread(Weapon weapon) {
        this.weapon = weapon;
    }

    @Override
    public synchronized void run() {

        try {
            wait(weapon.getReloadTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
