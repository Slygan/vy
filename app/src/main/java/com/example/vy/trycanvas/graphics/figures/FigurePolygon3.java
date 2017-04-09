package com.example.vy.trycanvas.graphics.figures;

import android.graphics.Bitmap;

import com.example.vy.trycanvas.graphics.Drawer;

/**
* Класс предназначен для построения полигонов в виде треугольника
 * x1,x2,x3,y1,y2,y3 - соответствующие координаты 3х точек
* */
public class FigurePolygon3 extends Figure {

    private int x1,x2,x3,y1,y2,y3;

    public FigurePolygon3(int x1, int x2, int x3, int y1, int y2, int y3){
        this.x1 = x1;
        this.x2 = x2;
        this.x3 = x3;
        this.y1 = y1;
        this.y2 = y2;
        this.y3 = y3;
    }

    public Figure buildPolygon(Bitmap bitmap){
        pixels.addAll(Drawer.getLine(x1,y1,x2,y2,color,bitmap).getFigure());
        if(x3 != -1){
            pixels.addAll(Drawer.getLine(x2,y2,x3,y3,color,bitmap).getFigure());
            pixels.addAll(Drawer.getLine(x3,y3,x1,y1,color,bitmap).getFigure());
        }
        return this;
    }

}
