package com.od.game;

import com.od.game.objects.BloodDrop;
//import com.od.game.objects.DashBoard;
import com.od.game.objects.GameObjects;
import com.od.game.objects.bonus.Bonus;
import com.od.game.objects.creatures.hero.Hero;
import com.od.game.objects.creatures.enemies.Enemy;
import com.od.game.objects.projectiles.Projectile;
import com.od.game.objects.weapons.Weapon;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ID {
    HERO (Hero.class),
    ENEMY (Enemy.class),
    WEAPON (Weapon.class),
    PROJECTILE (Projectile.class),
    BLOOD_DROP(BloodDrop.class),
    BONUS (Bonus.class),
//    DASH_BOARD (DashBoard.class)
    ;

    Class<? extends GameObjects> thisClass;
}
