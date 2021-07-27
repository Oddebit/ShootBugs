package com.od.game.states.menu;

import com.od.game.states.Dispatcher;
import com.od.game.states.StatesHandler;

import java.awt.*;

public class MenuDispatcher extends Dispatcher {
    private final ButtonHandler buttonHandler;
    
    public MenuDispatcher() {
        super(StatesHandler.GameState.MENU);
        this.buttonHandler = new ButtonHandler();
    }

    @Override
    public void tick() {
        
    }

    @Override
    public void render(Graphics2D graphics) {
        buttonHandler.render(graphics);
    }


    public void up() {
        buttonHandler.selectNext(-1);
    }

    public void down() {
        buttonHandler.selectNext(+1);
    }

    public void enter() {
        Button selectedButton = buttonHandler.getSelectedButton();
        if(selectedButton == buttonHandler.getPlay()) setWantedState(StatesHandler.GameState.PLAY);
        if(selectedButton == buttonHandler.getExit()) System.exit(0);
    }
}
