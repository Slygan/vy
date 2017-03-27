package com.example.vy.kg.graphics.figures;

import com.example.vy.kg.graphics.Drawer;

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

    public void buildPolygon(){
        Drawer drawer = Drawer.getInstance();
        pixels.addAll(drawer.getLine(x1,y1,x2,y2,0).getFigure());
        if(x3 != -1){
            pixels.addAll(drawer.getLine(x2,y2,x3,y3,0).getFigure());
            pixels.addAll(drawer.getLine(x3,y3,x1,y1,0).getFigure());
        }
    }

}
