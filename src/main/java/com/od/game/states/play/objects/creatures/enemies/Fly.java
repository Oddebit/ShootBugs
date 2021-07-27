package com.od.game.states.play.objects.creatures.enemies;

import com.od.game.data.DimensionData;
import com.od.game.states.play.threads.ChangeDirectionThread;
import com.od.game.util.GeomUtil;

import java.util.Random;

public class Fly extends Enemy{
    ChangeDirectionThread changeDirectionThread;

    public Fly() {
        super(EnemyType.FLY, 32, 1.1, 10);
        changeDirectionThread = new ChangeDirectionThread(0);
    }

    @Override
    public void tick() {
        super.tick();
        changeDirectionThread.tick();
        if(changeDirectionThread.isFinished())
            changeDirection();
    }

    public void changeDirection() {
        Random random = new Random();

        double angle = random.nextDouble() * 2*Math.PI;
        setVelocity(GeomUtil.getVector(angle, speed));

        changeDirectionThread.setStartedToFinishedTimeMillis(random.nextInt(2000) + 1000);
        changeDirectionThread.start();
    }

    @Override
    public void move() {
        if (GeomUtil.clamp(getX(), 0, DimensionData.REAL_WIDTH) != getX()) setVelX(getVelX() * -1);
        if (GeomUtil.clamp(getY(), 0, DimensionData.REAL_HEIGHT) != getY()) setVelY(getVelY() * -1);

        GeomUtil.translate(position, velocity);
    }
}
