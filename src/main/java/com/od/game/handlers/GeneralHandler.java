package com.od.game.handlers;

import com.od.game.ID;
import com.od.game.objects.GameObject;
import com.od.game.objects.creatures.Hero;
import lombok.Getter;

import java.awt.*;
import java.util.LinkedList;

@Getter
public class GeneralHandler {


    LinkedList<Handler<? extends GameObject>> handlers = new LinkedList<>();

    public GeneralHandler() {
        handlers.add(new BloodDropsHandler(this));
        handlers.add(new BonusesHandler(this));
        handlers.add(new EnemiesHandler(this));
        handlers.add(new HeroHandler(this));
        handlers.add(new ProjectilesHandler(this));
        handlers.add(new WeaponsHandler(this));
    }

    public void tick() {
        handlers.forEach(Handler::tick);
    }

    public void render(Graphics graphics) {
        handlers.forEach(handler -> handler.render(graphics));
    }

    public Handler<? extends GameObject> getHandler(ID responsibility) {
        return handlers.stream().filter(handler -> handler.getResponsibility() == responsibility).findFirst().orElseThrow();
    }

    public Hero getHero() {
        return (Hero) getHandler(ID.HERO).getToHandle().getFirst();
    }
}
