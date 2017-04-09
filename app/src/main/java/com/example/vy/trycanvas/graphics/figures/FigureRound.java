package com.example.vy.trycanvas.graphics.figures;

import android.graphics.Bitmap;
import com.example.vy.trycanvas.graphics.pixels.Point;

/**
* Класс для формирования фигуры круг
 * x0, y0 - координаты центра окружности
 * R - радиус окружности
* */
public class FigureRound extends Figure {

    private int x0,y0,R;
    public FigureRound(int x0, int y0, int R){
        this.x0 = x0;
        this.y0 = y0;
        this.R = R;
    }


    public Figure BrazAlgCircle(Bitmap bitmap){
        pixels.clear();
        int x = 0;
        int y = R;
        int delta = 1 - 2 * R;
        int error = 0;
        while(y >= 0){
            pixels.add(new Point(x0 + x,y0 + y));
            pixels.add(new Point(x0 + x,y0 - y));
            pixels.add(new Point(x0 - x,y0 + y));
            pixels.add(new Point(x0 - x,y0 - y));

            bitmap.setPixel(x0 + x,y0 + y,color);
            bitmap.setPixel(x0 + x,y0 - y,color);
            bitmap.setPixel(x0 - x,y0 + y,color);
            bitmap.setPixel(x0 - x,y0 - y,color);

            error = 2 * (delta + y) - 1;
            if((delta < 0) && (error <= 0)){
                delta += 2 * ++x + 1;
                continue;
            }
            error = 2 * (delta - x) - 1;
            if((delta > 0) && (error > 0)){
                delta +=  1 - 2 * --y;
                continue;
            }
            x++;
            delta += 2 * (x - y);
            y--;
        }
        return this;
    }

    public Figure ParamAlgCircle(Bitmap bitmap){
        pixels.clear();

        int y = 0;
        double fors = R / Math.sqrt(2);
        for(int x = 0; x < fors; x++){
            y = (int)Math.sqrt(R*R - x*x);
            pixels.add(new Point(x0+x,y0+y));
            pixels.add(new Point(x0+y,y0+x));
            pixels.add(new Point(x0+y,y0-x));
            pixels.add(new Point(x0+x,y0-y));
            pixels.add(new Point(x0-x,y0-y));
            pixels.add(new Point(x0-y,y0-x));
            pixels.add(new Point(x0-y,y0+x));
            pixels.add(new Point(x0-x,y0+y));

            bitmap.setPixel(x0+x,y0+y,color);
            bitmap.setPixel(x0+y,y0+x,color);
            bitmap.setPixel(x0+y,y0-x,color);
            bitmap.setPixel(x0+x,y0-y,color);
            bitmap.setPixel(x0-x,y0-y,color);
            bitmap.setPixel(x0-y,y0-x,color);
            bitmap.setPixel(x0-y,y0+x,color);
            bitmap.setPixel(x0-x,y0+y,color);
        }
        return this;
    }

}
