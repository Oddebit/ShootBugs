package com.od.game.page.objects;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class ContainerTest {

    @Test
    void test() {
        Menu menu = new Menu(100, 100);
        System.out.println(menu.getMargined());
        menu.addButtons("Hello");
        menu.setContained(50, 50, 0, 0, Color.BLACK, Color.BLACK);
        System.out.println(menu.getContainedFrame());
        System.out.println(menu.getContained().getFirst().getFrame());
        assertTrue(menu.canContain());
    }
}