package com.example.vy.kg.graphics.figures;

import com.example.vy.kg.graphics.pixels.MyPixelRect;


public class FigureCurve extends Figure {

    public void getBezierLine(int[] points, int size) {

        float step = 0.001F;
        float r[] = new float[points.length];
        float t = step;
        while (t <= 1) {
            for (int i = 0; i < r.length; i++) {
                r[i] = points[i];
            }
            for (int i = r.length - 2; i >= 0; i -= 2) {
                for (int j = 0; j < i; j++) {
                    r[j] += t * (r[j + 2] - r[j]);
                    j++;
                    r[j] += t * (r[j + 2] - r[j]);
                }
            }
            pixels.add(new MyPixelRect(size,(int)r[0],(int)r[1]));
            t += step;
        }
    }

}
