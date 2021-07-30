package com.od.game.states;

import com.od.game.Game;
import com.od.game.states.loss.LossDispatcher;
import com.od.game.states.loss.input.LossMouseInput;
import com.od.game.states.mainmenu.MainMenuDispatcher;
import com.od.game.states.mainmenu.input.MenuKeyInput;
import com.od.game.states.mainmenu.input.MenuMouseInput;
import com.od.game.states.pause.PauseDispatcher;
import com.od.game.states.pause.input.PauseMouseInput;
import com.od.game.states.play.PlayDispatcher;
import com.od.game.states.play.input.PlayMouseInput;
import com.od.game.states.win.WinDispatcher;
import com.od.game.states.win.input.WinMouseInput;
import com.od.input.Input;
import com.od.input.MouseInput;
import com.od.game.states.loss.input.LossKeyInput;
import com.od.game.states.pause.input.PauseKeyInput;
import com.od.game.states.play.input.PlayKeyInput;
import com.od.game.states.win.input.WinKeyInput;
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
    private MainMenuDispatcher mainMenuDispatcher;

    private Map<GameState, Dispatcher> dispatchers = new HashMap<>();
    private Map<Dispatcher, Input<?>> inputs = new HashMap<>();

    public StatesHandler(Game game) {

        this.game = game;

        playDispatcher = new PlayDispatcher();
        pauseDispatcher = new PauseDispatcher();
        winDispatcher = new WinDispatcher();
        lossDispatcher = new LossDispatcher();
        mainMenuDispatcher = new MainMenuDispatcher();

        dispatchers.put(GameState.PLAY, playDispatcher);
        dispatchers.put(GameState.PAUSE, pauseDispatcher);
        dispatchers.put(GameState.WIN, winDispatcher);
        dispatchers.put(GameState.LOSS, lossDispatcher);
        dispatchers.put(GameState.MENU, mainMenuDispatcher);

        inputs.put(playDispatcher, new Input<>(new PlayMouseInput(playDispatcher), new PlayKeyInput(playDispatcher)));
        inputs.put(pauseDispatcher, new Input<>(new PauseMouseInput(pauseDispatcher), new PauseKeyInput(pauseDispatcher)));
        inputs.put(winDispatcher, new Input<>(new WinMouseInput(winDispatcher), new WinKeyInput(winDispatcher)));
        inputs.put(lossDispatcher, new Input<>(new LossMouseInput(lossDispatcher), new LossKeyInput(lossDispatcher)));
        inputs.put(mainMenuDispatcher, new Input<>(new MenuMouseInput(mainMenuDispatcher), new MenuKeyInput(mainMenuDispatcher)));

        currentDispatcher = pauseDispatcher;
        setCurrentDispatcher(mainMenuDispatcher);
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
        WIN,
        MENU
    }
}
