package com.od.game.states.play;

import com.od.game.data.ColorData;
import com.od.game.data.DimensionData;
import com.od.game.data.FontData;
import com.od.game.states.Dispatcher;
import com.od.game.states.StatesHandler;
import com.od.game.states.play.objects.GameObject;
import com.od.game.states.play.objects.bonus.Bonus;
import com.od.game.states.play.objects.bonus.WeaponBonus;
import com.od.game.states.play.objects.creatures.enemies.Enemy;
import com.od.game.states.play.objects.creatures.hero.Hero;
import com.od.game.states.play.objects.handlers.*;
import com.od.game.states.play.objects.projectiles.Projectile;
import com.od.game.states.play.objects.weapons.Weapon;
import com.od.game.states.play.threads.LevelThread;
import com.od.game.util.GeomUtil;
import lombok.Getter;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

@Getter
public class PlayDispatcher extends Dispatcher {

    private int level;
    private final LevelThread levelThread;

    private final HeroHandler heroHandler;
    private final BloodDropsHandler bloodDropsHandler;
    private final BonusesHandler bonusesHandler;
    private final EnemiesHandler enemiesHandler;
    private final ProjectilesHandler projectilesHandler;
    private final WeaponsHandler weaponsHandler;

    private final LinkedList<PlayHandler<? extends GameObject>> playHandlers = new LinkedList<>();

    public PlayDispatcher() {

        super(StatesHandler.GameState.PLAY);

        this.level = 0;
        this.levelThread = new LevelThread(30_000);
        levelThread.start();

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
        levelThread.tick();
        tickHandlers();
        check();
    }

    public void tickHandlers() {
        playHandlers.forEach(PlayHandler::tick);
    }

    public void check() {
        checkSpawn();

        checkBonuses();
        checkBloodDrops();
        checkWeapons();
        checkEnemies();
        checkProjectiles();

        checkCollisions();

        checkLoss();
        checkWin();
    }

    private void checkLoss() {
        if (heroHandler.heroIsDead()) setWantedState(StatesHandler.GameState.LOSS);
    }

    private void checkWin() {
        if(enemiesHandler.getKillCount() >= 100) setWantedState(StatesHandler.GameState.WIN);
    }

    private void checkSpawn() {
        if (levelThread.isFinished()) {
            level++;
            levelThread.start();
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
        projectiles
                .forEach(projectile ->
                enemies.stream()
                        .filter(projectile::intersects)
                        .forEach(enemy ->  {
                            removeHp(enemy, projectile.getDamage());

                            //fixme:: no hardcode -> projectile.hp or enum Projectile.IntersectionEffect ?
                            Weapon.WeaponType weaponType = projectile.getWeapon().getWeaponType();
                            if (!weaponType.equals(Weapon.WeaponType.SNIPER) && !weaponType.equals(Weapon.WeaponType.GRENADE))
                                projectilesToRemove.add(projectile);
                        })
        );
        projectiles.removeAll(projectilesToRemove);
    }

    private void removeHp(Enemy enemy, int damage) {
        damage = Math.min(damage, enemy.getHp());
        enemy.removeHp(damage);
        bloodDropsHandler.addBlood(damage, enemy.getPosition());
    }

    private void checkHeroEnemiesCollisions() {

        if (!heroHandler.heroIsUntouchable())
            enemiesHandler.getHandled().stream()
                .filter(enemy -> enemy.intersects(heroHandler.getHero()))
                .forEach(enemy -> heroHandler.heroRemoveHp(enemy.getHp()));
    }

    private void checkHeroBonusCollisions() {

        if (bonusesHandler.getBonus().isPresent()) {
            Hero hero = heroHandler.getHero();
            Bonus bonus = bonusesHandler.getBonus().get();

            if (bonus.intersects(hero)) {
                if (bonus.getType() == Bonus.BonusType.HEALTH)
                    hero.resetHP();
                else
                    weaponsHandler.refillWeapon(((WeaponBonus) bonus).getWeaponType());
                bonusesHandler.clear();
            }
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
                .forEach(enemy -> enemy.setVelocity(GeomUtil.getVector(enemy.getPosition(), hero.getPosition(), enemy.getSpeed())));
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
            Weapon.WeaponType weaponType = weapon.getWeaponType();

            Point2D position = heroHandler.heroGetPosition();
            Point2D target = weaponsHandler.activeWeaponGetTarget();

            if (weaponType == Weapon.WeaponType.SHOTGUN)
                projectilesHandler.createShotgunProjectiles(weapon, position, target);
            else if (weaponType == Weapon.WeaponType.GRENADE)
                projectilesHandler.createGrenadeProjectiles(weapon, position, target);
            else
                projectilesHandler.createProjectile(weapon, position, target);

            weaponsHandler.activeWeaponShoot();
        }
    }

    public void checkProjectiles() {
        projectilesHandler.removeOverProjectiles();
    }

    public void render(Graphics2D graphics) {
        graphics.setFont(FontData.ENORMOUS);
        graphics.setColor(ColorData.LEVEL_GREY);

        String level = String.valueOf(this.level);
        int x = - graphics.getFontMetrics().stringWidth(level) / 3;
        int y = DimensionData.REAL_HEIGHT;

        graphics.drawString(level, x, y);
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
}
