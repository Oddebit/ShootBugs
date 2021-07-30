package com.od.game.page.objects;

import com.od.game.util.GeomUtil;
import lombok.Getter;
import lombok.Setter;

import java.awt.Color;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class Menu extends Container<Button> {

    private Button selectedButton;

    public Menu(double w, double h) {
        super(0, 0, w, h);
    }

    public void setContained(double w, double h, double padding, double margin, Color backgroundColor, Color outlineColor, Color fontColor) {
        super.setContained(w, h, padding, margin, backgroundColor, outlineColor);
        getContained().forEach(button -> button.setFontColor(fontColor));
    }

    public void setHovered(double w, double h, double padding, double margin, Color backgroundColor, Color outlineColor, Color fontColor) {
        getContained().forEach(button -> {
            Button hovered = new Button(button.getText() + " (hovered)");
            hovered.setDimension(w, h);
            hovered.setPadding(padding);
            hovered.setMargin(margin);
            hovered.setBackGroundColor(backgroundColor);
            hovered.setOutlineColor(outlineColor);
            hovered.setFontColor(fontColor);
            button.setHovered(hovered);
        });
        repositionSubdivs();
    }

    public void setSelected(double w, double h, double padding, double margin, Color backgroundColor, Color outlineColor, Color fontColor) {
        getContained().forEach(button -> {
            Button selected = new Button(button.getText() + " (selected)");
            selected.setDimension(w, h);
            selected.setPadding(padding);
            selected.setMargin(margin);
            selected.setBackGroundColor(backgroundColor);
            selected.setOutlineColor(outlineColor);
            selected.setFontColor(fontColor);
            button.setHovered(selected);
        });
        repositionSubdivs();
    }

    public void addButtons(String... names) {
        LinkedList<Button> buttons = new LinkedList<>();
        Arrays.stream(names).iterator().forEachRemaining(name -> buttons.add(new Button(name)));
        super.addSubdivs(buttons);
    }

    @Override
    public void addSubdiv(Button subdiv) {
        super.addSubdiv(subdiv);
        selectFirstButton();
    }

    @Override
    public void addSubdivs(List<Button> subdivs) {
        super.addSubdivs(subdivs);
        selectFirstButton();
    }

    private void selectFirstButton() {
        selectedButton = getContained().getFirst();
    }

    public void select(int index) {
        if(index < getContained().size()) selectedButton = getContained().get(index);
    }

    public void selectNext(int increment) {
        select((int) GeomUtil.loop(getContained().indexOf(selectedButton) + increment, 0, getContained().size(), 1));
    }
}
