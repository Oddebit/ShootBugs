package com.od.game.handlers;

import com.od.game.ID;
import com.od.game.objects.BloodDrop;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BloodDropsHandler extends Handler<BloodDrop> {
    public BloodDropsHandler(GeneralHandler generalHandler) {
        super(generalHandler, ID.BLOOD);
    }

    @Override
    public void check() {
        toHandle.removeIf(BloodDrop::isOver);
    }


    public void addBlood(int amount, float x, float y) {
        toHandle.addAll(
                IntStream.range(0, amount)
                        .mapToObj(n -> new BloodDrop(x, y))
                        .collect(Collectors.toList()));
    }
}
