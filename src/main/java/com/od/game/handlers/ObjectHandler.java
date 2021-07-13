package com.od.game.handlers;

import com.od.game.Game;
import com.od.game.Spawner;
import com.od.game.objects.BloodDrop;
import com.od.game.objects.GameObject;
import com.od.game.objects.bonus.Bonus;
import com.od.game.objects.creatures.Hero;
import com.od.game.objects.creatures.enemies.Enemy;
import com.od.game.objects.projectiles.Projectile;
import com.od.game.objects.weapons.Weapon;
import lombok.Getter;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Getter
public class ObjectHandler {

    private Game game;

    private final Hero hero;
    private final LinkedList<Enemy> enemies = new LinkedList<>();
    private final LinkedList<Projectile> projectiles = new LinkedList<>();
    private final LinkedList<Bonus> bonuses = new LinkedList<>();
    private final LinkedList<BloodDrop> bloodDrops = new LinkedList<>();

    LinkedList<GameObject> objects = new LinkedList<>();

    public ObjectHandler(Game game) {
        this.hero = new Hero();
        new Spawner(this, hero);
    }


    public void render(Graphics graphics) {

        objects.forEach(o -> o.render(graphics));
    }

    public void tick() {
        updateObjects();
        objects.forEach(GameObject::tick);
    }

    private void updateObjects() {
        objects.clear();
        objects.add(hero);
        objects.addAll(enemies);
        objects.addAll(projectiles);
        objects.addAll(bonuses);
        objects.addAll(bloodDrops);
    }

    public void check() {
        checkEnemies();
        checkProjectiles();
        checkBonuses();
        checkHero();
    }

    private void checkHero() {
        if(hero.isDead()) game.setState(Game.State.GameOver);
    }

    private void checkEnemies() {

        enemies.removeIf(Enemy::isDead);

        if (!hero.isUntouchable()) return;
        enemies.stream()
                .filter(enemy -> enemy.intersects(hero))
                .forEach(enemy -> {
                    hero.removeHp(enemy.getHp());
                    hero.setUntouchable();
                });
    }

    private void checkProjectiles() {

        projectiles.removeIf(Projectile::isOver);

        List<Projectile> projectilesToRemove = new LinkedList<>();
        projectiles.forEach(projectile -> {
            enemies.stream()
                    .filter(enemy -> enemy.intersects(projectile))
                    .forEach(enemy -> {
                        int damage = projectile.getDamage();
                        enemy.removeHp(damage);
                        addBlood(damage, projectile.getX(), projectile.getY());
                        //fixme:: no hardcode pls
                        if (!projectile.getWeapon().getType().equals(Weapon.Type.Sniper))
                            projectilesToRemove.add(projectile);
                    });
        });
        projectiles.removeAll(projectilesToRemove);
    }

    private void checkBonuses() {

        bonuses.removeIf(Bonus::isOver);

        bonuses.stream()
                .filter(bonus -> bonus.intersects(hero))
                //fixme: shouldn't give a hero to a bonus
                .forEach(projectile -> projectile.getBonus(hero));
    }

    private void addBlood(int amount, float x, float y) {
        bloodDrops.addAll(
                IntStream.range(0, amount)
                        .mapToObj(n -> new BloodDrop(x, y, this))
                        .collect(Collectors.toList()));
    }

    public void removeBlood(BloodDrop bloodDrop) {
        bloodDrops.removeIf(b -> b.equals(bloodDrop));
    }

    public void addProjectile(Projectile projectile) {
        projectiles.add(projectile);
    }

    public void removeProjectile(Projectile projectile) {
        projectiles.removeIf(p -> p.equals(projectile));
    }

    public void addEnemy(Enemy enemy) {
        enemies.add(enemy);
    }

    public void addBonus(Bonus bonus) {
        bonuses.add(bonus);
    }


//
//    private final LinkedList<GameObject> objects = new LinkedList<>();
//
//
//
//    public void tick() {
//        for (GameObject tempObject : objects) {
//            tempObject.tick();
//        }
//    }
//
//    public void render(Graphics graphics) {
//        for (int i = 0; i < objects.size(); i++) {
//            GameObject tempObject = objects.get(i);
//            tempObject.render(graphics);
//        }
//    }
//
//    public void addObject(GameObject object) {
//
//        this.objects.add(object);
//    }
//
//    public void removeObject(GameObject object) {
//        this.objects.remove(object);
//    }
}
