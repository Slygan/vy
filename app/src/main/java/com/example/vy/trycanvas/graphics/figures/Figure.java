package com.example.vy.trycanvas.graphics.figures;

import com.example.vy.trycanvas.graphics.pixels.Point;

import java.util.concurrent.CopyOnWriteArrayList;


/**
* Абстрактный класс фигуры
 * предназначен для хранения пикселей и цвета фигуры
 * colorFill - цвет заливки фигуры (круг, прямоугольник и тд)
 * color - цвет границы фигуры (круг, прямоугольник и тд)
 * color - цвет линии (линия)
 * pixels - массив точей из которых состоит данная фигура
* */
public abstract class Figure {

    protected int colorFill = 0xFF000000;
    protected int color = 0xFF000000;

    protected CopyOnWriteArrayList<Point> pixels;

    public Figure(){
        pixels = new CopyOnWriteArrayList<>();
    }

    public CopyOnWriteArrayList<Point> getFigure() {
        return pixels;
    }

    public int getColorFill() {
        return colorFill;
    }

    public Figure setColorFill(int colorFill) {
        this.colorFill = colorFill;
        return this;
    }

    public int getColor() {
        return color;
    }

    public Figure setColor(int color) {
        this.color = color;
        return this;
    }
}
