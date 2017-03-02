package com.example.vy.kg.graphics.figures;

import com.example.vy.kg.graphics.pixels.MyPixelRect;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by vy on 2/13/17.
 */

public abstract class Figure {

    protected int colorFill = 0xFF000000;
    protected int color = 0xFF000000;

    protected CopyOnWriteArrayList<MyPixelRect> pixels;

    public Figure(){
        pixels = new CopyOnWriteArrayList<>();
    }

    public CopyOnWriteArrayList<MyPixelRect> getFigure() {
        return pixels;
    }

    public int getColorFill() {
        return colorFill;
    }

    public void setColorFill(int colorFill) {
        this.colorFill = colorFill;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
