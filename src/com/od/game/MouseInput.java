package com.od.game;

import com.od.objects.GameObject;
import com.od.objects.Hero;
import com.od.objects.ID;
import com.od.objects.Projectile;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        handler.addObject(new Projectile(hero.getX() + hero.getW()/2, hero.getY() + hero.getH()/2, mouseX, mouseY, handler));
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        handler.addObject(new Projectile(hero.getX() + hero.getW()/2, hero.getY() + hero.getH()/2, mouseX, mouseY, handler));
    }
}
