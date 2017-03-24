package com.example.vy.kg.graphics.coloring;

import android.graphics.Bitmap;
import com.example.vy.kg.graphics.pixels.MyPixelRect;
import java.util.ArrayList;
import java.util.Stack;

public class Coloring {

    final String LOG_TAG = "VY_LOGS";


    public ArrayList<MyPixelRect> paintOverZatrav(int x, int y, int colorFill, Bitmap bmp){

        ArrayList<MyPixelRect> pixels = new ArrayList<>();
        Stack<MyPixelRect> stack = new Stack<>();
        stack.push(new MyPixelRect(1,x,y,colorFill));
        int width = bmp.getWidth();
        int height = bmp.getHeight();


        while (stack.size()!=0) {
            MyPixelRect myPixelRect = stack.pop();
            x = myPixelRect.getX();
            y = myPixelRect.getY();

            int rowCount = 0;

            int tempX = x, tempY = y;
            //холостой пробег влево
            while (bmp.getPixel(tempX, tempY) == -1 && tempX>0) {
                pixels.add(new MyPixelRect(1, tempX, tempY,colorFill));
                bmp.setPixel(tempX, tempY, colorFill);
                tempX--;
                rowCount++;
            }
            tempX = x + 1;
            //холостой пробег вправо
            while (tempX<width-1 && bmp.getPixel(tempX, tempY) == -1) {
                pixels.add(new MyPixelRect(1, tempX, tempY,colorFill));
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
                    if (!is1 && bmp.getPixel(tempX, tempY) == -1) {
                        stack.push(new MyPixelRect(1, tempX, tempY,colorFill));
                        is1 = true;
                        if (is2) {
                            break;
                        }
                    }
                    if (!is2 && bmp.getPixel(tempX, tempY2) == -1) {
                        is2 = true;
                        stack.push(new MyPixelRect(1, tempX, tempY2,colorFill));
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
