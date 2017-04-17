package com.example.vy.trycanvas.graphics;

import android.graphics.Bitmap;
import android.graphics.Color;
import com.example.vy.trycanvas.graphics.coloring.Coloring;
import com.example.vy.trycanvas.graphics.figures.Figure;
import com.example.vy.trycanvas.graphics.figures.FigureBSpline;
import com.example.vy.trycanvas.graphics.figures.FigureCurve;
import com.example.vy.trycanvas.graphics.figures.FigureErmit;
import com.example.vy.trycanvas.graphics.figures.FigureLine;
import com.example.vy.trycanvas.graphics.figures.FigurePolygon3;
import com.example.vy.trycanvas.graphics.figures.FigurePolygoneN;
import com.example.vy.trycanvas.graphics.figures.FigureRect;
import com.example.vy.trycanvas.graphics.figures.FigureRound;
import com.example.vy.trycanvas.graphics.figures.FiguresNURBS;
import com.example.vy.trycanvas.graphics.pixels.MyPixel;
import com.example.vy.trycanvas.graphics.pixels.MyPixelRect;
import com.example.vy.trycanvas.graphics.pixels.Point;
import java.util.ArrayList;
import java.util.Random;

/**
 * Класс предназначен для отрисовки примитивов и возврата соответствующих им фигур
 *
* */

public class Drawer {

    public static MyPixel getPixel(int x, int y, int color, int size, Bitmap bitmap){
        return new MyPixelRect(size,x,y,color,bitmap);
    }

    public static Figure getLine(int x1, int y1, int x2, int y2, int color, Bitmap bitmap) {
        return ((FigureLine)
                new FigureLine(x1,y1,x2,y2)
                        .setColor(color))
                        .buildBresenLine(bitmap);
    }

    public static Figure getRound(int x, int y, int r, int color, Bitmap bitmap) {
        return ((FigureRound)
                new FigureRound(x,y,r)
                        .setColor(color))
                        .BrazAlgCircle(bitmap);
    }

    public static Figure getEllipse(int x1, int y1, int x2, int y2, int color, Bitmap bitmap){
        int a,b,x,y;
        if(x2 < x1){
            int temp = x2;
            x2 = x1;
            x1 = temp;
        }
        if(y2 < y1){
            int temp = y2;
            y2 = y1;
            y1 = temp;
        }
        x = x1+(x2-x1)/2;
        y = y1+(y2-y1)/2;
        a = x2 - x;
        b = y2 - y;
        return ((FigureRound)
                new FigureRound(1,1,1)
                        .setColor(color))
                        .ellipse(x,y,a,b,color,bitmap);
    }

    public static Figure getRectangle(int x1, int y1, int x2, int y2, int color, Bitmap bitmap) {
        return ((FigureRect)
                new FigureRect(x1, x2, y1, y2)
                        .setColor(color))
                        .buildRect(bitmap);
    }

    public static Figure getFillRectangle(int x1, int y1, int x2, int y2, int color, int colorFill, Bitmap bitmap){
        return ((FigureRect)
                new FigureRect(x1, x2, y1, y2)
                        .setColor(color)
                        .setColorFill(colorFill))
                        .buildFillRect(bitmap);
    }

    public static void getMozaik(Bitmap bitmap, int pixelSize){
        int w = bitmap.getWidth() - pixelSize/2;
        int h = bitmap.getHeight() - pixelSize/2;
        Random random = new Random();
        int color;
        for (int i = pixelSize/2; i < h; i += pixelSize) {
            for (int j = pixelSize/2; j < w; j += pixelSize) {
                color = random.nextInt() % Color.rgb(125,125,125) + Color.rgb(125,125,125);
                new MyPixelRect(pixelSize, j, i, color, bitmap);
            }
        }
    }

    public static Figure getBezierLine(int [] points, int color, Bitmap bitmap){
        return ((FigureCurve)
                new FigureCurve()
                        .setColor(color))
                        .getBezierLine(points, bitmap);
    }

    public static Figure getPolygon3(int x1, int x2, int x3, int y1, int y2, int y3, int color, int colorFill, Bitmap bitmap){
        return ((FigurePolygon3)
                new FigurePolygon3(x1, x2, x3, y1, y2, y3)
                        .setColor(color)
                        .setColorFill(colorFill))
                        .buildPolygon(bitmap);
    }

    public static Figure getTriangle(int x1, int y1, int x2, int y2, int color, Bitmap bitmap){
        return ((FigurePolygon3)
                new FigurePolygon3(x1, x2, (x2-x1)/2+x1, y1, y1, y2)
                        .setColor(color))
                        .buildPolygon(bitmap);
    }

    public static Figure getFillPolygonN(int [] points, Bitmap bitmap, int colorLine, int colorFill){
        return ((FigurePolygoneN)
                new FigurePolygoneN()
                        .setColor(colorLine)
                        .setColorFill(colorFill))
                        .draw(points, bitmap) ;
    }
    public static Figure getErmit(int [] points, Bitmap bitmap, int colorLine){
        return ((FigureErmit)
                new FigureErmit()
                        .setColor(colorLine))
                        .draw(points, bitmap);
    }
    public static Figure getNURBS(int [] points, Bitmap bitmap, int colorLine){
        return ((FiguresNURBS)
                new FiguresNURBS(3)
                        .setColor(colorLine))
                        .draw(points, bitmap);
    }
    public static Figure getBSpline(int [] points, Bitmap bitmap, int colorLine){
        return ((FigureBSpline)
                new FigureBSpline(2)
                        .setColor(colorLine))
                        .draw(points, bitmap);
    }

    public static ArrayList<Point> fillFigureZatrav(int x, int y, int colorFill, Bitmap bitmap){
        return new Coloring().paintOverZatrav(x,y,colorFill,bitmap);
    }

}
