package com.example.vy.kg.graphics.figures;

import com.example.vy.kg.graphics.pixels.MyPixelRect;

/**
 * Created by vy on 2/17/17.
 */

public class FigureRound extends Figure {

    private int x0,y0,R;
    public FigureRound(int x0, int y0, int R){
        this.x0 = x0;
        this.y0 = y0;
        this.R = R;
    }


    public void BrazAlgCircle(int size){
        int x = 0;
        int y = R;
        int delta = 1 - 2 * R;
        int error = 0;
        while(y >= 0){
            pixels.add(new MyPixelRect(size,x0 + x,y0 + y));
            pixels.add(new MyPixelRect(size,x0 + x,y0 - y));
            pixels.add(new MyPixelRect(size,x0 - x,y0 + y));
            pixels.add(new MyPixelRect(size,x0 - x,y0 - y));

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
    }

    public void ParamAlgCircle(int size){
        FigureLine line;

        int y = 0;
        double fors = R / Math.sqrt(2);
        for(int x = 0; x < fors; x++){
            y = (int)Math.sqrt(R*R - x*x);
            pixels.add(new MyPixelRect(size,x0+x,y0+y));
            pixels.add(new MyPixelRect(size,x0+y,y0+x));
            pixels.add(new MyPixelRect(size,x0+y,y0-x));
            pixels.add(new MyPixelRect(size,x0+x,y0-y));
            pixels.add(new MyPixelRect(size,x0-x,y0-y));
            pixels.add(new MyPixelRect(size,x0-y,y0-x));
            pixels.add(new MyPixelRect(size,x0-y,y0+x));
            pixels.add(new MyPixelRect(size,x0-x,y0+y));
        }
    }

}
