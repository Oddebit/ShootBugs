package com.od.game;

import com.od.game.data.ColorData;
import com.od.game.handlers.GeneralHandler;
import com.od.game.objects.DashBoard;
import com.od.input.InputHandler;
import com.od.output.SoundPlayer;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.awt.image.BufferStrategy;

@Getter
@Setter
public class Game extends Canvas implements Runnable {

    private static final long serialVersionUID = 1550691097823471818L;

    private GeneralHandler generalHandler;
    private DashBoard dashBoard;

    private Thread thread;
    private boolean running = false;
    State state;

    public static final int WIDTH = 708, HEIGHT = 708;
    public static final int REAL_WIDTH = WIDTH - 16, REAL_HEIGHT = HEIGHT - 39;
    public static final int WIDTH_CENTER = REAL_WIDTH / 2, HEIGHT_CENTER = REAL_HEIGHT / 2;

    public Game() {
        state = State.Play;
        SoundPlayer.playMusic("sounds/battlefieldTheme.wav");

//        objectHandler =
        generalHandler = new GeneralHandler();

        dashBoard = new DashBoard(generalHandler);

        new InputHandler(this, generalHandler);
        new Window(WIDTH, HEIGHT, "Shoot Bugs", this);
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
        if (state == State.Play) {
            generalHandler.tick();
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

        if (state == State.Play) {
            generalHandler.render(graphics);
            dashBoard.render(graphics);
        } else if (state == State.GameOver) {
            graphics.setColor(Color.RED);
            graphics.setFont(new Font(Font.DIALOG, Font.BOLD, 75));
            String str = "GAME OVER";
            int height = graphics.getFontMetrics().getHeight();
            int width = graphics.getFontMetrics().stringWidth(str);
            graphics.drawString(str, (int) (WIDTH_CENTER - width / 2d), (int) (HEIGHT_CENTER - height / 2d));

            graphics.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
            String kills = String.format("You killed %d zombies.", dashBoard.getKillCount());
            int width2 = graphics.getFontMetrics().stringWidth(kills);
            graphics.drawString(kills, (int) (WIDTH_CENTER - width2 / 2d), (int) (HEIGHT_CENTER));
        } else if (state == State.Win){
            graphics.setColor(ColorData.GREEN);
            graphics.setFont(new Font(Font.DIALOG, Font.BOLD, 75));
            String str = "YOU WON";
            int height = graphics.getFontMetrics().getHeight();
            int width = graphics.getFontMetrics().stringWidth(str);
            graphics.drawString(str, (int) (WIDTH_CENTER - width / 2d), (int) (HEIGHT_CENTER - height / 2d));

            graphics.setFont(new Font(Font.DIALOG, 1, 30));
            String kills = String.format("You killed %d zombies.", dashBoard.getKillCount());
            int width2 = graphics.getFontMetrics().stringWidth(kills);
            graphics.drawString(kills, (int) (WIDTH_CENTER - width2 / 2d), HEIGHT_CENTER);
        } else {
            graphics.setColor(ColorData.HERO_ORANGE);
            graphics.setFont(new Font(Font.DIALOG, Font.BOLD, 75));
            String str = "PAUSE";
            int height = graphics.getFontMetrics().getHeight();
            int width = graphics.getFontMetrics().stringWidth(str);
            graphics.drawString(str, (int) (WIDTH_CENTER - width / 2d), (int) (HEIGHT_CENTER - height / 2d));
        }

        graphics.dispose();
        bufferStrategy.show();
    }

    public void setState(State state) {
        this.state = state;
    }

    public void changeState() {
        if (state == State.Play) {
            this.state = State.Pause;
        } else if (state == State.Pause) {
            this.state = State.Play;
        }
    }



    public static void main(String[] args) {
        new Game();
    }

    public enum State {
        Play, Pause, GameOver, Win
    }

}

