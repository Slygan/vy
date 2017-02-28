package com.example.vy.kg.graphics.figures;

import android.graphics.Color;

import com.example.vy.kg.graphics.pixels.MyPixelRect;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by vy on 2/13/17.
 */

public abstract class Figure {

    protected Color colorFill = null;
    protected Color colorBorder = null;

    protected CopyOnWriteArrayList<MyPixelRect> pixels;

    public Figure(){
        pixels = new CopyOnWriteArrayList<>();
    }

    public CopyOnWriteArrayList<MyPixelRect> getFigure() {
        return pixels;
    }

    public Color getColorFill() {
        return colorFill;
    }

    public void setColorFill(Color colorFill) {
        this.colorFill = colorFill;
    }

    public Color getColorBorder() {
        return colorBorder;
    }

    public void setColorBorder(Color colorBorder) {
        this.colorBorder = colorBorder;
    }
}
