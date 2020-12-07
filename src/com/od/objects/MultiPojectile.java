package com.od.objects;

import com.od.game.ObjectHandler;
import com.od.game.SurroundingsHandler;


public class MultiPojectile {

    ObjectHandler objectHandler;
    SurroundingsHandler surroundingsHandler;
    GameObject shooter;

    private final float x;
    private final float y;
    private final float targetX;
    private final float targetY;
    private float distance;
    private double angle;

    public MultiPojectile(float targetX, float targetY, GameObject shooter, ObjectHandler objectHandler, SurroundingsHandler surroundingsHandler) {

        this.objectHandler = objectHandler;
        this.surroundingsHandler = surroundingsHandler;
        this.shooter = shooter;

        this.x = shooter.x + shooter.w/2;
        this.y = shooter.y + shooter.h/2;
        this.targetX = targetX;
        this.targetY = targetY;

        //initial triangle
        float deltaX = targetX - x;
        float deltaY = targetY - y;
        distance = (float) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

        //conversion
        deltaX /= distance;
        deltaY /= distance;


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

        createProjectiles();
    }

    public void createProjectiles() {
        for (int i = -2; i < 3; i++) {
            double tempAngle = angle + i * Math.PI/27;
            float tempTargetX = (float) (x + Math.cos(tempAngle) * 1000);
            float tempTargetY = (float) (y + Math.sin(tempAngle) * 1000);
            objectHandler.addObject(new Projectile(tempTargetX, tempTargetY, shooter, objectHandler, surroundingsHandler));
        }
    }
}
