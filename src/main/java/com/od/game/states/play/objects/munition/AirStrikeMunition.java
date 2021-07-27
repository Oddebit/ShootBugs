package com.od.game.states.play.objects.munition;

import com.od.game.states.play.objects.GameObject;
import com.od.game.states.play.threads.StartedFinishedThread;

public class AirStrikeMunition extends Munition{

    StartedFinishedThread strikeThread;

    public AirStrikeMunition() {
        super(0, 1000, 0, 0, false);
        this.strikeThread = new StartedFinishedThread(200);
        strikeThread.start();
    }

    @Override
    public void tick() {
        super.tick();
        strikeThread.tick();
    }

    @Override
    public boolean intersects(GameObject gameObject) {
        return true;
    }

    @Override
    public boolean isOver() {
        return strikeThread.isFinished();
    }
}
