package com.od.game.states.play.threads;

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
