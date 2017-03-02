package com.example.vy.kg.graphics.pixels;

/**
 * Created by vy on 2/11/17.
 */

public abstract class MyPixel {
    protected int size;
    protected int x, y;
    protected int color = 0xFF000000;
    protected float [] points;

    public MyPixel(int size,int x, int y){
        this.size = size;
        this.x = x;
        this.y = y;
    }

    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
    }

    public  float[] getPixel(){
        return points;
    }
}
