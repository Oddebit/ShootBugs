package com.od.game.states.mainmenu;

import com.od.game.states.Dispatcher;
import com.od.game.states.StatesHandler;

import java.awt.*;

public class MainMenuDispatcher extends Dispatcher {
    private final MainMenuHandler mainMenuHandler;
    
    public MainMenuDispatcher() {
        super(StatesHandler.GameState.MENU);
        this.mainMenuHandler = new MainMenuHandler();
    }

    @Override
    public void tick() {
        
    }

    @Override
    public void render(Graphics2D graphics) {
        mainMenuHandler.render(graphics);
    }


    public void up() {
        mainMenuHandler.selectNext(-1);
    }

    public void down() {
        mainMenuHandler.selectNext(+1);
    }

    public void enter() {
        String selectedButtonText = mainMenuHandler.getSelectedButtonText();
        if(selectedButtonText.equals("Play")) setWantedState(StatesHandler.GameState.PLAY);
        if(selectedButtonText.equals("Exit")) System.exit(0);
    }
}
