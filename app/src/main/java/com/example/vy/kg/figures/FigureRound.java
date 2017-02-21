package com.example.vy.kg.figures;

import com.example.vy.kg.pixels.MyPixelRect;

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

        int x2 = x0, y2 = y0 + R;
        int x1 = 0, y1 = 0;
        for(int i = 1; i <= 360; i++){
            x1 = x2;
            y1 = y2;
            x2 = (int)(R * Math.sin((double)(i * Math.PI) / 180) + x0);
            y2 = (int)(R * Math.cos((double)(i * Math.PI) / 180) + y0);

            line = new FigureLine(x1,y1,x2,y2);
            line.buildParamLine(size);
            pixels.addAll(line.pixels);
        }
    }

}
