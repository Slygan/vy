package com.example.vy.kg;

import android.content.Context;

import com.example.vy.kg.graphics.Drawer;

public class Controller {
    private Context context;
    private Drawer drawer;
    public static int colorLine = 0xFF000000;
    public static int colorFill = 0xFF000000;
    public static int pixelSize = 1;

    public boolean IS_PEN = false;
    public boolean IS_LINE = false;
    public boolean IS_ROUND = false;
    public boolean IS_RECT = false;
    public boolean IS_BEZIER = false;
    public boolean IS_POLYGON = false;

    public boolean IS_ZATRAVKA = false;

    private static Controller instance;


    public Controller(Context context) {
        this.context = context;
        instance = this;
        drawer = Drawer.getInstance();
    }
    public static Controller getInstance(){
        return instance;
    }

    public void setPenTool(){
        clearTools();
        IS_PEN = true;
    }
    public void setLineTool(){
        clearTools();
        IS_LINE = true;
    }
    public void setRoundTool(){
        clearTools();
        IS_ROUND = true;
    }
    public void setRectTool(){
        clearTools();
        IS_RECT = true;
    }
    public void setBezierTool(){
        clearTools();
        IS_BEZIER = true;
    }
    public void setPolygonTool(){
        clearTools();
        IS_POLYGON = true;
    }

    public void setZatravka(){
        clearTools();
        IS_ZATRAVKA = true;
    }

    public void clearTools(){
        IS_PEN = false;
        IS_LINE = false;
        IS_ROUND = false;
        IS_RECT = false;
        IS_BEZIER = false;
        IS_POLYGON = false;
        IS_ZATRAVKA = false;
    }



    public void clearFild(){
        DrawThread.pixels.clear();
        DrawThread.figures.clear();
        DrawThread.flag = false;
    }

}
