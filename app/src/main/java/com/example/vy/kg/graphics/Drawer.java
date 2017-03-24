package com.example.vy.kg.graphics;

import android.content.Context;
import android.graphics.Color;

import com.example.vy.kg.Controller;
import com.example.vy.kg.DrawThread;
import com.example.vy.kg.graphics.figures.Figure;
import com.example.vy.kg.graphics.figures.FigureCurve;
import com.example.vy.kg.graphics.figures.FigureLine;
import com.example.vy.kg.graphics.figures.FigurePolygon;
import com.example.vy.kg.graphics.figures.FigureRect;
import com.example.vy.kg.graphics.figures.FigureRound;
import com.example.vy.kg.graphics.pixels.MyPixel;
import com.example.vy.kg.graphics.pixels.MyPixelRect;
import java.util.ArrayList;
import java.util.Random;

/*
* Объект класса сохдается в начале работы программы в классе MainActivity
* Инициализируется контекстом для вывода диалоговых окон
* */

public class Drawer {

    private static Drawer instance;
    private Context context;

    private Drawer() { }
    public Drawer(Context context){
        this.context = context;
        instance = this;
    }
    public static Drawer getInstance(){
        return instance;
    }

    public Figure getLine(int x1, int y1, int x2, int y2, int color) {

        FigureLine line = new FigureLine(x1,y1,x2,y2);
        line.setColor(color);
        line.buildBresenLine(Controller.pixelSize);

        return line;
    }

    public Figure getRound(int x, int y, int r, int color, int size) {

        FigureRound round = new FigureRound(x,y,r);
        round.setColor(color);
        round.BrazAlgCircle(Controller.pixelSize);

        return round;
    }

    public Figure getRectangle(int x1, int y1, int x2, int y2, int colorBorder, int colorFill) {
        FigureRect rect = new FigureRect(x1, x2, y1, y2);
        rect.setColor(colorBorder);
        rect.setColorFill(colorFill);
        rect.buildRect();
        return rect;
    }

    public ArrayList<MyPixelRect> getMozaik(){
        ArrayList<MyPixelRect> mozaik = new ArrayList<>();
        int size = Controller.pixelSize;
        int w = DrawThread.width + size;
        int h = DrawThread.height + size;
        Random random = new Random();
        MyPixelRect pixel;
        for (int i = size/2; i < h; i += size) {
            for (int j = size/2; j < w; j += size) {
                pixel = new MyPixelRect(size, j, i);
                pixel.setColor(random.nextInt() % Color.rgb(125,125,125) + Color.rgb(125,125,125));
                mozaik.add(pixel);
            }
        }
        return mozaik;
    }

    public Figure getBezierLine(int [] points){
        int size = Controller.pixelSize;

        FigureCurve brezenLine = new FigureCurve();
        brezenLine.getBezierLine(points,size);

        return brezenLine;
    }

    public Figure getPolygon(int x1, int x2, int x3, int y1, int y2, int y3, int colorBorder, int colorFill){
        FigurePolygon polygon = new FigurePolygon(x1, x2, x3, y1, y2, y3);
        polygon.setColor(colorBorder);
        polygon.setColorFill(colorFill);
        polygon.buildPolygon();

        return polygon;
    }

}
