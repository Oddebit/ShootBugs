package com.od.game.threads;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Getter
@Setter
public class StartedFinishedThread {

    private Instant startedTime;
    private final long startedToFinishedTimeMillis;
    private ThreadState state;

    public StartedFinishedThread(long startedToFinishedTimeMillis) {
        this.startedTime = Instant.now();
        this.startedToFinishedTimeMillis = startedToFinishedTimeMillis;
        this.state = ThreadState.FINISHED;
    }

    public void start() {
        state = ThreadState.STARTED;
        startedTime = Instant.now();
    }

    public void tick() {
        if(isStarted() && startedTime.plusMillis(startedToFinishedTimeMillis).isBefore(Instant.now()))
            state = ThreadState.FINISHED;
    }

    public boolean isStarted() {
        return state == ThreadState.STARTED;
    }

    public boolean isFinished() {
        return state == ThreadState.FINISHED;
    }

    public long getTimeMillis() {
        return ChronoUnit.MILLIS.between(startedTime, Instant.now());
    }

    public enum ThreadState {
        STARTED,
        FINISHED
    }
}
