package com.od.game.handlers;

import com.od.game.ID;
import com.od.game.objects.BloodDrop;

public class BloodDropsHandler extends Handler<BloodDrop> {
    public BloodDropsHandler(GeneralHandler generalHandler) {
        super(generalHandler, ID.BLOOD);
    }

    @Override
    public void check() {
        toHandle.removeIf(BloodDrop::isOver);
    }
}
