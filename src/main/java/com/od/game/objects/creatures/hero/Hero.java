package com.od.game.objects.creatures.hero;

import com.od.game.ID;
import com.od.game.data.ColorData;
import com.od.game.data.SoundData;
import com.od.game.objects.creatures.Creature;
import com.od.game.objects.creatures.hero.threads.UntouchableThread;
import com.od.output.SoundPlayer;
import lombok.Getter;
import lombok.Setter;

import static com.od.game.Game.HEIGHT_CENTER;
import static com.od.game.Game.WIDTH_CENTER;

@Getter
@Setter
public class Hero extends Creature {

    private static final float diameter = 51;

    private int maxHP = 100;

    private int untouchableTimeMillis = 2500;
    private UntouchableThread untouchableThread;

    public Hero() {
        super(WIDTH_CENTER, HEIGHT_CENTER, diameter, diameter, 100, ID.HERO);

        this.speed = 5;
        this.color = ColorData.HERO_ORANGE;

        this.untouchableThread = new UntouchableThread(untouchableTimeMillis);
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
