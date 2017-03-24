package com.example.vy.kg;

import android.content.Context;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.vy.kg.graphics.Drawer;
import com.example.vy.kg.graphics.coloring.Coloring;
import com.example.vy.kg.graphics.pixels.MyPixelRect;

import java.util.concurrent.CopyOnWriteArrayList;

public class MySurfaceView extends SurfaceView implements SurfaceHolder.Callback {


    private CopyOnWriteArrayList<Integer> points;
    private int x1 = -1, y1 = -1, x2 = -1, y2 = -1, x3 = -1, y3 = -1;

    private DrawThread drawThread;
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



    /*
    * Обработка любых дейстивй пользователя
    * на области экрана
    * */
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(controller.IS_LINE){
            if(x1 == -1){
                x1 = (int)event.getX();
                y1 = (int)event.getY();
            }else{
                x2 = (int)event.getX();
                y2 = (int)event.getY();
                DrawThread.motion.clear();
                if(event.getAction() == MotionEvent.ACTION_UP){
                    DrawThread.figures.add(drawer.getLine(x1,y1,x2,y2,Controller.colorLine));
                    x1 = -1; x2 = -1;
                }else{
                    DrawThread.motion.add(drawer.getLine(x1,y1,x2,y2,Controller.colorLine));
                }
            }

        }

        if(controller.IS_ROUND){
            if(x1 == -1){
                x1 = (int)event.getX();
                y1 = (int)event.getY();
            }else{
                x2 = (int)event.getX();
                y2 = (int)event.getY();
                int R = (int)Math.sqrt(Math.abs((x2-x1)*(x2-x1) + (y2-y1)*(y2-y1)));
                DrawThread.motion.clear();
                if(event.getAction() == MotionEvent.ACTION_UP){
                    DrawThread.figures.add(drawer.getRound(x1,y1,R,Controller.colorLine,1));
                    x1 = -1; x2 = -1;
                }else{
                    DrawThread.motion.add(drawer.getRound(x1,y1,R,Controller.colorLine,1));
                }
            }
        }

        if(controller.IS_RECT){
            if(x1 == -1){
                x1 = (int)event.getX();
                y1 = (int)event.getY();
            }else{
                x2 = (int)event.getX();
                y2 = (int)event.getY();
                DrawThread.motion.clear();
                if(event.getAction() == MotionEvent.ACTION_UP){
                    DrawThread.figures.add(drawer.getRectangle(x1,y1,x2,y2,Controller.colorLine,Controller.colorFill));
                    x1 = -1; x2 = -1;
                }else{
                    DrawThread.motion.add(drawer.getRectangle(x1,y1,x2,y2,Controller.colorLine,Controller.colorFill));
                }

            }
        }

        if(controller.IS_BEZIER){
            if(event.getAction() == MotionEvent.ACTION_UP){
                points.add((int)event.getX());
                points.add((int)event.getY());

                if(points.size() >= 8){
                    int [] pts = new int [points.size()];

                    for(int i = 0; i < points.size(); i++){
                        pts[i] = points.get(i);
                    }

                    DrawThread.figures.add(drawer.getBezierLine(pts));
                }
            }
        }

        if(controller.IS_POLYGON){
            if(x1 == -1) {
                x1 = (int) event.getX();
                y1 = (int) event.getY();
            } else {
                if(x2 == -1){
                    x2 = (int)event.getX();
                    y2 = (int)event.getY();
                    DrawThread.motion.clear();
                    if(event.getAction() == MotionEvent.ACTION_UP){
                        DrawThread.figures.add(drawer.getLine(x1,y1,x2,y2,Controller.colorLine));
                    }else{
                        DrawThread.motion.add(drawer.getLine(x1,y1,x2,y2,Controller.colorLine));
                        x2 = -1;
                    }
                }else{
                    x3 = (int)event.getX();
                    y3 = (int)event.getY();
                    DrawThread.motion.clear();
                    if(event.getAction() == MotionEvent.ACTION_UP && x3!=-1){
                        DrawThread.figures.remove(DrawThread.figures.size()-1);
                        DrawThread.figures.add(drawer.getPolygon(x1,x2,x3,y1,y2,y3,Controller.colorLine,Controller.colorFill));
                        x1 = -1; x2 = -1; x3 = -1;
                    }else{
                        DrawThread.motion.add(drawer.getPolygon(x1,x2,x3,y1,y2,y3,Controller.colorLine,Controller.colorFill));
                        x3 = -1;
                    }
                }
            }
        }

        if(controller.IS_ZATRAVKA){
            DrawThread.saveBMP();
            Coloring color = new Coloring();
            DrawThread.pixels.addAll(color.paintOverZatrav((int)event.getX(),(int)event.getY(),Controller.colorFill,DrawThread.b));
        }

        if(controller.IS_PEN){
            DrawThread.pixels.add(new MyPixelRect(Controller.pixelSize,(int)event.getX(),(int)event.getY(),Controller.colorLine));
        }
        return true;
    }
}
