package com.od.game.data;

import java.awt.*;

public enum FontData {

    NORMAL(Font.DIALOG, Font.PLAIN, 12),
    BOLD(Font.DIALOG, Font.BOLD, 24);

    Font font;

    FontData(String name, int style, int size) {
        this.font = new Font(name, style, size);
    }

    public Font getFont() {
        return font;
    }
}
