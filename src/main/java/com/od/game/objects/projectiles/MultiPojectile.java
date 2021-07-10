package com.od.game.objects.projectiles;

import com.od.game.handlers.ObjectHandler;
import com.od.game.handlers.SurroundingsHandler;
import com.od.game.util.GeomUtil;
import com.od.game.objects.GameObject;


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

        this.x = shooter.getX() + shooter.getW()/2;
        this.y = shooter.getY() + shooter.getH()/2;
        this.targetX = targetX;
        this.targetY = targetY;

        //initial triangle
        float deltaX = targetX - x;
        float deltaY = targetY - y;
        distance = (float) Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));

        //conversion
        deltaX /= distance;
        deltaY /= distance;


        GeomUtil.getAngle(deltaX, deltaY);

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
