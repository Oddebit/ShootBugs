package com.od.game.data;

import com.od.game.states.play.objects.GameObject;
import com.od.game.states.play.objects.creatures.hero.Hero;
import com.od.game.states.play.objects.weapons.*;

import java.util.Arrays;
import java.util.Optional;

public enum SoundData {
    PISTOL_SHOOT(Pistol.class, Action.SHOOT, "sounds/pistolShoot.wav"),
    RIFLE_SHOOT(Rifle.class, Action.SHOOT, "sounds/rifleShoot.wav"),
    SHOTGUN_SHOOT(Shotgun.class, Action.SHOOT, "sounds/shotgunShoot.wav"),
    SNIPER_SHOOT(Sniper.class, Action.SHOOT, "sounds/sniperShoot.wav"),
    AIRSTRIKE_SHOOT(AirStrike.class, Action.SHOOT, "sounds/airstrikeShoot.wav"),

    PISTOL_RELOAD(Pistol.class, Action.RELOAD, "sounds/pistolReload.wav"),
    RIFLE_RELOAD(Rifle.class, Action.RELOAD, "sounds/rifleReload.wav"),
    SHOTGUN_RELOAD(Shotgun.class, Action.RELOAD, "sounds/shotgunInsertBullet.wav"),
    SNIPER_RELOAD(Sniper.class, Action.RELOAD, "sounds/pistolReload.wav"),

    HERO_DIE(Hero.class, Action.DIE, "sounds/manYawn.wav");

    Class<? extends GameObject> objectClass;
    Action action;
    String soundLocation;

    SoundData(Class<? extends GameObject> objectClass, Action action, String soundLocation) {
        this.objectClass = objectClass;
        this.action = action;
        this.soundLocation = soundLocation;
    }

    public String getSoundLocation() {
        return soundLocation;
    }

    public static Optional<String> getSoundLocation(Class<? extends GameObject> objectClass, Action action) {
        return Arrays.stream(SoundData.values())
                .filter(weaponSoundData -> weaponSoundData.action.equals(action))
                .filter(weaponSoundData -> weaponSoundData.objectClass.equals(objectClass))
                .findAny()
                .map(SoundData::getSoundLocation);
    }

    public enum Action {
        SHOOT, RELOAD, DIE
    }
}
