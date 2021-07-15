package com.od.game.handlers;

import com.od.game.ID;
import com.od.game.objects.projectiles.Projectile;
import com.od.game.objects.weapons.Weapon;
import com.od.game.util.GeomUtil;

public class ProjectilesHandler extends Handler<Projectile> {

    private final int shotgunProjectiles = 6;

    public ProjectilesHandler() {
        super(ID.PROJECTILE);
    }

    public void createProjectile(Weapon weapon, float x, float y, float targetX, float targetY) {

        handled.add(new Projectile(weapon, x, y, targetX, targetY));
    }

    public void createShotgunProjectiles(Weapon weapon, float x, float y, float targetX, float targetY) {

        float deltaX = targetX - x;
        float deltaY = targetY - y;
        double angle = GeomUtil.getAngle(deltaX, deltaY);

        for (int i = -2; i < 3; i++) {

            double tempAngle = angle + i * Math.PI/27;
            float newTargetX = (float) (x + Math.cos(tempAngle) * 1000);
            float newTargetY = (float) (y + Math.sin(tempAngle) * 1000);
            createProjectile(weapon, x, y, newTargetX, newTargetY);
        }
    }

    public void checkProjectiles() {
        handled.removeIf(Projectile::isOver);
    }
}
