package com.example.vy.kg.file.parser;

import android.util.Log;

import com.example.vy.kg.figures.Figure;
import com.example.vy.kg.figures.FigureLine;
import com.example.vy.kg.figures.FigureRect;
import com.example.vy.kg.figures.FigureRound;

import java.util.ArrayList;

/**
 * Created by vy on 2/23/17.
 */

public class XMLParser implements Parser {


    final String LOG_TAG = "VY_LOGS";

    public ArrayList<Figure> parse(ArrayList<String> list) {
        ArrayList<Figure> figures = new ArrayList<>();

        for(String line: list){
            String [] masLine = line.split(" ");

            switch (masLine[0]){
                case "rect":
                    Log.d(LOG_TAG,String.valueOf(Integer.valueOf(masLine[1])+Integer.valueOf(masLine[2])+Integer.valueOf(masLine[3])+Integer.valueOf(masLine[4])));
                    FigureRect rect = new FigureRect(Integer.valueOf(masLine[1]),Integer.valueOf(masLine[2]),Integer.valueOf(masLine[3]),Integer.valueOf(masLine[4]));
                    rect.buildRect();
                    figures.add(rect);
                    break;
                case "line":
                    FigureLine fLine = new FigureLine(Integer.valueOf(masLine[1]),Integer.valueOf(masLine[2]),Integer.valueOf(masLine[3]),Integer.valueOf(masLine[4]));
                    fLine.buildParamLine(1);
                    figures.add(fLine);
                    break;
                case "round":
                    FigureRound round = new FigureRound(Integer.valueOf(masLine[1]),Integer.valueOf(masLine[2]),Integer.valueOf(masLine[3]));
                    round.BrazAlgCircle(1);
                    figures.add(round);
                    break;
            }
        }

        return figures;
    }



}
