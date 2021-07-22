package com.od.game.threads;

import com.od.game.threads.StartedFinishedThread;
import lombok.Getter;

@Getter
public class BurstThread extends StartedFinishedThread {

    public BurstThread() {
        super(0);
    }

    @Override
    public void tick() {
    }

    public void finish() {
        setState(ThreadState.FINISHED);
    }
}
