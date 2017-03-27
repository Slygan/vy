package com.example.vy.kg;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vy.kg.graphics.Drawer;
import com.example.vy.kg.graphics.figures.FigurePolygoneN;

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
    public boolean IS_ZATRAVKA_POLYGON = false;
    public boolean IS_FILL_RECT = false;

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

    public void setZatravkaPolygon(final View linearLayout){
        clearTools();

        final AlertDialog.Builder ratingdialog = new AlertDialog.Builder(context);
        ratingdialog.setView(linearLayout);

        ratingdialog.setPositiveButton("ОК",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        EditText editText = (EditText) linearLayout.findViewById(R.id.chooseNodeNumber);
                        FigurePolygoneN.nodesNum = Integer.valueOf(editText.getText().toString());
                        Toast.makeText(context,editText.getText().toString(),Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });

        ratingdialog.create();
        ratingdialog.show();

        IS_ZATRAVKA_POLYGON = true;
    }

    public void setFillRect(){
        clearTools();
        IS_FILL_RECT = true;
    }

    public void clearTools(){
        IS_PEN = false;
        IS_LINE = false;
        IS_ROUND = false;
        IS_RECT = false;
        IS_BEZIER = false;
        IS_POLYGON = false;
        IS_ZATRAVKA = false;
        IS_ZATRAVKA_POLYGON = false;
        IS_FILL_RECT = false;
    }



    public void clearFild(){
        DrawThread.pixels.clear();
        DrawThread.figures.clear();
        DrawThread.flag = false;
    }

}
