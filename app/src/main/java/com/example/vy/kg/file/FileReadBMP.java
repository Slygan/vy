package com.example.vy.kg.file;


import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.*;

/**
 * Created by oleg on 04.03.2017.
 */

public class FileReadBMP{
    final String LOG_TAG = "file";
    final String DIR_SD = "DRAW";
    final String FILENAME_SD = "test.txt";

    java.io.File _sdPath;

    public FileReadBMP() {
        // проверяем доступность SD
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.d(LOG_TAG, "SD-карта не доступна: " + Environment.getExternalStorageState());
            return;
        }
        // получаем путь к SD
        _sdPath = Environment.getExternalStorageDirectory();
        // добавляем свой каталог к пути
        _sdPath = new java.io.File(_sdPath.getAbsolutePath() + "/" + DIR_SD);
        // создаем каталог
        if (_sdPath.mkdirs()) {
            Log.d(LOG_TAG, "Создал: " + _sdPath.getAbsolutePath());
        } else {
            Log.d(LOG_TAG, "не создал: " + _sdPath.getAbsolutePath());
        }
    }

    private java.io.File openFile(String fileName) throws IOException {
        java.io.File sdFile = new java.io.File(_sdPath, fileName);
        if (sdFile.exists()) {
            sdFile.createNewFile();
        }
        return sdFile;
    }

    public Bitmap readBMP24(String fileName){
        // формируем объект File, который содержит путь к файлу
        Bitmap bmp = null;
        try {

            File sdFile = openFile(fileName);
            ReadBMP fbr = new ReadBMP(sdFile);
            bmp = fbr.read();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmp;
    }
}