package com.od.game.objects.creatures.hero;

import com.od.game.ID;
import com.od.game.data.ColorData;
import com.od.game.data.SoundData;
import com.od.game.objects.creatures.Creature;
import com.od.game.objects.creatures.hero.threads.UntouchableThread;
import com.od.game.util.GeomUtil;
import com.od.output.SoundPlayer;
import lombok.Getter;
import lombok.Setter;

import static com.od.game.Game.*;

@Getter
@Setter
public class Hero extends Creature {

    private static final float diameter = 51;

    private int maxHP = 100;
    private int HP;

    private int untouchableTimeMillis = 2500;
    private UntouchableThread untouchableThread;

    public Hero() {
        super(WIDTH_CENTER, HEIGHT_CENTER, diameter, diameter, ID.HERO);
        this.HP = maxHP;
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

    @Override
    public void move() {

        x += velocityX;
        y += velocityY;

        x = GeomUtil.clamp(x, 0, REAL_WIDTH - diameter);
        y = GeomUtil.clamp(y, 0, REAL_HEIGHT - diameter);
    }

    public boolean isUntouchable() {
        return !untouchableThread.isFinished();
    }

    public void setUntouchable() {
        untouchableThread.start();
    }
}
