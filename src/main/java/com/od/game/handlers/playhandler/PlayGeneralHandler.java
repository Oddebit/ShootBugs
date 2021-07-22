package com.od.game.handlers.playhandler;

import com.od.game.handlers.GeneralHandler;
import com.od.game.handlers.StatesHandler;
import com.od.game.objects.GameObject;
import com.od.game.objects.bonus.Bonus;
import com.od.game.objects.bonus.WeaponBonus;
import com.od.game.objects.creatures.enemies.Enemy;
import com.od.game.objects.creatures.hero.Hero;
import com.od.game.objects.projectiles.Projectile;
import com.od.game.objects.weapons.Weapon;
import com.od.game.util.GeomUtil;
import lombok.Getter;

import java.awt.*;
import java.awt.geom.Point2D;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;

@Getter
public class PlayGeneralHandler extends GeneralHandler {

    private final Instant initTime;
    private int level;

    private final HeroHandler heroHandler;
    private final BloodDropsHandler bloodDropsHandler;
    private final BonusesHandler bonusesHandler;
    private final EnemiesHandler enemiesHandler;
    private final ProjectilesHandler projectilesHandler;
    private final WeaponsHandler weaponsHandler;

    private final LinkedList<PlayHandler<? extends GameObject>> playHandlers = new LinkedList<>();

    public PlayGeneralHandler() {

        setWantedState(StatesHandler.GameState.PLAY);

        this.initTime = Instant.now();
        this.level = 0;

        this.heroHandler = new HeroHandler();
        this.bloodDropsHandler = new BloodDropsHandler();
        this.bonusesHandler = new BonusesHandler();
        this.enemiesHandler = new EnemiesHandler();
        this.projectilesHandler = new ProjectilesHandler();
        this.weaponsHandler = new WeaponsHandler();

        playHandlers.add(bloodDropsHandler);
        playHandlers.add(heroHandler);
        playHandlers.add(bonusesHandler);
        playHandlers.add(enemiesHandler);
        playHandlers.add(projectilesHandler);
        playHandlers.add(weaponsHandler);
    }

    public void tick() {
        tickHandlers();
        check();
    }

    public void tickHandlers() {
        playHandlers.forEach(PlayHandler::tick);
    }

    public void check() {
        checkSpawn();
        checkHero();
        checkBonuses();
        checkBloodDrops();
        checkWeapons();
        checkEnemies();
        checkProjectiles();
        checkCollisions();
    }

    private void checkHero() {
        if (heroHandler.heroIsDead()) setWantedState(StatesHandler.GameState.LOSS);
    }

    private void checkSpawn() {
        long timeInGame = ChronoUnit.MILLIS.between(initTime, Instant.now());

        if (timeInGame % 60_000 == 0) {
            level++;
            System.out.println(level);
        }

        enemiesHandler.askSpawn(level);
        bonusesHandler.askSpawn();
    }

    private void checkBonuses() {
        bonusesHandler.checkOver();
    }

    private void checkBloodDrops() {
        bloodDropsHandler.checkOver();
    }


    private void checkCollisions() {
        checkProjectilesEnemiesCollisions();
        checkHeroEnemiesCollisions();
        checkHeroBonusCollisions();
    }

    private void checkProjectilesEnemiesCollisions() {
        List<Projectile> projectiles = projectilesHandler.getHandled();
        List<Enemy> enemies = enemiesHandler.getHandled();

        List<Projectile> projectilesToRemove = new LinkedList<>();
        projectiles.forEach(projectile ->
                enemies.stream()

                        .filter(enemy -> enemy.intersects(projectile))
                        .forEach(enemy -> {

                            int damage = projectile.getDamage();
                            enemy.removeHp(damage);
                            bloodDropsHandler.addBlood(damage, projectile.getX(), projectile.getY());

                            //fixme:: no hardcode pls -> field in weapon > projectile : "hp"
                            if (!projectile.getWeapon().getWeaponType().equals(Weapon.WeaponType.SNIPER))
                                projectilesToRemove.add(projectile);
                        })
        );
        projectiles.removeAll(projectilesToRemove);
    }

    private void checkHeroEnemiesCollisions() {

        List<Enemy> enemies = enemiesHandler.getHandled();

        if (!heroHandler.heroIsUntouchable())
        enemies.stream()

                .filter(enemy -> enemy.intersects(heroHandler.getHero()))
                .forEach(enemy -> heroHandler.heroRemoveHp(enemy.getHp()));
    }

    private void checkHeroBonusCollisions() {
        Hero hero = heroHandler.getHero();
        Bonus bonus;

        if (bonusesHandler.getBonus().isPresent()) {
            bonus = bonusesHandler.getBonus().get();
        } else {
            return;
        }

        if (bonus.intersects(hero)) {
            if (bonus.getType() == Bonus.BonusType.HEALTH)
                hero.resetHP();
            else
                weaponsHandler.refillWeapon(((WeaponBonus) bonus).getWeaponType());
            bonusesHandler.clear();
        }
    }

    private void checkEnemies() {
        checkEnemiesDead();
        setEnemiesVelocity();
    }

    private void setEnemiesVelocity() {
        Hero hero = heroHandler.getHero();
        List<Enemy> enemies = enemiesHandler.getHandled();
        enemies.stream()

                .filter(enemy -> enemy.getType() == Enemy.EnemyType.BUG || enemy.getType() == Enemy.EnemyType.SPIDER)
                .forEach(enemy -> {

                    Point2D vector = GeomUtil.getVector(hero, enemy);
                    double dx = vector.getX();
                    double dy = vector.getY();

                    double distance = GeomUtil.getDistance(hero, enemy);
                    double speed = enemy.getSpeed();

                    enemy.getVelocity().setLocation(-speed * dx / distance, -speed * dy / distance);
                });
    }

    private void checkEnemiesDead() {
        enemiesHandler.checkDead();
    }

    private void checkWeapons() {
        checkWeaponTotalMunitionLeft();
        checkWeaponShot();
    }

    private void checkWeaponTotalMunitionLeft() {
        weaponsHandler.checkTotalMunitionLeft();
    }

    public void checkWeaponShot() {

        if (weaponsHandler.activeWeaponIsAskingToShoot()) {

            Weapon weapon = weaponsHandler.getActiveWeapon();
            Point2D position = heroHandler.heroGetPosition();
            Point2D target = weaponsHandler.activeWeaponGetTarget();

            Weapon.WeaponType weaponType = weapon.getWeaponType();
            if (weaponType == Weapon.WeaponType.SHOTGUN)
                projectilesHandler.createShotgunProjectiles(weapon, position, target);
            else
                projectilesHandler.createProjectile(weapon, position, target);

            weaponsHandler.activeWeaponShoot();
        }
    }

    public void checkProjectiles() {
        projectilesHandler.checkProjectiles();
    }

    public void render(Graphics2D graphics) {
        playHandlers.forEach(handler -> handler.render(graphics));
    }

    public void heroSetMovementX(int movement) {
        heroHandler.setMovementX(movement);
    }

    public void heroSetMovementY(int movement) {
        heroHandler.setMovementY(movement);
    }

    public void weaponAskInitReload() {
        weaponsHandler.activeWeaponAskInitReload();
    }

    public void weaponSetNextActiveWeapon(int increment) {
        weaponsHandler.setNextActiveWeapon(increment);
    }

    public void weaponAskInitShot() {
        weaponsHandler.activeWeaponAskInitShot();
    }

    public void weaponAskInitBurst() {
        weaponsHandler.activeWeaponAskInitBurst();
    }

    public void weaponStopBurst() {
        weaponsHandler.activeWeaponStopBurst();
    }

    public void weaponRetarget(double x, double y) {
        weaponsHandler.activeWeaponRetarget(x, y);
    }

    public void pause() {
        setWantedState(StatesHandler.GameState.PAUSE);
    }
}
