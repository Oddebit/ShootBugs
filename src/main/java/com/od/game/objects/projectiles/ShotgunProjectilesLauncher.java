package com.od.game.objects.projectiles;

import com.od.game.ID;
import com.od.game.handlers.GeneralHandler;
import com.od.game.util.GeomUtil;
import com.od.game.objects.GameObject;


public class ShotgunProjectilesLauncher {

    private final GeneralHandler generalHandler;
    GameObject shooter;

    private final float x;
    private final float y;
    private final float targetX;
    private final float targetY;
    private float distance;
    private double angle;

    public ShotgunProjectilesLauncher(float targetX, float targetY, GameObject shooter, GeneralHandler generalHandler) {

        this.generalHandler = generalHandler;
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
            generalHandler.getHandler(ID.PROJECTILE).add(new Projectile(tempTargetX, tempTargetY, shooter, generalHandler));
        }
    }
}
