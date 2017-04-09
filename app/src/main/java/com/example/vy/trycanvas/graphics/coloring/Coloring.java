package com.example.vy.trycanvas.graphics.coloring;

import android.graphics.Bitmap;
import android.util.Log;

import com.example.vy.trycanvas.graphics.pixels.MyPixelRect;
import com.example.vy.trycanvas.graphics.pixels.Point;

import java.util.ArrayList;
import java.util.Stack;

public class Coloring {

    final String LOG_TAG = "VY_LOGS";
    ArrayList<Point> pixels = new ArrayList<>();


    public ArrayList<Point> paintOverZatrav(int x, int y, int colorFill, Bitmap bmp){

        Stack<Point> stack = new Stack<>();
        stack.push(new Point(x,y));
        int width = bmp.getWidth();
        int height = bmp.getHeight();

        while (stack.size()!=0) {
            Point point = stack.pop();
            x = point.getX();
            y = point.getY();

            int rowCount = 0;

            int tempX = x, tempY = y;
            //холостой пробег влево
            while (bmp.getPixel(tempX, tempY) == 0 && tempX>0) {
                //pixels.add(new Point(tempX, tempY));
                bmp.setPixel(tempX, tempY, colorFill);
                tempX--;
                rowCount++;
            }
            tempX = x + 1;
            //холостой пробег вправо
            while (tempX<width-1 && bmp.getPixel(tempX, tempY) == 0) {
                //pixels.add(new Point(tempX, tempY));
                bmp.setPixel(tempX, tempY, colorFill);
                tempX++;
                rowCount++;
            }
            //находим затр пиксели
            tempY = y - 1;
            int tempY2 = y + 1;
            if(y>1 && y < height-1){
                boolean is1 = false, is2 = false;
                int i = 0;
                while (i < rowCount) {
                    tempX--;
                    i++;
                    if (!is1 && bmp.getPixel(tempX, tempY) == 0) {
                        stack.push(new Point(tempX, tempY));
                        is1 = true;
                        if (is2) {
                            break;
                        }
                    }
                    if (!is2 && bmp.getPixel(tempX, tempY2) == 0) {
                        is2 = true;
                        stack.push(new Point(tempX, tempY2));
                        if (is1) {
                            break;
                        }
                    }

                }
            }

        }
        return pixels;
    }


}
