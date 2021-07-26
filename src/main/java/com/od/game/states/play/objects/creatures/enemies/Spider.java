package com.od.game.states.play.objects.creatures.enemies;

import lombok.Getter;

@Getter
public class Spider extends Enemy {

    private final int miniSpiders = 4;

    public Spider() {
        super(EnemyType.SPIDER, 60, 1, 50);
    }
}