package com.example.esoukhanov.group_project_organizer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tool_bar_init();
        //set a toolbar in activity window
        setSupportActionBar(toolbar);
    }

    public void tool_bar_init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
    }

    @Override
    // Initiates Menu XML file (defines menu view layout)
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.activity_tracking:
                Intent first = new Intent(this, Activity_tracking.class);
                startActivity(first);
            case R.id.food_nutrition_information_tracker:
                Intent second = new Intent(this, Food_nutrition_information_tracker.class);
                startActivity(second);
        }
        return true;

    }

    }
