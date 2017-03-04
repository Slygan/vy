package com.example.vy.kg;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.example.vy.kg.file.FileReadBMP;
import com.example.vy.kg.file.FileReader;
import com.example.vy.kg.file.FileWriter;
import com.example.vy.kg.file.WriteBMP;
import com.example.vy.kg.file.parser.OBJParser;
import com.example.vy.kg.file.parser.XMLParser;
import com.example.vy.kg.graphics.Drawer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private LinearLayout ll;

    private Context context;
    private Drawer drawer;

    final String LOG_TAG = "VY_LOGS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Log.d(LOG_TAG,"START");


        context = this;
        drawer = new Drawer(context);

        ll = (LinearLayout) findViewById(R.id.surface);
        ll.addView(new MySurfaceView(this));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final AlertDialog.Builder ratingdialog = new AlertDialog.Builder(context);

                View linearlayout = getLayoutInflater().inflate(R.layout.change_pixel, null);
                ratingdialog.setView(linearlayout);

                SeekBar size = (SeekBar) linearlayout.findViewById(R.id.seekBar);

                size.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        DrawThread.pixelSize = progress;
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });

                ratingdialog.setPositiveButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("Отмена",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                ratingdialog.create();
                ratingdialog.show();

                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item){

        int id = item.getItemId();

        if (id == R.id.nav_clear) {
            DrawThread.pixels.clear();
            DrawThread.figures.clear();
            DrawThread.flag = false;
        } else if (id == R.id.nav_pen) {
            MySurfaceView.IS_PEN = true;
            MySurfaceView.IS_LINE = false;
            MySurfaceView.IS_ROUND = false;
            MySurfaceView.IS_RECT = false;
            MySurfaceView.IS_BEZIER = false;
        } else if (id == R.id.nav_line) {
            MySurfaceView.IS_PEN = false;
            MySurfaceView.IS_LINE = true;
            MySurfaceView.IS_ROUND = false;
            MySurfaceView.IS_RECT = false;
            MySurfaceView.IS_BEZIER = false;
        } else if (id == R.id.nav_round) {
            MySurfaceView.IS_PEN = false;
            MySurfaceView.IS_LINE = false;
            MySurfaceView.IS_ROUND = true;
            MySurfaceView.IS_RECT = false;
            MySurfaceView.IS_BEZIER = false;
        } else if (id == R.id.nav_rect) {
            MySurfaceView.IS_RECT = true;
            MySurfaceView.IS_PEN = false;
            MySurfaceView.IS_LINE = false;
            MySurfaceView.IS_ROUND = false;
            MySurfaceView.IS_BEZIER = false;
        } else if (id == R.id.nav_model) {
            DrawThread.figures.addAll(new OBJParser().parse(FileReader.readFile(context.getResources().openRawResource(R.raw.african_head))));
        } else if (id == R.id.nav_file){
            DrawThread.figures.addAll(new XMLParser().parse(FileReader.readFile(context.getResources().openRawResource(R.raw.figures))));
        } else if (id == R.id.nav_bezier){
            MySurfaceView.IS_BEZIER = true;
            MySurfaceView.IS_RECT = false;
            MySurfaceView.IS_PEN = false;
            MySurfaceView.IS_LINE = false;
            MySurfaceView.IS_ROUND = false;
        } else if (id == R.id.nav_mosaic){
            MySurfaceView.IS_RECT = false;
            MySurfaceView.IS_PEN = false;
            MySurfaceView.IS_LINE = false;
            MySurfaceView.IS_ROUND = false;
            MySurfaceView.IS_BEZIER = false;
            DrawThread.figures.clear();
            DrawThread.pixels.clear();
            DrawThread.pixels.addAll(drawer.getMozaik());
        } else if (id == R.id.nav_save){
            DrawThread.saveBMP();
            FileWriter fl = new FileWriter();
            fl.writeBMP24("test",DrawThread.b);
            DrawThread.c.drawColor(Color.WHITE);
        } else if (id == R.id.nav_open){
            FileReadBMP frb = new FileReadBMP();
            DrawThread.c.drawBitmap(frb.readBMP24("test"),0,0,DrawThread.paint);
            DrawThread.flag = true;
        }

        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
