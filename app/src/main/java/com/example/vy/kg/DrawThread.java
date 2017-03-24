package com.example.vy.kg;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import com.example.vy.kg.graphics.figures.Figure;
import com.example.vy.kg.graphics.pixels.MyPixelRect;
import java.util.concurrent.CopyOnWriteArrayList;

public class DrawThread extends Thread{

    private boolean runFlag = false;
    private SurfaceHolder surfaceHolder;
    public static Paint paint;

    public static int width = 0;
    public static int height = 0;
    public static Bitmap b = null;
    public static Canvas c = null;
    public static boolean flag = false;


    public static CopyOnWriteArrayList<MyPixelRect> pixels = new CopyOnWriteArrayList<>();
    public static CopyOnWriteArrayList<Figure> figures = new CopyOnWriteArrayList<>();
    public static CopyOnWriteArrayList<Figure> motion = new CopyOnWriteArrayList<>();

    public DrawThread(SurfaceHolder surfaceHolder, Resources resources, Context context) {
        this.surfaceHolder = surfaceHolder;

        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(15);

        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        width = metrics.widthPixels;
        height = metrics.heightPixels;
        b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        c = new Canvas(b);
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

                synchronized (surfaceHolder) {
                    if(flag) {
                        pixels.clear();
                        motion.clear();
                        figures.clear();
                        canvas.drawBitmap(b,0,0,paint);
                    }else {
                        if (!flag) {
                            canvas.drawColor(Color.WHITE);
                        }
                        for (MyPixelRect pixel : pixels) {
                            paint.setColor(pixel.getColor());
                            canvas.drawPoints(pixel.getPixel(), paint);
                        }

                        for (Figure figure : motion) {
                            paint.setColor(figure.getColor());
                            for (MyPixelRect pixel : figure.getFigure()) {
                                canvas.drawPoints(pixel.getPixel(), paint);
                            }
                        }

                        for (Figure figure : figures) {
                            paint.setColor(figure.getColor());
                            for (MyPixelRect pixel : figure.getFigure()) {
                                canvas.drawPoints(pixel.getPixel(), paint);
                            }
                        }
                    }
                    /*
                    int [] mas = {0xffffffff,0xffffffff,0xffffffff,0xffffffff,0xffffffff,0xffffffff,0xffffffff,0xffffffff,0xffffffff,0xffffffff,0xffffffff,0xffffffff,0xffffffff,0xffffffff,0xffffffff,0xffffffff};
                    canvas.drawBitmap(mas,0,4,450,450,4,4,true,null);
                    */
                }
            } catch (Exception ex) {

            } finally {
                if (canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }


    }

    public static void saveBMP(){
        c.drawColor(Color.WHITE);

        for (MyPixelRect pixel : pixels) {
            paint.setColor(pixel.getColor());
            c.drawPoints(pixel.getPixel(), paint);

        }

        for (Figure figure : motion) {
            paint.setColor(figure.getColor());
            for (MyPixelRect pixel : figure.getFigure()) {
                c.drawPoints(pixel.getPixel(), paint);
            }
        }

        for (Figure figure : figures) {
            paint.setColor(figure.getColor());
            for (MyPixelRect pixel : figure.getFigure()) {
                c.drawPoints(pixel.getPixel(), paint);
            }
        }

    }


}
