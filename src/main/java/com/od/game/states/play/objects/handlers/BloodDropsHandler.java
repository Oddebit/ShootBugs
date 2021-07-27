package com.od.game.states.play.objects.handlers;

import com.od.game.states.play.objects.BloodDrop;
import com.od.game.states.play.objects.GameObject;
import com.od.game.util.GeomUtil;

import java.awt.geom.Point2D;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BloodDropsHandler extends PlayHandler<BloodDrop> {
    public BloodDropsHandler() {
        super(GameObject.ID.BLOOD_DROP);
    }


    public void addBlood(int amount, Point2D position) {
        Random random = new Random();
        handled.addAll(
                IntStream.range(0, amount)
                        .mapToObj(n -> new BloodDrop(GeomUtil.getCopy(position), random.nextInt(24)))
                        .collect(Collectors.toList()));
    }

    public void checkOver() {
        handled.removeIf(BloodDrop::isOver);
    }
}
