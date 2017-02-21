package com.example.vy.kg;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.example.vy.kg.figures.FigureLine;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class FileInput {

    static ArrayList<String> list;

    final String LOG_TAG = "VY_LOGS";

    int width = 700;
    int height = 1000;

    public FileInput(Context context){

        list = new ArrayList<>();

        openFile(context);

        for(String str : list){
            if(str!=null && str.compareTo("")!=0){
                if(str.charAt(0) == 'f'){
                    String[] tempStr = str.split(" ");
                    Integer lineA = Integer.valueOf(tempStr[1].split("/")[0])-1;
                    Integer lineB = Integer.valueOf(tempStr[2].split("/")[0])-1;
                    Integer lineC = Integer.valueOf(tempStr[3].split("/")[0])-1;

                    Log.d(LOG_TAG,"Треугольник " + lineA + "\t" + lineB + "\t" + lineC);

                    int xA = (int)((Double.valueOf(list.get(lineA).split(" ")[1])+1.)*width/2);
                    int yA = (int)((Double.valueOf(list.get(lineA).split(" ")[2])*-1+1.)*height/2);

                    int xB = (int)((Double.valueOf(list.get(lineB).split(" ")[1])+1.)*width/2);
                    int yB = (int)((Double.valueOf(list.get(lineB).split(" ")[2])*-1+1.)*height/2);

                    int xC = (int)((Double.valueOf(list.get(lineC).split(" ")[1])+1.)*width/2);
                    int yC = (int)((Double.valueOf(list.get(lineC).split(" ")[2])*-1+1.)*height/2);

                    FigureLine tempLine1 = new FigureLine(xA,yA,xB,yB);
                    tempLine1.buildParamLine(1);
                    DrawThread.figures.add(tempLine1);

                    FigureLine tempLine2 = new FigureLine(xA,yA,xC,yC);
                    tempLine2.buildParamLine(1);
                    DrawThread.figures.add(tempLine2);

                    FigureLine tempLine3 = new FigureLine(xC,yC,xB,yB);
                    tempLine3.buildParamLine(1);
                    DrawThread.figures.add(tempLine3);

                }
            }
        }
    }


    private void openFile(Context context) {

        InputStream inputStream = context.getResources().openRawResource(R.raw.african_head);
        Log.d(LOG_TAG,"Открыли файл");
        if (inputStream != null) {
                InputStreamReader isr = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(isr);
                String line;
                StringBuilder builder = new StringBuilder();
//terracotta army
            try {
                while ((line = reader.readLine()) != null) {
                    list.add(line);
                    builder.append(line + "\n");
                }
            } catch (IOException e) {
                Toast.makeText(context,"Exception 2",Toast.LENGTH_SHORT).show();
            }

            try {
                inputStream.close();
            } catch (IOException e) {
                Toast.makeText(context,"Exception 3",Toast.LENGTH_SHORT).show();
            }
        }

    }

}
