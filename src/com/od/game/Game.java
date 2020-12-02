package com.od.game;

import com.od.objects.Enemy;
import com.od.objects.Hero;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 1550691097823471818L;

    private ObjectHandler oHandler;
    private SurroundingsHandler sHandler;
    private Thread thread;
    private boolean running = false;
    Random random = new Random();

    public static final int WIDTH = 1306, HEIGHT = 708;
    public static final int REAL_WIDTH = WIDTH - 16, REAL_HEIGHT = HEIGHT - 39;
    public static final int WIDTH_CENTER = REAL_WIDTH / 2, HEIGHT_CENTER = REAL_HEIGHT / 2;

    public Game() {
        sHandler = new SurroundingsHandler();
        oHandler = new ObjectHandler();
        oHandler.addObject(new Hero(oHandler));
        this.addKeyListener(new KeyInput(oHandler));
        this.addMouseListener(new MouseInput(oHandler));
        new Window(WIDTH, HEIGHT, "Shoot Bad Guys", this);

        for (int i = 0; i < 20; i++) {
            int enemyX = random.nextInt(REAL_WIDTH + (int)Enemy.getDiameter());
            int enemyY = random.nextInt(REAL_HEIGHT + (int)Enemy.getDiameter());
            int proba = random.nextInt(4);

            switch (proba) {
                case 0:
                    enemyY = 0;
                    break;
                case 1:
                    enemyX = 0;
                    break;
                case 2:
                    enemyY = REAL_HEIGHT - (int)Enemy.getDiameter();
                    break;
                case 3:
                    enemyX = REAL_WIDTH - (int)Enemy.getDiameter();
                    break;
            }
            oHandler.addObject(new Enemy(enemyX, enemyY, oHandler, sHandler));
        }

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
        double amountOfTicks = 60;
        double ns = 1_000_000_000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
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
        sHandler.tick();
        oHandler.tick();
    }

    private void render() {
        BufferStrategy bufferStrategy = this.getBufferStrategy();
        if (bufferStrategy == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics graphics = bufferStrategy.getDrawGraphics();

        graphics.setColor(Color.black);
        graphics.fillRect(0, 0, WIDTH, HEIGHT);

        sHandler.render(graphics);
        oHandler.render(graphics);

        graphics.dispose();
        bufferStrategy.show();
    }

    public static float clamp(float var, float min, float max) {
        if(var >= max)
            return max;
        else if(var <= min)
            return min;
        else
            return var;
    }

    public static void main(String[] args) {
        new Game();
    }
}

