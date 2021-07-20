package com.od.game.handlers;

import com.od.game.ID;
import com.od.game.objects.BloodDrop;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BloodDropsHandler extends Handler<BloodDrop> {
    public BloodDropsHandler() {
        super(ID.BLOOD_DROP);
    }


    public void addBlood(int amount, double x, double y) {
        Random random = new Random();
        handled.addAll(
                IntStream.range(0, amount)
                        .mapToObj(n -> new BloodDrop(x, y, random.nextInt(24)))
                        .collect(Collectors.toList()));
    }

    public void checkOver() {
        handled.removeIf(BloodDrop::isOver);
    }
}
