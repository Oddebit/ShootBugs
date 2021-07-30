package com.od.game.util;


import com.od.game.page.objects.Div;

import java.awt.geom.Point2D;
import java.util.Comparator;
import java.util.List;

public class PageUtil {

    public static Point2D getDimension(List<? extends Div> divs) {
        double height = divs.stream()
                .mapToDouble(subdiv -> subdiv.getPadded().getHeight())
                .sum();
        double width = divs.stream()
                .max(Comparator.comparingDouble(d -> d.getPadded().getWidth()))
                .orElseThrow().getW();

        return new Point2D.Double(width, height);
    }
}
