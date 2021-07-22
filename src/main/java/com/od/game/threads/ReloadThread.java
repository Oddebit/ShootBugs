package com.od.game.threads;

import com.od.game.threads.StartedReadyDoneFinishedThread;

public class ReloadThread extends StartedReadyDoneFinishedThread {

    public ReloadThread(long reloadTimeMillis) {
        super(reloadTimeMillis, 0);
    }
}
