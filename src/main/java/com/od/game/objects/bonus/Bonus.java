package com.od.game.objects.bonus;

import com.od.game.Game;
import com.od.game.ID;
import com.od.game.handlers.ObjectHandler;
import com.od.game.objects.GameObject;
import com.od.game.objects.creatures.Hero;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.time.Instant;
import java.util.Random;

@Getter
@Setter
public abstract class Bonus extends GameObject {

    private Random random = new Random();

    protected Color color;
    protected String name;

    ObjectHandler objectHandler;
    Hero hero;

    private Instant startingTime;
    private long lifeTimeMillis;


    public Bonus(ObjectHandler objectHandler) {
        super(0, 0, 24, 24, ID.Bonus);

        this.objectHandler = objectHandler;

        this.startingTime = Instant.now();
        this.lifeTimeMillis = 6000;

        for (int i = 0; i < objectHandler.objects.size(); i++) {
            GameObject tempObject = objectHandler.objects.get(i);
            if (tempObject.getId() == ID.Hero) hero = (Hero) tempObject;
        }

        setRandomPosition();
    }

    @Override
    public void tick() {

        askRemove();
    }

    private void askRemove() {
        if (startingTime.plusMillis(lifeTimeMillis).isBefore(Instant.now()))
            remove();
    }

    private void remove() {
        objectHandler.removeObject(this);
    }

    @Override
    public void render(Graphics graphics) {

        graphics.setColor(color);
        graphics.setFont(new Font(Font.DIALOG, Font.PLAIN, 12));

        graphics.drawString(name, (int) (x + w + 3), (int) y);
        graphics.fillRect((int) x, (int) y, (int) w, (int) h);
    }

    public abstract void getBonus(Hero hero);

    public void setRandomPosition() {
        this.x = random.nextInt(Game.REAL_WIDTH - (int) w);
        this.y = random.nextInt(Game.REAL_HEIGHT - (int) h);
    }
}
