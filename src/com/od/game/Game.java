package com.od.game;

import com.od.objects.Hero;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 1550691097823471818L;

    private ObjectHandler oHandler;
    private SurroundingsHandler sHandler;
    private Thread thread;
    private boolean running = false;
    State state;

    public static final int WIDTH = 1306, HEIGHT = 708;
    public static final int REAL_WIDTH = WIDTH - 16, REAL_HEIGHT = HEIGHT - 39;
    public static final int WIDTH_CENTER = REAL_WIDTH / 2, HEIGHT_CENTER = REAL_HEIGHT / 2;

    public Game() {
        state = State.Play;
        sHandler = new SurroundingsHandler();
        oHandler = new ObjectHandler();
        oHandler.addObject(new Hero(oHandler, this));
        oHandler.addObject(new Spawner(oHandler, sHandler));
        this.addKeyListener(new KeyInput(oHandler));
        this.addMouseListener(new MouseInput(oHandler));
        new Window(WIDTH, HEIGHT, "Shoot Bad Guys", this);
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
        if(state == State.Play) {
            oHandler.tick();
        }
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
        if(state == State.Play) {
            oHandler.render(graphics);
        } else {
            graphics.setColor(Color.RED);
            graphics.setFont(new Font(Font.DIALOG, 1, 100));
            String str = "GAME OVER";
            int height = graphics.getFontMetrics().getHeight();
            int width = graphics.getFontMetrics().stringWidth(str);
            graphics.drawString(str, (int)(WIDTH_CENTER - width/2d), (int)(HEIGHT_CENTER - height/2d));
        }


        graphics.dispose();
        bufferStrategy.show();
    }

    public void setState(State state) {
        this.state = state;
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

    public enum State {
        Play, GameOver
    }
}

