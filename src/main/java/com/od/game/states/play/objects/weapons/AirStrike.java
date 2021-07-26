package com.od.game.states.play.objects.weapons;

public class AirStrike extends Weapon {

    public AirStrike() {
        super(WeaponType.AIR_STRIKE,
                1000, 1000, 1000, 1000,
                1, 1,
                2000, 2000, 2000);
    }

//    @Override
//    public void initShot(float targetX, float targetY) {
//
//        super.initShot(targetX, targetY);
//        shootSound();
//    }

//    @Override
//    public void shoot() {
////        for (int i = 0; i < objectHandler.objects.size(); i++) {
////
////            GameObject tempObject = objectHandler.objects.get(i);
////            if (tempObject.getId() == ID.Enemy) {
////                for (int j = 0; j < ((Enemy) tempObject).getHp(); j++) {
////                    surroundingsHandler.addSurrounding(new Blood(tempObject.getX(), tempObject.getY(), objectHandler));
////                }
////                ((Enemy) tempObject).setHp((0));
////            }
////        }
//    }
}
