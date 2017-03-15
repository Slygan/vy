package com.example.vy.kg;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.example.vy.kg.graphics.Drawer;
import com.example.vy.kg.graphics.pixels.MyPixelRect;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private int currentColor = 0xFF000000;

    private DrawThread drawThread;
    private CopyOnWriteArrayList<Integer> points;
    private Context context;
    private Drawer drawer;
    private Controller controller;

    public MySurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);

        this.context = context;
        drawer = Drawer.getInstance();
        controller = Controller.getInstance();
        points = new CopyOnWriteArrayList<>();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawThread = new DrawThread(getHolder(), getResources(), this.getContext());
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


    /*
    * Обработка любых дейстивй пользователя
    * по области экрана
    * */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(controller.IS_LINE()){
            if(x1 == -1){
                x1 = (int)event.getX();
                y1 = (int)event.getY();
            }else{
                x2 = (int)event.getX();
                y2 = (int)event.getY();
                DrawThread.motion.clear();
                if(event.getAction() == MotionEvent.ACTION_UP){
                    DrawThread.figures.add(drawer.getLine(x1,y1,x2,y2,currentColor));
                    x1 = -1; x2 = -1;
                }else{
                    DrawThread.motion.add(drawer.getLine(x1,y1,x2,y2,currentColor));
                }
            }

        }

        if(controller.IS_ROUND()){
            if(x1 == -1){
                x1 = (int)event.getX();
                y1 = (int)event.getY();
            }else{
                x2 = (int)event.getX();
                y2 = (int)event.getY();
                int R = (int)Math.sqrt(Math.abs((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1)));
                DrawThread.motion.clear();
                if(event.getAction() == MotionEvent.ACTION_UP){
                    DrawThread.figures.add(drawer.getRound(x1,y1,R,currentColor,1));
                    x1 = -1; x2 = -1;
                }else{
                    DrawThread.motion.add(drawer.getRound(x1,y1,R,currentColor,1));
                }
            }
        }

        if(controller.IS_RECT()){
            if(x1 == -1){
                x1 = (int)event.getX();
                y1 = (int)event.getY();
            }else{
                x2 = (int)event.getX();
                y2 = (int)event.getY();
                DrawThread.motion.clear();
                if(event.getAction() == MotionEvent.ACTION_UP){
                    DrawThread.figures.add(drawer.getRectangle(x1,y1,x2,y2,currentColor,currentColor));
                    x1 = -1; x2 = -1;
                }else{
                    DrawThread.motion.add(drawer.getRectangle(x1,y1,x2,y2,currentColor,currentColor));
                }

            }
        }

        if(controller.IS_BEZIER()){
            if(event.getAction() == MotionEvent.ACTION_UP){
                points.add((int)event.getX());
                points.add((int)event.getY());

                if(points.size() >= 8){
                    int [] pts = new int [points.size()];

                    for(int i = 0; i < points.size(); i++){
                        pts[i] = points.get(i);
                    }

                    DrawThread.figures.add(drawer.getBrezenLine(pts));
                }
            }
        }

        if(controller.IS_PEN()){
            DrawThread.pixels.add(new MyPixelRect(DrawThread.pixelSize,(int)event.getX(),(int)event.getY()));
        }
        return true;
    }
}
