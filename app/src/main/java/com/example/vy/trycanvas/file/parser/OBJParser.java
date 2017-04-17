package com.example.vy.trycanvas.file.parser;


import android.graphics.Bitmap;
import android.graphics.Color;

import com.example.vy.trycanvas.Controller;
import com.example.vy.trycanvas.graphics.Drawer;
import com.example.vy.trycanvas.graphics.figures.Figure;

import java.util.ArrayList;
import java.util.Random;

public class OBJParser {

    public ArrayList<Figure> parse(ArrayList<String> list, int width, int height, Bitmap bitmap) {

        int color = Controller.colorLine;

        ArrayList<Figure> figures = new ArrayList<>();

        for(String str : list){
            if(str!=null && str.compareTo("")!=0){
                if(str.charAt(0) == 'f'){
                    String[] tempStr = str.split(" ");
                    Integer lineA = Integer.valueOf(tempStr[1].split("/")[0])-1;
                    Integer lineB = Integer.valueOf(tempStr[2].split("/")[0])-1;
                    Integer lineC = Integer.valueOf(tempStr[3].split("/")[0])-1;

                    //Log.d(LOG_TAG,"Треугольник " + lineA + "\t" + lineB + "\t" + lineC);

                    int xA = (int)((Double.valueOf(list.get(lineA).split(" ")[1])+1.)*width/2);
                    int yA = (int)((Double.valueOf(list.get(lineA).split(" ")[2])*-1+1.)*height/2);

                    int xB = (int)((Double.valueOf(list.get(lineB).split(" ")[1])+1.)*width/2);
                    int yB = (int)((Double.valueOf(list.get(lineB).split(" ")[2])*-1+1.)*height/2);

                    int xC = (int)((Double.valueOf(list.get(lineC).split(" ")[1])+1.)*width/2);
                    int yC = (int)((Double.valueOf(list.get(lineC).split(" ")[2])*-1+1.)*height/2);


                    figures.add(Drawer.getLine(xA,yA,xB,yB,color,bitmap));
                    figures.add(Drawer.getLine(xA,yA,xC,yC,color,bitmap));
                    figures.add(Drawer.getLine(xC,yC,xB,yB,color,bitmap));

                }
            }
        }

        return figures;
    }

    public ArrayList<Figure> parseStaticFillStaticColor(ArrayList<String> list, int width, int height, Bitmap bitmap, int colorFill) {

        int colorLine = Controller.colorLine;

        ArrayList<Figure> figures = new ArrayList<>();

        for(String str : list){
            if(str!=null && str.compareTo("")!=0){
                if(str.charAt(0) == 'f'){
                    String[] tempStr = str.split(" ");
                    Integer lineA = Integer.valueOf(tempStr[1].split("/")[0])-1;
                    Integer lineB = Integer.valueOf(tempStr[2].split("/")[0])-1;
                    Integer lineC = Integer.valueOf(tempStr[3].split("/")[0])-1;

                    //Log.d(LOG_TAG,"Треугольник " + lineA + "\t" + lineB + "\t" + lineC);

                    int xA = (int)((Double.valueOf(list.get(lineA).split(" ")[1])+1.)*width/2);
                    int yA = (int)((Double.valueOf(list.get(lineA).split(" ")[2])*-1+1.)*height/2);

                    int xB = (int)((Double.valueOf(list.get(lineB).split(" ")[1])+1.)*width/2);
                    int yB = (int)((Double.valueOf(list.get(lineB).split(" ")[2])*-1+1.)*height/2);

                    int xC = (int)((Double.valueOf(list.get(lineC).split(" ")[1])+1.)*width/2);
                    int yC = (int)((Double.valueOf(list.get(lineC).split(" ")[2])*-1+1.)*height/2);




                    int [] points = {xA,yA,xB,yB,xC,yC};

                    Drawer.getFillPolygonNStatic(points,bitmap,colorLine,colorFill);
                    figures.add(Drawer.getLine(xA,yA,xB,yB,colorLine,bitmap));
                    figures.add(Drawer.getLine(xA,yA,xC,yC,colorLine,bitmap));
                    figures.add(Drawer.getLine(xC,yC,xB,yB,colorLine,bitmap));
                }
            }
        }

        return figures;
    }

    public ArrayList<Figure> parseStaticFillRandomColor(ArrayList<String> list, int width, int height, Bitmap bitmap) {

        int colorLine = Controller.colorLine;

        ArrayList<Figure> figures = new ArrayList<>();
        Random random = new Random();
        for(String str : list){
            if(str!=null && str.compareTo("")!=0){
                if(str.charAt(0) == 'f'){
                    String[] tempStr = str.split(" ");
                    Integer lineA = Integer.valueOf(tempStr[1].split("/")[0])-1;
                    Integer lineB = Integer.valueOf(tempStr[2].split("/")[0])-1;
                    Integer lineC = Integer.valueOf(tempStr[3].split("/")[0])-1;

                    //Log.d(LOG_TAG,"Треугольник " + lineA + "\t" + lineB + "\t" + lineC);

                    int xA = (int)((Double.valueOf(list.get(lineA).split(" ")[1])+1.)*width/2);
                    int yA = (int)((Double.valueOf(list.get(lineA).split(" ")[2])*-1+1.)*height/2);

                    int xB = (int)((Double.valueOf(list.get(lineB).split(" ")[1])+1.)*width/2);
                    int yB = (int)((Double.valueOf(list.get(lineB).split(" ")[2])*-1+1.)*height/2);

                    int xC = (int)((Double.valueOf(list.get(lineC).split(" ")[1])+1.)*width/2);
                    int yC = (int)((Double.valueOf(list.get(lineC).split(" ")[2])*-1+1.)*height/2);


                    //figures.add(Drawer.getLine(xA,yA,xB,yB,colorLine,bitmap));
                    //figures.add(Drawer.getLine(xA,yA,xC,yC,colorLine,bitmap));
                    //figures.add(Drawer.getLine(xC,yC,xB,yB,colorLine,bitmap));

                    int [] points = {xA,yA,xB,yB,xC,yC};

                    int color = random.nextInt() % Color.rgb(125,125,125) + Color.rgb(125,125,125);
                    Drawer.getFillPolygonNStatic(points,bitmap,colorLine,color);

                }
            }
        }

        return figures;
    }

}
