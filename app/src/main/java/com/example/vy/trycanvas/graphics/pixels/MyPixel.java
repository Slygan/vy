package com.example.vy.trycanvas.graphics.pixels;


/**
* Абстрактный класс пикселя
 * предназначен для создания пикселей произвольного размера
 * size - размерность пикселя (если пиксель квадратный, то число реальных пикселей
 *                             будет равняться size*size)
 * point - координата центра пикселя
* */
public abstract class MyPixel {
    protected int size;
    protected Point point;
    protected int color = 0xFF000000;
    protected float [] points;


    public MyPixel(int size, int x, int y){
        this.size = size;
        this.point = new Point(x,y);
    }

    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
    }

    public int getX() {
        return point.getX();
    }
    public int getY() {
        return point.getY();
    }

    public  float[] getPixel(){
        return points;
    }
}
