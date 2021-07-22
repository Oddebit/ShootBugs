package com.od.game.handlers;

import com.od.game.Game;
import com.od.game.handlers.losshandler.LossGeneralHandler;
import com.od.game.handlers.pausehandler.PauseGeneralHandler;
import com.od.game.handlers.playhandler.PlayGeneralHandler;
import com.od.game.handlers.winhandler.WinGeneralHandler;
import com.od.input.Input;
import com.od.input.mouse.MouseInput;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
public class StatesHandler {

    private Game game;
    private GeneralHandler currentHandler;

    private PlayGeneralHandler playGeneralHandler;
    private PauseGeneralHandler pauseGeneralHandler;
    private WinGeneralHandler winGeneralHandler;
    private LossGeneralHandler lossGeneralHandler;

    private Map<GameState, GeneralHandler> handlers = new HashMap<>();
    private Map<GeneralHandler, Input<?>> inputs = new HashMap<>();

    public StatesHandler(Game game) {

        this.game = game;

        playGeneralHandler = new PlayGeneralHandler();
        pauseGeneralHandler = new PauseGeneralHandler();
        winGeneralHandler = new WinGeneralHandler();
        lossGeneralHandler = new LossGeneralHandler();

        handlers.put(GameState.PLAY, playGeneralHandler);
        handlers.put(GameState.PAUSE, pauseGeneralHandler);
        handlers.put(GameState.WIN, winGeneralHandler);
        handlers.put(GameState.LOSS, lossGeneralHandler);

        inputs.put(playGeneralHandler, new Input<PlayGeneralHandler>());
        inputs.put(pauseGeneralHandler, new Input<PauseGeneralHandler>());
        inputs.put(winGeneralHandler, new Input<WinGeneralHandler>());
        inputs.put(lossGeneralHandler, new Input<LossGeneralHandler>());

        currentHandler = playGeneralHandler;
    }

    public void tick() {

        currentHandler.tick();

        GameState wantedState = currentHandler.getWantedState();
        GeneralHandler wantedHandler = handlers.get(wantedState);

        if (wantedHandler.equals(currentHandler)) return;

        if (shouldReplay(wantedState)) {
            this.playGeneralHandler = new PlayGeneralHandler();
            setCurrentHandler(playGeneralHandler);
        }

        setCurrentHandler(wantedHandler);
    }

    public void render(Graphics2D graphics) {
        currentHandler.render(graphics);
    }

    private boolean shouldReplay(GameState wantedState) {
        if (currentHandler == winGeneralHandler || currentHandler == lossGeneralHandler) {
            return wantedState == GameState.PLAY;
        } else return false;
    }

    private void setCurrentHandler(GeneralHandler wantedHandler) {

        MouseInput<? extends GeneralHandler> currentMouseInput = inputs.get(currentHandler).getMouseInput();

        game.removeKeyListener(inputs.get(currentHandler).getKeyInput());
        game.removeMouseListener(currentMouseInput);
        game.removeMouseWheelListener(currentMouseInput);
        game.removeMouseMotionListener(currentMouseInput);

        MouseInput<? extends GeneralHandler> wantedMouseInput = inputs.get(currentHandler).getMouseInput();

        game.addKeyListener(inputs.get(wantedHandler).getKeyInput());
        game.addMouseListener(wantedMouseInput);
        game.addMouseWheelListener(wantedMouseInput);
        game.addMouseMotionListener(wantedMouseInput);

    }

    public enum GameState {
        PLAY, PAUSE, LOSS, WIN;
    }
}
