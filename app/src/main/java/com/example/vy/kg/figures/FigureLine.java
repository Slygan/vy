package com.example.vy.kg.figures;

import com.example.vy.kg.pixels.MyPixelRect;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by vy on 2/13/17.
 */

public class FigureLine extends Figure {

    private int x1,x2,y1,y2;

    public FigureLine(int x1,int y1, int x2, int y2){
        if(x1 < x2){
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }else{
            this.x1 = x2;
            this.x2 = x1;
            this.y1 = y2;
            this.y2 = y1;
        }
    }

    public CopyOnWriteArrayList<MyPixelRect> buildParamLine(int size){

        pixels.clear();

        double x = x1, y = y1;

        pixels.add(new MyPixelRect(size,(int)x,(int)y));

        int dx = x2-x1;
        int dy = y2-y1;

        double t = 1.0 / Math.max(Math.abs(dx),Math.abs(dy));

        while(x < x2){
            x+= t*dx;
            y+= t*dy;

            //x+=size;
            //y+=size;

            pixels.add(new MyPixelRect(size,(int)x,(int)y));
        }

        return pixels;
    }

    private int sign (int x) {
        return (x > 0) ? 1 : (x < 0) ? -1 : 0;
    }
    public CopyOnWriteArrayList<MyPixelRect> buildBresenLine (int size){

        pixels.clear();

        int x, y, dx, dy, incx, incy, pdx, pdy, es, el, err;

        dx = x2 - x1;//проекция на ось икс
        dy = y2 - y1;//проекция на ось игрек

        incx = sign(dx);
	/*
	 * Определяем, в какую сторону нужно будет сдвигаться. Если dx < 0, т.е. отрезок идёт
	 * справа налево по иксу, то incx будет равен -1.
	 * Это будет использоваться в цикле постороения.
	 */
        incy = sign(dy);
	/*
	 * Аналогично. Если рисуем отрезок снизу вверх -
	 * это будет отрицательный сдвиг для y (иначе - положительный).
	 */

        if (dx < 0) dx = -dx;//далее мы будем сравнивать: "if (dx < dy)"
        if (dy < 0) dy = -dy;//поэтому необходимо сделать dx = |dx|; dy = |dy|
        //эти две строчки можно записать и так: dx = Math.abs(dx); dy = Math.abs(dy);

        if (dx > dy){
            //определяем наклон отрезка:
	 /*
	  * Если dx > dy, то значит отрезок "вытянут" вдоль оси икс, т.е. он скорее длинный, чем высокий.
	  * Значит в цикле нужно будет идти по икс (строчка el = dx;), значит "протягивать" прямую по иксу
	  * надо в соответствии с тем, слева направо и справа налево она идёт (pdx = incx;), при этом
	  * по y сдвиг такой отсутствует.
	  */
            pdx = incx;	pdy = 0;
            es = dy;	el = dx;
        }
        else//случай, когда прямая скорее "высокая", чем длинная, т.е. вытянута по оси y
        {
            pdx = 0;	pdy = incy;
            es = dx;	el = dy;//тогда в цикле будем двигаться по y
        }

        x = x1;
        y = y1;
        err = el/2;
        pixels.add(new MyPixelRect(size,x,y));//ставим первую точку

        for (int t = 0; t < el; t++){
            err -= es;
            if (err < 0){
                err += el;
                x += incx;//сдвинуть прямую (сместить вверх или вниз, если цикл проходит по иксам)
                y += incy;//или сместить влево-вправо, если цикл проходит по y
            }else{
                x += pdx;//продолжить тянуть прямую дальше, т.е. сдвинуть влево или вправо, если
                y += pdy;//цикл идёт по иксу; сдвинуть вверх или вниз, если по y
            }

            pixels.add(new MyPixelRect(size,x,y));

        }
        return pixels;
    }


}
