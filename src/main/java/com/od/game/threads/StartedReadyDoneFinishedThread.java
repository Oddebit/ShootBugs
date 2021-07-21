package com.od.game.threads;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class StartedReadyDoneFinishedThread {

    private Instant startedTime;
    private Instant doneTime;

    long startedToReadyMillis;
    long doneToFinishedMillis;
    private StartedReadyDoneFinishedThread.ThreadState state;

    public StartedReadyDoneFinishedThread(long startedToReadyMillis, long doneToFinishedMillis) {

        this.startedToReadyMillis = startedToReadyMillis;
        this.doneToFinishedMillis = doneToFinishedMillis;
        this.state = ThreadState.FINISHED;
    }

    public void tick() {
        if(isStarted() && startedTime.plusMillis(startedToReadyMillis).isBefore(Instant.now()))
            state = ThreadState.READY;

        else if(isDone() && doneTime.plusMillis(doneToFinishedMillis).isBefore(Instant.now()))
            state = ThreadState.FINISHED;
    }

    public boolean isStarted() {
        return state == ThreadState.STARTED;
    }

    public boolean isReady() {
        return state == ThreadState.READY;
    }

    public boolean isDone() {
        return state == ThreadState.DONE;
    }

    public boolean isFinished() {
        return state == ThreadState.FINISHED;
    }

    public boolean is(ThreadState state) {
        return this.state == state;
    }

    public void set(ThreadState state) {
        this.state = state;
    }

    public void start() {
        state = ThreadState.STARTED;
        startedTime = Instant.now();
    }

    public void done() {
        state = ThreadState.DONE;
        doneTime = Instant.now();
    }

    public long getTimeMillis() {
        return ChronoUnit.MILLIS.between(startedTime, Instant.now());
    }

    public enum ThreadState {
        STARTED,
        READY,
        DONE,
        FINISHED,
    }
}
