package com.example.vy.kg;

import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import com.example.vy.kg.graphics.figures.Figure;
import com.example.vy.kg.graphics.pixels.MyPixelRect;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by vy on 2/10/17.
 */

class DrawThread extends Thread{

    private boolean runFlag = false;
    private SurfaceHolder surfaceHolder;
    private Paint paint;

    public static int pixelSize = 1;
    public static int width = 0;
    public static int height = 0;


    final String LOG_TAG = "VY_LOGS";

    static CopyOnWriteArrayList<MyPixelRect> pixels = new CopyOnWriteArrayList<>();
    static CopyOnWriteArrayList<Figure> figures = new CopyOnWriteArrayList<>();
    static CopyOnWriteArrayList<Figure> motion = new CopyOnWriteArrayList<>();

    public DrawThread(SurfaceHolder surfaceHolder, Resources resources){
        this.surfaceHolder = surfaceHolder;

        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(15);

    }
    public DrawThread(SurfaceHolder surfaceHolder, Resources resources, int color){
        this.surfaceHolder = surfaceHolder;

        paint = new Paint();
        paint.setColor(color);
        paint.setTextSize(15);

    }



    public void setRunning(boolean run) {
        runFlag = run;
    }

    @Override
    public void run() {
        Canvas canvas = null;

        while (runFlag) {
            try {
                canvas = surfaceHolder.lockCanvas(null);
                width = canvas.getWidth();
                height = canvas.getHeight();
                synchronized (surfaceHolder) {
                    canvas.drawColor(Color.WHITE);

                    for (MyPixelRect pixel : pixels) {
                        canvas.drawPoints(pixel.getPixel(), paint);
                    }

                    for (Figure figure : motion) {
                        for (MyPixelRect pixel : figure.getFigure()) {
                            canvas.drawPoints(pixel.getPixel(), paint);
                        }
                    }

                    for (Figure figure : figures) {
                        for (MyPixelRect pixel : figure.getFigure()) {
                            canvas.drawPoints(pixel.getPixel(), paint);
                        }
                    }

                    //int [] mas = {0xffffffff,0xffffffff,0xffffffff,0xffffffff,0xffffffff,0xffffffff,0xffffffff,0xffffffff,0xffffffff,0xffffffff,0xffffffff,0xffffffff,0xffffffff,0xffffffff,0xffffffff,0xffffffff};
                    //canvas.drawBitmap(mas,0,4,450,450,4,4,true,null);

                }
            } catch (Exception ex) {

            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }


    }
}
