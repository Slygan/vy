package com.example.vy.trycanvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.MotionEvent;
import com.example.vy.trycanvas.file.FileReader;
import com.example.vy.trycanvas.file.parser.OBJParser;
import com.example.vy.trycanvas.graphics.Drawer;

public class Controller {
    private Context context;

    public static int colorLine = 0xFF000000;
    public static int colorFill = 0xFF000000;
    public static int pixelSize = 1;
    public static int nodesNum = 3;

    /**
    * Текущая операция
     * -1 - нет операции
     * 0 - карандаш
     * 1 - линия
     * 2 - круг
     * 3 - прямоугольник
     * 4 - кривая Безье
     * 5 - полигон 3
     * 6 - затравка замкнутой фигуры
     * 7 - закрашенный прямоугольник
     * 8 - закрашенный многоугольник (градиент)
     * 9 - эллипс
     * 10 - треугольник
     * 11 - треугольник затравочный1
     * 12 - закрашенный эллипс
     * 13 - эрмит
     *
     *
     *
     *
     * 18 - закрашенный многоугольник (статич.)
    * */
    private int currentOperation = -1;
    private Operation operation;
    private Field field;

    private static Controller instance;

    public Controller(Context context) {
        this.context = context;
        operation = new Operation();
        instance = this;
    }
    public static Controller getInstance(){
        return instance;
    }

    /*
    * TODO: переделать как-нибудь (вызывается в Field()
    * */
    public void initField(Field field){
        this.field = field;
    }

    public void clear(){
        operation.operationClear();
        currentOperation = -1;
        field.createNewBitmap();
        field.getBitmapMotion().eraseColor(Color.WHITE);
        field.invalidate();
    }

    public void setMozaikOperation(){
        Drawer.getMozaik(field.getBitmap(), pixelSize);
    }
    public void setPenOperation(){
        currentOperation = 0;
        operation.operationClear();
    }
    public void setLineOperation(){
        currentOperation = 1;
        operation.operationClear();
    }
    public void setRoundOperation(){
        currentOperation = 2;
        operation.operationClear();
    }
    public void setRectangleOperation(){
        currentOperation = 3;
        operation.operationClear();
    }
    public void setBezierOperation(){
        currentOperation = 4;
        operation.operationClear();
    }
    public void setPolygon3Operation(){
        currentOperation = 5;
        operation.operationClear();
    }
    public void setZatravOperation(){
        currentOperation = 6;
        operation.operationClear();
    }
    public void setFillRectOperation(){
        currentOperation = 7;
        operation.operationClear();
    }
    public void setFillPolygonNGradOperation(){
        currentOperation = 8;
        operation.operationClear();
    }
    public void setEllipseOperation(){
        currentOperation = 9;
        operation.operationClear();
    }
    public void setTriangle(){
        currentOperation = 10;
        operation.operationClear();
    }
    public void setTriangleFill(){
        currentOperation = 11;
        operation.operationClear();
    }
    public void setEllipseFill(){
        currentOperation = 12;
        operation.operationClear();
    }
    public void setErmit(){
        currentOperation = 13;
        operation.operationClear();
    }
    public void setNURBS(){
        currentOperation = 14;
        operation.operationClear();
    }
    public void setBspline(){
        currentOperation = 15;
        operation.operationClear();
    }
    public void setSave(){
        currentOperation = 16;
        operation.operationClear();
    }
    public void setOpen(){
        currentOperation = 17;
        operation.operationClear();
    }
    public void setFillPolygonNStaticOperation(){
        currentOperation = 18;
        operation.operationClear();
    }


    public void modelHeadOperation(){
        new OBJParser().parse(FileReader.readFile(context.getResources().openRawResource(R.raw.african_head)),
                field.getBitmap().getWidth()/2,
                field.getBitmap().getHeight()/2,
                field.getBitmap());
    }
    public void modelHeadStaticOperation(){
        new OBJParser().parseStaticFillStaticColor(FileReader.readFile(context.getResources().openRawResource(R.raw.african_head)),
                field.getBitmap().getWidth()/2,
                field.getBitmap().getHeight()/2,
                field.getBitmap(),colorFill);
    }
    public void modelHeadRandomOperation(){
        new OBJParser().parseStaticFillRandomColor(FileReader.readFile(context.getResources().openRawResource(R.raw.african_head)),
                field.getBitmap().getWidth()/2,
                field.getBitmap().getHeight()/2,
                field.getBitmap());
    }


    /**
    * Смотрим текущую операцию и передаем работу соотвутствующему методу
    * */
    public void operate(Bitmap bitmap, Bitmap bitmapMotion, MotionEvent event){
        switch (currentOperation){
            case 0:
                operation.operationPen(event, colorLine, pixelSize, bitmap);
                break;
            case 1:
                operation.operationLine(bitmap, bitmapMotion, event, colorLine, field);
                break;
            case 2:
                operation.operationRound(bitmap, bitmapMotion, event, colorLine, field);
                break;
            case 3:
                operation.operationRect(bitmap, bitmapMotion, event, colorLine, field);
                break;
            case 4:
                operation.operationBezier(bitmap, bitmapMotion, event, colorLine, field);
                break;
            case 5:
                operation.operationPolygon3(bitmap, bitmapMotion, event, colorLine, colorFill, field);
                break;
            case 6:
                operation.operationZatrav(bitmap, event, colorFill);
                break;
            case 7:
                operation.operationFillRect(bitmap, bitmapMotion, event, colorLine, colorFill, field);
                break;
            case 8:
                operation.operationFillPolygonNGrad(bitmap, bitmapMotion, event, colorLine, colorFill, field, nodesNum);
                break;
            case 9:
                operation.operationEllipse(bitmap,bitmapMotion,event,colorLine,field);
                break;
            case 10:
                operation.operationTriangle(bitmap,bitmapMotion,event,colorLine,field);
                break;
            case 11:
                operation.operationTriangleFill(bitmap,bitmapMotion,event,colorLine,colorFill,field);
                break;
            case 12:
                operation.operationEllipseFill(bitmap,bitmapMotion,event,colorLine,colorFill,field);
                break;
            case 13:
                operation.operationErmit(bitmap,bitmapMotion,event,colorLine,field);
                break;
            case 14:
                operation.operationNURBS(bitmap,bitmapMotion,event,colorLine,field);
                break;
            case 15:
                operation.operationBSpline(bitmap,bitmapMotion,event,colorLine,field);
                break;
            case 16:
                operation.operationSave(bitmap,bitmapMotion,event,colorLine,field);
                break;
            case 17:
                operation.operationOpen(field);
                break;
            case 18:
                operation.operationFillPolygonNStatic(bitmap,bitmapMotion,event,colorLine,colorFill,field,nodesNum);
                break;
        }
    }



}

