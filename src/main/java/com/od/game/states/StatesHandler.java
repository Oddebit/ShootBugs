package com.od.game.states;

import com.od.game.Game;
import com.od.game.states.loss.LossDispatcher;
import com.od.game.states.pause.PauseDispatcher;
import com.od.game.states.play.PlayDispatcher;
import com.od.game.states.win.WinDispatcher;
import com.od.input.Input;
import com.od.input.key.LossKeyInput;
import com.od.input.key.PauseKeyInput;
import com.od.input.key.PlayKeyInput;
import com.od.input.key.WinKeyInput;
import com.od.input.mouse.*;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class StatesHandler {

    private Game game;
    private Dispatcher currentDispatcher;

    private PlayDispatcher playDispatcher;
    private PauseDispatcher pauseDispatcher;
    private WinDispatcher winDispatcher;
    private LossDispatcher lossDispatcher;

    private Map<GameState, Dispatcher> dispatchers = new HashMap<>();
    private Map<Dispatcher, Input<?>> inputs = new HashMap<>();

    public StatesHandler(Game game) {

        this.game = game;

        playDispatcher = new PlayDispatcher();
        pauseDispatcher = new PauseDispatcher();
        winDispatcher = new WinDispatcher();
        lossDispatcher = new LossDispatcher();

        dispatchers.put(GameState.PLAY, playDispatcher);
        dispatchers.put(GameState.PAUSE, pauseDispatcher);
        dispatchers.put(GameState.WIN, winDispatcher);
        dispatchers.put(GameState.LOSS, lossDispatcher);

        inputs.put(playDispatcher, new Input<>(new PlayMouseInput(playDispatcher), new PlayKeyInput(playDispatcher)));
        inputs.put(pauseDispatcher, new Input<>(new PauseMouseInput(pauseDispatcher), new PauseKeyInput(pauseDispatcher)));
        inputs.put(winDispatcher, new Input<>(new WinMouseInput(winDispatcher), new WinKeyInput(winDispatcher)));
        inputs.put(lossDispatcher, new Input<>(new LossMouseInput(lossDispatcher), new LossKeyInput(lossDispatcher)));

        currentDispatcher = pauseDispatcher;
        setCurrentDispatcher(winDispatcher);
    }

    public void tick() {

        currentDispatcher.tick();

        GameState wantedState = currentDispatcher.getWantedState();
        Dispatcher wantedDispatcher = dispatchers.get(wantedState);

        if (wantedDispatcher.equals(currentDispatcher)) return;

        if (shouldReplay(wantedState)) {
            this.playDispatcher = new PlayDispatcher();
        }

        setCurrentDispatcher(wantedDispatcher);
    }

    public void render(Graphics2D graphics) {
        currentDispatcher.render(graphics);
    }

    private boolean shouldReplay(GameState wantedState) {
        if (currentDispatcher == winDispatcher || currentDispatcher == lossDispatcher) {
            return wantedState == GameState.PLAY;
        } else return false;
    }

    private void setCurrentDispatcher(Dispatcher wantedDispatcher) {

        currentDispatcher.resetWantedState();

        game.removeKeyListener(inputs.get(currentDispatcher).getKeyInput());
        MouseInput<? extends Dispatcher> currentMouseInput = inputs.get(currentDispatcher).getMouseInput();
        game.removeMouseListener(currentMouseInput);
        game.removeMouseWheelListener(currentMouseInput);
        game.removeMouseMotionListener(currentMouseInput);

        game.addKeyListener(inputs.get(wantedDispatcher).getKeyInput());
        MouseInput<? extends Dispatcher> wantedMouseInput = inputs.get(wantedDispatcher).getMouseInput();
        game.addMouseListener(wantedMouseInput);
        game.addMouseWheelListener(wantedMouseInput);
        game.addMouseMotionListener(wantedMouseInput);

        currentDispatcher = wantedDispatcher;
    }

    public enum GameState {
        PLAY,
        PAUSE,
        LOSS,
        WIN
    }
}
