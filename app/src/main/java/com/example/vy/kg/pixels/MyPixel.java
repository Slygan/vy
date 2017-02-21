package com.example.vy.kg.pixels;

/**
 * Created by vy on 2/11/17.
 */

abstract class MyPixel {
    protected int size;
    protected int x, y;

    protected float [] points;

    public MyPixel(int size,int x, int y){
        this.size = size;
        this.x = x;
        this.y = y;
    }

    public void setCoordinatCenter(int x, int y){
        this.x = x;
        this.y = y;
    }

    public void setSize(int size){
        this.size = size;
    }
    public int getSize(){
        return size;
    }

    public  float[] getPixel(){
        return points;
    }

    public abstract void refactor();
}
