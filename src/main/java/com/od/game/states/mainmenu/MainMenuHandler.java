package com.od.game.states.mainmenu;

import com.od.game.data.ColorData;
import com.od.game.data.DimensionData;
import com.od.game.page.objects.Menu;
import com.od.game.page.objects.Page;
import lombok.Getter;

import java.awt.*;

@Getter
public class MainMenuHandler {

    private final Page page;
    private final Menu menu;

    public MainMenuHandler() {

        page = new Page(DimensionData.REAL_WIDTH, DimensionData.REAL_HEIGHT);
//
//        TextDiv title = new TextDiv("MAIN MENU");
//        title.setDimension(500, 100);
//        title.setFont(FontData.TITLE);
//        title.setFontColor(ColorData.SHOOTBUGS_RED);
//        page.addSubdiv(title);

        menu = new Menu(500, 500);
        menu.addButtons("Play", "High Scores", "Settings", "Exit");
        menu.setContained(50, 5, 0, 0, ColorData.TRANSPARENT, ColorData.KILLCOUNT_TURQUOISE, ColorData.KILLCOUNT_TURQUOISE);
        menu.setHovered(50, 5, 0, 0, ColorData.TRANSPARENT, ColorData.HEALTH_TURQUOISE, ColorData.HEALTH_TURQUOISE);
        menu.setSelected(50, 5, 0, 0, ColorData.TRANSPARENT, ColorData.SHOOTBUGS_RED, ColorData.SHOOTBUGS_RED);
        page.addSubdiv(menu);
    }

    public void selectNext(int increment) {
        menu.selectNext(increment);
    }

    public void render(Graphics2D graphics) {
        page.render(graphics);
    }

    public String getSelectedButtonText() {
        return menu.getSelectedButton().getText();
    }
}
