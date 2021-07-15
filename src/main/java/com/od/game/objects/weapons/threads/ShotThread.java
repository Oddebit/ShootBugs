package com.od.game.objects.weapons.threads;

import lombok.Getter;

@Getter
public class ShotThread extends StartedReadyDoneFinishedThread {

    private float targetX;
    private float targetY;

    public ShotThread(long beforeShotMillis, long afterShotMillis) {
        super(beforeShotMillis, afterShotMillis);
    }

    public void setTarget(float x, float y) {
        targetX = x;
        targetY = y;
    }
}
