package com.od.game.util;

import com.od.game.data.DimensionData;
import com.od.game.states.play.objects.GameObject;

import java.awt.geom.Point2D;
import java.util.Random;

public class GeomUtil {

    public static boolean intersects(GameObject gameObject1, GameObject gameObject2){
        return getDistance(gameObject1, gameObject2) < gameObject1.getW()/2 + gameObject2.getW()/2;
    }



    public static double getAngle(Point2D vector) {

        double deltaX = vector.getX();
        double deltaY = vector.getY();
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

    public static double getDistance(GameObject from, GameObject to) {
        return getDistance(from.getPosition(), to.getPosition());
    }

    public static double getDistance(Point2D from, Point2D to) {
        return to.distance(from);
    }


    public static Point2D getVector(double angle, double norm) {
        return new Point2D.Double(Math.cos(angle) * norm, Math.sin(angle) * norm);
    }

    public static Point2D getVector(Point2D position1, Point2D position2) {
        return new Point2D.Double(position2.getX() - position1.getX(), position2.getY() - position1.getY());
    }

    public static Point2D getVector(Point2D position1, Point2D position2, double norm) {
        Point2D vector = getTrigonometricVector(position1, position2);
        return new Point2D.Double(vector.getX() * norm, vector.getY() * norm);
    }

    public static Point2D getTrigonometricVector(Point2D position1, Point2D position2) {
        Point2D vector = getVector(position1, position2);
        double distance = getDistance(position1, position2);
        return new Point2D.Double(vector.getX()/distance, vector.getY()/distance);
    }


    public static Point2D getPoint(Point2D position, Point2D vector) {
        return new Point2D.Double(position.getX() + vector.getX(), position.getY() + vector.getY());
    }



    public static Point2D randomPosition(double side) {
        return new Point2D.Double(randomX(side), randomY(side));
    }

    public static double randomX(double dimension) {
        return dimension/2 + new Random().nextInt(DimensionData.REAL_WIDTH - (int) dimension);
    }

    public static double randomY(double dimension) {
        return dimension/2 + new Random().nextInt(DimensionData.REAL_HEIGHT - (int) dimension);
    }

    public static float randomX() {
        return new Random().nextInt(DimensionData.REAL_WIDTH);
    }

    public static float randomY() {
        return new Random().nextInt(DimensionData.REAL_HEIGHT);
    }



    public static void translate(Point2D position, double dx, double dy) {
        translate(position, new Point2D.Double(dx, dy));
    }

    public static void translate(Point2D position, Point2D vector) {
        position.setLocation(position.getX() + vector.getX(), position.getY() + vector.getY());
    }

    public static void translateAndClamp(Point2D position, Point2D vector) {
        double x = clamp(position.getX() + vector.getX(), 0, DimensionData.REAL_WIDTH);
        double y = clamp(position.getY() + vector.getY(), 0, DimensionData.REAL_HEIGHT);
        position.setLocation(x, y);
    }

    public static double clamp(double var, double min, double max) {
        if (var >= max)
            return max;
        else return Math.max(var, min);
    }

    public static Point2D getSquare(double side) {
        return new Point2D.Double(side, side);
    }

    public static Point2D getCopy(Point2D point) {
        return new Point2D.Double(point.getX(), point.getY());
    }
}
