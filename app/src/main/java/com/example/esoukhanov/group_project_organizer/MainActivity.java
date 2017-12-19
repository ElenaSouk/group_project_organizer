package com.example.esoukhanov.group_project_organizer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.design.widget.NavigationView;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;

    DrawerLayout drawerLayout;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tool_bar_init();

        //set a toolbar in activity window
        //setSupportActionBar(toolbar);// setting toolbar as an action bar

    }

    public void tool_bar_init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setNavigationIcon(R.mipmap.ic_tool_nav);
        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        drawerLayout.openDrawer(Gravity.LEFT);
                    }
                });
        setNavigationDrawer();
    }

/*    @Override
    // Initiates Menu XML file (defines menu_items view layout)
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_items, menu);
        return true;
    }*/

    public void setNavigationDrawer() {
        // initiating a DrawerLayout
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        // initiating a NavigationView
        NavigationView navigation_view = (NavigationView) findViewById(R.id.navigation); // initiate a Navigation View
        // implementing setNavigationItemSelectedListener event on NavigationView
        navigation_view.setNavigationItemSelectedListener
                (new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    //the first constructor argument of Intent should be Context
                    //Line 19 - final Context context = this;
                    case R.id.activity_tracking:
                        Toast.makeText(MainActivity.this, "Activity tracking is selected", Toast.LENGTH_SHORT).show();
                       // Intent first = new Intent(context, Activity_tracking.class);
                        //startActivity(first);
                        break;
                    case R.id.food_nutrition_information_tracker:
                        Toast.makeText(MainActivity.this, "Food nutrition information tracker is selected", Toast.LENGTH_SHORT).show();
                        //Intent second = new Intent(context, Food_nutrition_information_tracker.class);
                        //startActivity(second);
                        break;
                    case R.id.house_thermostat:
                        //Intent third = new Intent(context, House_thermostat.class);
                        //startActivity(third);
                        Toast.makeText(MainActivity.this, "House thermostat is selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.automobile:
                        Intent fourth = new Intent(context, Automobile.class);
                        startActivity(fourth);
                        Toast.makeText(MainActivity.this, "Automobile is selected", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }
}