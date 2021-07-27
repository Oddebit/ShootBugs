package com.od.game.states.play.threads;

public class ExplosionThread extends StartedReadyDoneFinishedThread {
    public ExplosionThread(long setToExplodeMillis, long explosionMillis) {
        super(setToExplodeMillis, explosionMillis);
    }
}
