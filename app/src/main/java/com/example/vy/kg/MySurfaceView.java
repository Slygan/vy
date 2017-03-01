package com.example.vy.kg;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.example.vy.kg.graphics.figures.FigureLine;
import com.example.vy.kg.graphics.figures.FigureRect;
import com.example.vy.kg.graphics.figures.FigureRound;
import com.example.vy.kg.graphics.pixels.MyPixelRect;

/**
 * Created by vy on 2/10/17.
 */

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    public static boolean IS_PEN = false;
    public static boolean IS_LINE = false;
    public static boolean IS_ROUND = false;
    public static boolean IS_RECT = false;

    private DrawThread drawThread;

    public Context context;

    public MySurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
        this.context = context;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getHolder(), getResources(),0xff00ff00);
        drawThread.setRunning(true);
        drawThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        drawThread.setRunning(false);
        while (retry) {
            try {
                drawThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    private int x1 = -1, y1 = 0, x2 = 0, y2 = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(IS_LINE){
            if(x1 == -1){
                x1 = (int)event.getX();
                y1 = (int)event.getY();
            }else{
                x2 = (int)event.getX();
                y2 = (int)event.getY();

                FigureLine tempLine = new FigureLine(x1,y1,x2,y2);
                tempLine.buildBresenLine(1);

                DrawThread.motion.clear();
                if(event.getAction() == MotionEvent.ACTION_UP){
                    DrawThread.figures.add(tempLine);
                    x1 = -1;
                }else{
                    DrawThread.motion.add(tempLine);
                }
            }

        }

        if(IS_ROUND){
            if(x1 == -1){
                x1 = (int)event.getX();
                y1 = (int)event.getY();
            }else{
                x2 = (int)event.getX();
                y2 = (int)event.getY();
                int R = (int)Math.sqrt(Math.abs((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1)));

                FigureRound round = new FigureRound(x1,y1,R);
                round.BrazAlgCircle(DrawThread.pixelSize);

                DrawThread.motion.clear();
                if(event.getAction() == MotionEvent.ACTION_UP){
                    DrawThread.figures.add(round);
                    x1 = -1;
                }else{
                    DrawThread.motion.add(round);
                }
            }
        }

        if(IS_RECT){
            if(x1 == -1){
                x1 = (int)event.getX();
                y1 = (int)event.getY();
            }else{
                x2 = (int)event.getX();
                y2 = (int)event.getY();

                FigureRect rect = new FigureRect(x1,x2,y1,y2);
                rect.buildRect();

                DrawThread.motion.clear();
                if(event.getAction() == MotionEvent.ACTION_UP){
                    DrawThread.figures.add(rect);
                    x1 = -1;
                }else{
                    DrawThread.motion.add(rect);
                }

            }
        }

        if(IS_PEN){
            DrawThread.pixels.add(new MyPixelRect(DrawThread.pixelSize,(int)event.getX(),(int)event.getY()));
        }
        return true;
    }
}
