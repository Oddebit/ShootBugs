package com.od.game;

import com.od.game.data.DimensionData;
import com.od.game.states.StatesHandler;
import com.od.output.SoundPlayer;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.image.BufferStrategy;

@Getter
@Setter
public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 1550691097823471818L;

    private StatesHandler handler;

    private Thread thread;
    private boolean running = false;

    public Game() {
        SoundPlayer.playMusic("sounds/battlefieldTheme.wav");

        handler = new StatesHandler(this);

//        new InputHandler(this, handler);
        new Window(DimensionData.WIDTH, DimensionData.HEIGHT, "Shoot Bugs", this);
    }

    public synchronized void start() {
        thread = new Thread(this);
        thread.start();
        running = true;
    }

    public synchronized void stop() {
        try {
            thread.join();
            running = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {

        long lastTime = System.nanoTime();
        double amountOfTicks = 100;
        double tickTime = 1_000_000_000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / tickTime;
            lastTime = now;

            while (delta >= 1) {

                tick();
                delta--;
            }

            if (running) {
                render();
            }
            frames++;

            // Affiche les FPS chaque seconde
            if (System.currentTimeMillis() - timer > 1_000) {
                timer += 1_000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick() {
        handler.tick();
    }

    private void render() {
        BufferStrategy bufferStrategy = this.getBufferStrategy();
        if (bufferStrategy == null) {
            this.createBufferStrategy(3);
            return;
        }
        Graphics2D graphics = (Graphics2D) bufferStrategy.getDrawGraphics();
        graphics.setColor(Color.black);
        graphics.fillRect(0, 0, DimensionData.WIDTH, DimensionData.HEIGHT);

        handler.render(graphics);

        graphics.dispose();
        bufferStrategy.show();
    }

    public static void main(String[] args) {
        new Game();
    }
}

