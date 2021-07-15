package com.od.game.objects.weapons.threads;

public class ReloadThread extends StartedReadyDoneFinishedThread{

    public ReloadThread(long reloadTimeMillis) {
        super(reloadTimeMillis, 0);
    }
}
