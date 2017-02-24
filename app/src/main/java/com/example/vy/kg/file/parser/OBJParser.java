package com.example.vy.kg.file.parser;

import com.example.vy.kg.figures.Figure;
import com.example.vy.kg.figures.FigureLine;

import java.util.ArrayList;

/**
 * Created by vy on 2/23/17.
 */

public class OBJParser implements Parser{
    private final int width = 700;
    private final int height = 1000;

    @Override
    public ArrayList<Figure> parse(ArrayList<String> list) {

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

                    FigureLine tempLine1 = new FigureLine(xA,yA,xB,yB);
                    tempLine1.buildParamLine(1);
                    //DrawThread.figures.add(tempLine1);

                    FigureLine tempLine2 = new FigureLine(xA,yA,xC,yC);
                    tempLine2.buildParamLine(1);
                    //DrawThread.figures.add(tempLine2);

                    FigureLine tempLine3 = new FigureLine(xC,yC,xB,yB);
                    tempLine3.buildParamLine(1);
                    //DrawThread.figures.add(tempLine3);

                }
            }
        }

        return figures;
    }
}
