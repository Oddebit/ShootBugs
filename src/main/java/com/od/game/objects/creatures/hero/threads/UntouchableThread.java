package com.od.game.objects.creatures.hero.threads;

import com.od.game.handlers.threads.StartedFinishedThread;

public class UntouchableThread extends StartedFinishedThread {

    public UntouchableThread(long untouchableTimeMillis) {
        super(untouchableTimeMillis);
    }
}
