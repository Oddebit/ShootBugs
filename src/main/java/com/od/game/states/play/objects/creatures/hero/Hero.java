package com.od.game.states.play.objects.creatures.hero;

import com.od.game.data.ColorData;
import com.od.game.data.DimensionData;
import com.od.game.data.SoundData;
import com.od.game.states.play.objects.creatures.Creature;
import com.od.game.states.play.threads.UntouchableThread;
import com.od.output.SoundPlayer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Hero extends Creature {

    private int untouchableTimeMillis = 2500;
    private UntouchableThread untouchableThread;

    public Hero() {
        super(51,3.5,100, ID.HERO);

        this.color = ColorData.HERO_ORANGE;

        this.untouchableThread = new UntouchableThread(untouchableTimeMillis);
    }

    @Override
    public void setStartingPosition() {
        setPosition(DimensionData.WIDTH_CENTER, DimensionData.HEIGHT_CENTER);
    }

    @Override
    public void tick() {
        super.tick();
        untouchableThread.tick();
    }

    public void die() {
        dieSound();
    }

    public void dieSound() {
        SoundData.getSoundLocation(this.getClass(), SoundData.Action.DIE).ifPresent(SoundPlayer::playSound);
    }

    public boolean isUntouchable() {
        return !untouchableThread.isFinished();
    }

    public void setUntouchable() {
        untouchableThread.start();
    }
}
