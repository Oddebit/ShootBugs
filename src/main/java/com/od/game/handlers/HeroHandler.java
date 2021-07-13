package com.od.game.handlers;

import com.od.game.ID;
import com.od.game.objects.creatures.Hero;

public class HeroHandler extends Handler<Hero>{
    public HeroHandler(GeneralHandler generalHandler) {
        super(generalHandler, ID.HERO);
    }

    @Override
    public void check() {
//        if(toHandle.getFirst().isDead()) game.setState(Game.State.GameOver);
    }
}
