package com.example.vy.kg.graphics.figures;

import com.example.vy.kg.graphics.pixels.MyPixelRect;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by vy on 2/23/17.
 */

public class FigureRect extends Figure {

    final String LOG_TAG = "VY_LOGS";

    private int x1 = 0, x2 = 0, y1 = 0, y2 = 0;

    public FigureRect(int x1, int x2, int y1, int y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public CopyOnWriteArrayList<MyPixelRect> buildRect(){
        pixels.clear();

        FigureLine line1 = new FigureLine(x1,y1,x2,y1);
        FigureLine line2 = new FigureLine(x1,y1,x1,y2);
        FigureLine line3 = new FigureLine(x1,y2,x2,y2);
        FigureLine line4 = new FigureLine(x2,y1,x2,y2);

        line1.buildBresenLine(1);
        line2.buildBresenLine(1);
        line3.buildBresenLine(1);
        line4.buildBresenLine(1);

        pixels.addAll(line1.getFigure());
        pixels.addAll(line2.getFigure());
        pixels.addAll(line3.getFigure());
        pixels.addAll(line4.getFigure());

        return pixels;
    }

}
