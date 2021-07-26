package com.od.game.states.play.threads;

public class ReloadThread extends StartedReadyDoneFinishedThread {

    public ReloadThread(long reloadTimeMillis) {
        super(reloadTimeMillis, 0);
    }
}
