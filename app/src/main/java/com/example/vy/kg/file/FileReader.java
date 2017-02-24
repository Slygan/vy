package com.example.vy.kg.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by vy on 2/23/17.
 */

public class FileReader extends File{

    public static ArrayList<String> readFile(InputStream stream) {

        ArrayList<String> list = new ArrayList<>();

        InputStream inputStream = stream;
        if (inputStream != null) {
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isr);
            String line;
            StringBuilder builder = new StringBuilder();

            try {
                while ((line = reader.readLine()) != null) {
                    list.add(line);
                    builder.append(line + "\n");
                }
            } catch (IOException e) {
                //Toast.makeText(context,"Exception 2",Toast.LENGTH_SHORT).show();
            }

            try {
                inputStream.close();
            } catch (IOException e) {
                //Toast.makeText(context,"Exception 3",Toast.LENGTH_SHORT).show();
            }
        }
        return list;
    }

}
