package com.od.game.objects.bonus;

import com.od.game.Game;
import com.od.game.ID;
import com.od.game.objects.GameObjects;
import com.od.game.util.GeomUtil;
import lombok.Getter;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.time.Instant;
import java.util.Random;

@Getter
public abstract class Bonus extends GameObjects {

    private Random random = new Random();

    protected BonusType type;
    protected String name;

    private final Instant startingTime;
    private final long lifeTimeMillis = 6_000;


    public Bonus(BonusType type) {
        super(GeomUtil.randomX(), GeomUtil.randomX(), 24, 24, ID.BONUS);

        this.type = type;
        this.startingTime = Instant.now();

//        setRandomPosition();
        this.shape = new Rectangle2D.Float(x - w / 2, y - h / 2, w, h);
    }

    @Override
    public void render(Graphics2D graphics) {
        super.render(graphics);

        graphics.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
        graphics.drawString(name, (int) (x + w + 3), (int) y);
    }

    public boolean isOver() {
        return startingTime.plusMillis(lifeTimeMillis).isBefore(Instant.now());
    }

    public void setRandomPosition() {
        this.x = random.nextInt(Game.REAL_WIDTH - (int) w);
        this.y = random.nextInt(Game.REAL_HEIGHT - (int) h);
    }

    public enum BonusType {
        WEAPON,
        HEALTH
    }
}
