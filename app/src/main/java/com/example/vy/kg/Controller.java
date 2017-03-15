package com.example.vy.kg;

import android.content.Context;
import android.widget.Toast;

import com.example.vy.kg.graphics.Drawer;

public class Controller {
    private Context context;
    private Drawer drawer;

    private boolean IS_PEN = false;
    private boolean IS_LINE = false;
    private boolean IS_ROUND = false;
    private boolean IS_RECT = false;
    private boolean IS_BEZIER = false;

    private static Controller instance;


    public Controller(Context context) {
        this.context = context;
        instance = this;
        drawer = Drawer.getInstance();
    }
    public static Controller getInstance(){
        return instance;
    }

    public boolean IS_BEZIER() {
        return IS_BEZIER;
    }
    public boolean IS_RECT() {
        return IS_RECT;
    }
    public boolean IS_ROUND() {
        return IS_ROUND;
    }
    public boolean IS_LINE() {
        return IS_LINE;
    }
    public boolean IS_PEN() {
        return IS_PEN;
    }

    public void setPenTool(){
        IS_PEN = true;
        IS_LINE = false;
        IS_ROUND = false;
        IS_RECT = false;
        IS_BEZIER = false;
    }
    public void setLineTool(){
        IS_PEN = false;
        IS_LINE = true;
        IS_ROUND = false;
        IS_RECT = false;
        IS_BEZIER = false;
    }
    public void setRoundTool(){
        IS_PEN = false;
        IS_LINE = false;
        IS_ROUND = true;
        IS_RECT = false;
        IS_BEZIER = false;
    }
    public void setRectTool(){
        IS_RECT = true;
        IS_PEN = false;
        IS_LINE = false;
        IS_ROUND = false;
        IS_BEZIER = false;
    }
    public void setBezierTool(){
        IS_BEZIER = true;
        IS_RECT = false;
        IS_PEN = false;
        IS_LINE = false;
        IS_ROUND = false;
    }
    public void clearTools(){
        IS_PEN = false;
        IS_LINE = false;
        IS_ROUND = false;
        IS_RECT = false;
        IS_BEZIER = false;
    }

    public void clearFild(){
        DrawThread.pixels.clear();
        DrawThread.figures.clear();
        DrawThread.flag = false;
    }

}
