package com.example.vy.kg.graphics.figures;

import com.example.vy.kg.Controller;
import com.example.vy.kg.DrawThread;
import com.example.vy.kg.graphics.Drawer;
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

    public void buildRect(){
        Drawer drawer = Drawer.getInstance();
        pixels.addAll(drawer.getLine(x1,y1,x2,y1, Controller.colorLine).getFigure());
        pixels.addAll(drawer.getLine(x1,y1,x1,y2,Controller.colorLine).getFigure());
        pixels.addAll(drawer.getLine(x1,y2,x2,y2,Controller.colorLine).getFigure());
        pixels.addAll(drawer.getLine(x2,y1,x2,y2,Controller.colorLine).getFigure());
    }

    public void buildFillRect(){
        Drawer drawer = Drawer.getInstance();
        pixels.addAll(drawer.getRectangle(x1,y1,x2,y2,color).getFigure());
        for(int i = x1+1; i < x2; i++){
            for(int j = y1+1; j < y2; j++){
                pixels.add(new MyPixelRect(1,i,j,colorFill));
            }
        }
    }

}
