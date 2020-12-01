package com.od.game;

import com.od.objects.Enemy;
import com.od.objects.Hero;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 1550691097823471818L;

    private Handler handler;
    private Thread thread;
    private boolean running = false;
    Random random = new Random();

    public static final int WIDTH = 1306, HEIGHT = 708;
    public static final int REAL_WIDTH = WIDTH - 16, REAL_HEIGHT = HEIGHT - 39;
    public static final int WIDTH_CENTER = REAL_WIDTH / 2, HEIGHT_CENTER = REAL_HEIGHT / 2;
    public static final double BORDER_PROPORTION = 0.03;
    public static final int WIDTH_BORDER = (int) (REAL_WIDTH * BORDER_PROPORTION), HEIGHT_BORDER = (int) (REAL_HEIGHT * BORDER_PROPORTION);
    public static final int BORDER_WIDTH = REAL_WIDTH - 2 * WIDTH_BORDER, BORDER_HEIGHT = REAL_HEIGHT - 2 * HEIGHT_BORDER;
    public static final int GRAPH_HEIGHT = 50;


    public Game() {
        handler = new Handler();
        handler.addObject(new Hero(handler));
        this.addKeyListener(new KeyInput(handler));
        this.addMouseListener(new MouseInput(handler));
        new Window(WIDTH, HEIGHT, "Shoot Bad Guys", this);

        for (int i = 0; i < 20; i++) {
            int enemyX = random.nextInt(REAL_WIDTH - (int)Enemy.getDiameter());
            int enemyY = random.nextInt(REAL_HEIGHT - (int)Enemy.getDiameter());
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
            handler.addObject(new Enemy(enemyX, enemyY, handler));
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
        handler.tick();
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

        handler.render(graphics);

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

