package com.example.vy.trycanvas;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import com.example.vy.trycanvas.fragments.FragmentChooseNodeNumDialog;
import com.example.vy.trycanvas.fragments.FragmentField;
import com.example.vy.trycanvas.fragments.FragmentMenuFABDialog;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static Context context;
    private Controller controller;
    final String LOG_TAG = "VY_LOGS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(LOG_TAG,"START");

        context = this;
        controller = new Controller(context);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.surface, new FragmentField()).commit();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new FragmentMenuFABDialog().show(getSupportFragmentManager(),"custom");
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



    }


    /**
    * При выборе определенного пункта в конктроллере устанавливается
     * текущаю операция, далее окнтроллер уже сам знает какое действие
     * в случае чего нужно делать
    * */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item){

        int id = item.getItemId();

        switch (id){
            case R.id.nav_clear:
                controller.clear();
                break;
            case R.id.nav_mosaic:
                controller.setMozaikOperation();
                break;
            case R.id.nav_line:
                controller.setLineOperation();
                break;
            case R.id.nav_round:
                controller.setRoundOperation();
                break;
            case R.id.nav_rect:
                controller.setRectangleOperation();
                break;
            case R.id.nav_polygon:
                controller.setPolygon3Operation();
                break;
            case R.id.nav_zatrav:
                controller.setZatravOperation();
                break;
            case R.id.nav_pen:
                controller.setPenOperation();
                break;
            case R.id.nav_fill_rect:
                controller.setFillRectOperation();
                break;
            case R.id.nav_zatrav_polygon:
                new FragmentChooseNodeNumDialog().show(getSupportFragmentManager(),"custom");
                controller.setFillPolygonNOperation();
                break;
            case R.id.nav_bezier:
                controller.setBezierOperation();
                break;
            case R.id.nav_model:
                controller.modelHeadOperation();
                break;
        }

        /*
         if (id == R.id.nav_save){
            DrawThread.saveBMP();
            FileWriter fl = new FileWriter();
            fl.writeBMP24("test",DrawThread.b);
            DrawThread.c.drawColor(Color.WHITE);
        } else if (id == R.id.nav_open){
            FileReadBMP frb = new FileReadBMP();
            DrawThread.c.drawBitmap(frb.readBMP24("test"),0,0,DrawThread.paint);
            DrawThread.flag = true;
        }*/

        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
