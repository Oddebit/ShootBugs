package com.od.game.page.objects;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class Button extends TextDiv{

    private boolean isHovered = false;
    private Button hovered;

    private boolean isSelected = false;
    private Button selected;

    public Button(String text) {
        super(text);
    }

    @Override
    public void render(Graphics2D graphics) {
        if(isHovered && hovered != null)
            hovered.render(graphics);

        else if(isSelected && selected != null)
            selected.render(graphics);

        else
            super.render(graphics);
    }

    public void setHovered(Button hovered) {
        this.hovered = hovered;
    }

    public void setSelected(Button selected) {
        this.selected = selected;
    }
}
