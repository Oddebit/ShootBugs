package com.od.game;

import com.od.objects.GameObject;
import com.od.objects.Hero;
import com.od.objects.ID;
import com.od.objects.Projectile;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class MouseInput extends MouseAdapter {

    Handler handler;
    Hero hero;

    public MouseInput(Handler handler) {
        this.handler = handler;
        for (int i = 0; i < handler.objects.size(); i++) {
            GameObject tempObject = handler.objects.get(i);
            if (tempObject.getId() == ID.Hero) {
                hero = (Hero)tempObject;
                break;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent event) {
    }

    @Override
    public void mousePressed(MouseEvent event) {
        int mouseX = event.getX();
        int mouseY = event.getY();

        hero.getActiveWeapon().shoot(mouseX, mouseY);
    }

    @Override
    public void mouseReleased(MouseEvent event) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent event) {
        int scroll = event.getWheelRotation();
        hero.setActiveWeapon(scroll);
    }
}
