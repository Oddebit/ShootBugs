package com.od.states;

import com.od.game.objects.GameObject;

import java.awt.*;

public class PlayState extends State{
    @Override
    public void tick(GameObject... gameObjects) {
        for (GameObject gameObject : gameObjects) {
            gameObject.tick();
        }
    }

    @Override
    public void render(Graphics graphics, GameObject... gameObjects) {
        for (GameObject gameObject : gameObjects) {
            gameObject.render(graphics);
        }
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics graphics) {

    }
}
