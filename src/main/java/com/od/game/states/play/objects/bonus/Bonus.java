package com.od.game.states.play.objects.bonus;

import com.od.game.states.play.objects.GameObject;
import com.od.game.util.GeomUtil;
import lombok.Getter;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.time.Instant;
import java.util.Random;

@Getter
public abstract class Bonus extends GameObject {

    private final Random random = new Random();

    protected final BonusType type;
    protected String name;

    private final Instant startingTime;
    private final long lifeTimeMillis = 6_000;


    public Bonus(BonusType type) {
        super(GeomUtil.randomPosition(24), GeomUtil.getSquare(24), ID.BONUS);

        this.type = type;
        this.startingTime = Instant.now();

//        setRandomPosition();
        this.shape = new Rectangle2D.Double();
        shape.setFrame(getFrame());
    }

    @Override
    public void render(Graphics2D graphics) {
        super.render(graphics);

        graphics.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));
        graphics.drawString(name, (int) (position.getX() + dimension.getX() + 1), (int) position.getY());
    }

    public boolean isOver() {
        return startingTime.plusMillis(lifeTimeMillis).isBefore(Instant.now());
    }

//    public void setRandomPosition() {
//
//        double x = random.nextInt(Game.REAL_WIDTH - (int) dimension.getX());
//        double y = random.nextInt(Game.REAL_HEIGHT - (int) dimension.getY());
//        position.setLocation(x, y);
//    }

    public enum BonusType {
        WEAPON,
        HEALTH
    }
}
