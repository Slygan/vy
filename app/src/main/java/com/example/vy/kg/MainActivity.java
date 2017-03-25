package com.example.vy.kg;

import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
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
import android.widget.Toast;

import com.example.vy.kg.file.FileReadBMP;
import com.example.vy.kg.file.FileReader;
import com.example.vy.kg.file.FileWriter;
import com.example.vy.kg.file.parser.OBJParser;
import com.example.vy.kg.file.parser.XMLParser;
import com.example.vy.kg.graphics.Drawer;
import com.skydoves.colorpickerview.ColorPickerView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private LinearLayout ll;
    private ColorPickerView colorPickerLine;
    private ColorPickerView colorPickerFill;

    private Context context;
    private Drawer drawer;
    private Controller controller;

    final String LOG_TAG = "VY_LOGS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Log.d(LOG_TAG,"START");


        context = this;
        drawer = new Drawer(context);
        controller = new Controller(context);

        ll = (LinearLayout) findViewById(R.id.surface);
        ll.addView(new MySurfaceView(this));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initChangePixelFAB();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    private void initChangePixelFAB(){
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
                        Controller.pixelSize = progress;
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                });


                colorPickerLine = (ColorPickerView) linearlayout.findViewById(R.id.colorPickerLine);
                colorPickerFill = (ColorPickerView) linearlayout.findViewById(R.id.colorPickerFill);

                final View colorLineRect = linearlayout.findViewById(R.id.colorLineRect);
                final View colorFillRect = linearlayout.findViewById(R.id.colorFillRect);

                colorPickerLine.setColorListener(new ColorPickerView.ColorListener() {
                    @Override
                    public void onColorSelected(int color) {
                        Controller.colorLine = color;
                        colorLineRect.setBackgroundColor(color);
                    }
                });
                colorPickerFill.setColorListener(new ColorPickerView.ColorListener() {
                    @Override
                    public void onColorSelected(int color) {
                        Controller.colorFill = color;
                        colorFillRect.setBackgroundColor(color);
                    }
                });


                ratingdialog.setPositiveButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        /*.setNegativeButton("Отмена",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                })*/;

                ratingdialog.create();
                ratingdialog.show();

                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
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
            controller.clearFild();
        } else if (id == R.id.nav_pen) {
            controller.setPenTool();
        } else if (id == R.id.nav_line) {
            controller.setLineTool();
        } else if (id == R.id.nav_round) {
            controller.setRoundTool();
        } else if (id == R.id.nav_rect) {
            controller.setRectTool();
        } else if (id == R.id.nav_polygon) {
            controller.setPolygonTool();
        } else if (id == R.id.nav_model) {
            DrawThread.figures.addAll(new OBJParser().parse(FileReader.readFile(context.getResources().openRawResource(R.raw.african_head))));
        } else if (id == R.id.nav_file){
            DrawThread.figures.addAll(new XMLParser().parse(FileReader.readFile(context.getResources().openRawResource(R.raw.figures))));
        } else if (id == R.id.nav_bezier){
            controller.setBezierTool();
        } else if (id == R.id.nav_mosaic){
            controller.clearTools();
            DrawThread.pixels.addAll(drawer.getMozaik());
        } else if(id == R.id.nav_zatrav){
            controller.setZatravka();
        } if (id == R.id.nav_save){
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
