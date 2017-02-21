package com.example.vy.kg.figures;

import com.example.vy.kg.pixels.MyPixelRect;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by vy on 2/13/17.
 */

public abstract class Figure {

    protected CopyOnWriteArrayList<MyPixelRect> pixels;

    public Figure(){
        pixels = new CopyOnWriteArrayList<>();
    }

    public CopyOnWriteArrayList<MyPixelRect> getFigure() {
        return pixels;
    }

}
