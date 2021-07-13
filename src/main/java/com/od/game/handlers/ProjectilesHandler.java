package com.od.game.handlers;

import com.od.game.ID;
import com.od.game.objects.BloodDrop;
import com.od.game.objects.GameObject;
import com.od.game.objects.creatures.enemies.Enemy;
import com.od.game.objects.projectiles.Projectile;
import com.od.game.objects.weapons.Weapon;

import java.util.LinkedList;
import java.util.List;

public class ProjectilesHandler extends Handler<Projectile> {
    public ProjectilesHandler(GeneralHandler generalHandler) {
        super(generalHandler, ID.PROJECTILE);
    }

    @Override
    public void check() {
        List<Projectile> projectilesToRemove = new LinkedList<>();
        Handler<BloodDrop> bloodHandler = (Handler<BloodDrop>) generalHandler.getHandler(ID.BLOOD);

        toHandle.removeIf(Projectile::isOver);

        toHandle.forEach(projectile -> {
            ((LinkedList<Enemy>)generalHandler.getHandler(ID.ENEMY).getToHandle()).stream()
                    .filter(enemy -> enemy.intersects(projectile))
                    .forEach(enemy -> {
                        int damage = projectile.getDamage();
                        enemy.removeHp(damage);
                        bloodHandler.add(new BloodDrop(damage, projectile.getX(), projectile.getY()));
                        //fixme:: no hardcode pls
                        if (!projectile.getWeapon().getType().equals(Weapon.Type.Sniper))
                            projectilesToRemove.add(projectile);
                    });
        });
        toHandle.removeAll(projectilesToRemove);
    }
}
