package com.od.game.util;

import com.od.game.Game;

import java.util.Random;

public class GeomUtil {

    public static double getAngle(double deltaX, double deltaY) {

        double angle;

        if(deltaX >= 0 && deltaY >= 0) {
            //4e quadran
            angle = Math.acos(deltaX);
        } else if(deltaX < 0 && deltaY >= 0) {
            //3e quadran
            angle = Math.acos(deltaX);
        } else if(deltaX < 0 && deltaY < 0) {
            //2e quadran
            angle = Math.PI - Math.asin(deltaY);
        } else {
            //1er quadran
            angle = Math.asin(deltaY);
        }
        return angle;
    }

    public static float clamp(float var, float min, float max) {
        if (var >= max)
            return max;
        else if (var <= min)
            return min;
        else
            return var;
    }

    public static float randomX() {
        return new Random().nextInt(Game.REAL_WIDTH);
    }

    public static float randomY() {
        return new Random().nextInt(Game.REAL_HEIGHT);
    }
}
