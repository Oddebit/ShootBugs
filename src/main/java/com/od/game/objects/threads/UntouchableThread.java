package com.od.game.objects.threads;

import com.od.game.objects.creatures.Hero;

public class UntouchableThread extends Thread{

    private final Hero hero;

    public UntouchableThread(Hero hero) {
        this.hero = hero;
    }

    @Override
    public void run() {

        try {
            wait(hero.getUntouchableTimeMillis());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
