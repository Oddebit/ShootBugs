package com.od.game;

import com.od.objects.DashBoard;
import com.od.objects.Hero;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.File;

public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 1550691097823471818L;

    private ObjectHandler objectHandler;
    private SurroundingsHandler surroundingsHandler;
    private DashBoard dashBoard;

    private Hero hero;
    private Thread thread;
    private boolean running = false;
    State state;

    public static final int WIDTH = 708, HEIGHT = 708;
    public static final int REAL_WIDTH = WIDTH - 16, REAL_HEIGHT = HEIGHT - 39;
    public static final int WIDTH_CENTER = REAL_WIDTH / 2, HEIGHT_CENTER = REAL_HEIGHT / 2;

    public Game() {
        state = State.Play;
        playMusic("sounds/battlefieldTheme.wav");

        surroundingsHandler = new SurroundingsHandler();
        objectHandler = new ObjectHandler();

        hero = new Hero(objectHandler, surroundingsHandler, this);
        objectHandler.addObject(hero);

        dashBoard = new DashBoard(objectHandler);

        objectHandler.addObject(new Spawner(objectHandler, surroundingsHandler, dashBoard, this, hero));
//        objectHandler.addObject(new ToxicArea(objectHandler));
        this.addKeyListener(new KeyInput(objectHandler));
        this.addMouseListener(new MouseInput(objectHandler));
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
        surroundingsHandler.tick();
        if(state == State.Play) {
            objectHandler.tick();
            dashBoard.tick();
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

        surroundingsHandler.render(graphics);
        if(state == State.Play) {
            objectHandler.render(graphics);
            dashBoard.render(graphics);
        } else if (state == State.GameOver) {
            graphics.setColor(Color.RED);
            graphics.setFont(new Font(Font.DIALOG, Font.BOLD, 75));
            String str = "GAME OVER";
            int height = graphics.getFontMetrics().getHeight();
            int width = graphics.getFontMetrics().stringWidth(str);
            graphics.drawString(str, (int)(WIDTH_CENTER - width/2d), (int)(HEIGHT_CENTER - height/2d));

            graphics.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
            String kills = "You killed " + dashBoard.getKillCount() + " zombies.";
            int height2 = graphics.getFontMetrics().getHeight();
            int width2 = graphics.getFontMetrics().stringWidth(kills);
            graphics.drawString(kills, (int)(WIDTH_CENTER - width2/2d), (int)(HEIGHT_CENTER));
        } else {
            graphics.setColor(new Color(0, 255, 50));
            graphics.setFont(new Font(Font.DIALOG, Font.BOLD, 75));
            String str = "YOU WON";
            int height = graphics.getFontMetrics().getHeight();
            int width = graphics.getFontMetrics().stringWidth(str);
            graphics.drawString(str, (int)(WIDTH_CENTER - width/2d), (int)(HEIGHT_CENTER - height/2d));

            graphics.setFont(new Font(Font.DIALOG, 1, 30));
            String kills = "You killed " + dashBoard.getKillCount() + " zombies.";
            int height2 = graphics.getFontMetrics().getHeight();
            int width2 = graphics.getFontMetrics().stringWidth(kills);
            graphics.drawString(kills, (int)(WIDTH_CENTER - width2/2d), (int)(HEIGHT_CENTER));
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

    public static void playMusic(String musicLocation) {
        try {
            File musicPath = new File(musicLocation);
            if(musicPath.exists()) {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }else {
                System.out.println("File not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void playSound(String soundLocation) {
        try {
            File musicPath = new File(soundLocation);
            if(musicPath.exists()) {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicPath);
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
            }else {
                System.out.println("File not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Game();
    }

    public enum State {
        Play, GameOver, Win
    }
}

