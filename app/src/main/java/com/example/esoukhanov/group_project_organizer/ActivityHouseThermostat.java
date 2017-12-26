package com.example.esoukhanov.group_project_organizer;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.view.ActionMode;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/*
 Some ideas I took from this resource:
http://www.vogella.com/tutorials/AndroidListView/article.html
 */
public class ActivityHouseThermostat extends ListActivity implements OnItemClickListener {
    private ArrayAdapter<String> listAdapter;
    //PopupWindow popupWindow;
    final Context context = this;
    private SQLiteDatabase db;  //a SQLiteDatabase object
    private ListView listView;
    private ArrayAdapter<ThermoItem> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ThermoListAdapter(this, getModel());
        setListAdapter(adapter);
        listView = getListView();
        // Setup listener for clicks
        listView.setOnItemClickListener(this);
    }

    public void refreshList() {
        adapter = new ThermoListAdapter(this, getModel());
        setListAdapter(adapter);
        listView = getListView();
        // Setup listener for clicks
        listView.setOnItemClickListener(this);
    }
    private List<ThermoItem> getModel() {
        ThermoDatabaseHelper tdb = new ThermoDatabaseHelper(this);
        List<ThermoItem> list = new ArrayList<>();

        db = tdb.getWritableDatabase(); //open it for both read and write
        Cursor cursor = db.query(ThermoDatabaseHelper.TABLE_NAME,
                new String[]{ThermoDatabaseHelper.ID,
                        ThermoDatabaseHelper.COL_WEEK,
                        ThermoDatabaseHelper.COL_MON,
                        ThermoDatabaseHelper.COL_AFTER,
                        ThermoDatabaseHelper.COL_EVE},
                null,null,null,null,null,null);
        // If our table is empty, we initialize it for the first time with default values
        if(cursor.getCount() < 1) {

            //Insert first row
            ContentValues cValue = new ContentValues();
            cValue.put(ThermoDatabaseHelper.COL_WEEK, R.string.Monday);
            cValue.put(ThermoDatabaseHelper.COL_MON, 19.5);
            cValue.put(ThermoDatabaseHelper.COL_AFTER, 20.5);
            cValue.put(ThermoDatabaseHelper.COL_EVE, 20.0);
            db.insert(ThermoDatabaseHelper.TABLE_NAME, "NULL", cValue);

            //second row
            cValue = new ContentValues();
            cValue.put(ThermoDatabaseHelper.COL_WEEK,R.string.Tuesday);
            cValue.put(ThermoDatabaseHelper.COL_MON, 19.5);
            cValue.put(ThermoDatabaseHelper.COL_AFTER, 20.5);
            cValue.put(ThermoDatabaseHelper.COL_EVE, 20.0);
            db.insert(ThermoDatabaseHelper.TABLE_NAME, "NULL", cValue);

            cValue = new ContentValues();
            cValue.put(ThermoDatabaseHelper.COL_WEEK,R.string.Wednesday);
            cValue.put(ThermoDatabaseHelper.COL_MON, 19.5);
            cValue.put(ThermoDatabaseHelper.COL_AFTER, 20.5);
            cValue.put(ThermoDatabaseHelper.COL_EVE, 20.0);
            db.insert(ThermoDatabaseHelper.TABLE_NAME, "NULL", cValue);

            cValue = new ContentValues();
            cValue.put(ThermoDatabaseHelper.COL_WEEK,R.string.Thursday);
            cValue.put(ThermoDatabaseHelper.COL_MON, 19.5);
            cValue.put(ThermoDatabaseHelper.COL_AFTER, 20.5);
            cValue.put(ThermoDatabaseHelper.COL_EVE, 20.0);
            db.insert(ThermoDatabaseHelper.TABLE_NAME, "NULL", cValue);

            cValue = new ContentValues();
            cValue.put(ThermoDatabaseHelper.COL_WEEK,R.string.Friday);
            cValue.put(ThermoDatabaseHelper.COL_MON, 19.5);
            cValue.put(ThermoDatabaseHelper.COL_AFTER, 20.5);
            cValue.put(ThermoDatabaseHelper.COL_EVE, 20.0);
            db.insert(ThermoDatabaseHelper.TABLE_NAME, "NULL", cValue);

            cValue = new ContentValues();
            String Saturday = getResources().getString(R.string.Saturday);
            cValue.put(ThermoDatabaseHelper.COL_WEEK,Saturday);
            cValue.put(ThermoDatabaseHelper.COL_MON, 19.5);
            cValue.put(ThermoDatabaseHelper.COL_AFTER, 20.5);
            cValue.put(ThermoDatabaseHelper.COL_EVE, 20.0);
            db.insert(ThermoDatabaseHelper.TABLE_NAME, "NULL", cValue);

            cValue = new ContentValues();
            cValue.put(ThermoDatabaseHelper.COL_WEEK,R.string.Sunday);
            cValue.put(ThermoDatabaseHelper.COL_MON, 19.5);
            cValue.put(ThermoDatabaseHelper.COL_AFTER, 20.5);
            cValue.put(ThermoDatabaseHelper.COL_EVE, 20.0);
            db.insert(ThermoDatabaseHelper.TABLE_NAME, "NULL", cValue);

            //Select again
            cursor = db.query(ThermoDatabaseHelper.TABLE_NAME,
                    new String[]{ThermoDatabaseHelper.ID,
                            ThermoDatabaseHelper.COL_WEEK,
                            ThermoDatabaseHelper.COL_MON,
                            ThermoDatabaseHelper.COL_AFTER,
                            ThermoDatabaseHelper.COL_EVE},
                    null,null,null,null,null,null);
        }
        //Our table has values which we want to display in our list
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            //add weekday from query into the ArrayList
            int index = cursor.getColumnIndex( ThermoDatabaseHelper.COL_WEEK);
            String day = cursor.getString( index );
            index = cursor.getColumnIndex( ThermoDatabaseHelper.COL_MON);
            double morning = cursor.getDouble( index );
            index = cursor.getColumnIndex( ThermoDatabaseHelper.COL_AFTER);
            double after = cursor.getDouble( index );
            index = cursor.getColumnIndex( ThermoDatabaseHelper.COL_EVE);
            double evening = cursor.getDouble( index );
            list.add(new ThermoItem(day, morning, after, evening));
            cursor.moveToNext();
        }

        return list;
    }

    @Override
    public void onItemClick(AdapterView<?> adapter, View arg1, int position, long arg3) {
        /*String item = adapter.getItemAtPosition(position).toString();
        //Toast.makeText(Test.this, "CLICK: " + item, Toast.LENGTH_SHORT).show();
        Button closePopupBtn;
        //instantiate the popup.xml layout file
        LayoutInflater layoutInflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = layoutInflater.inflate(R.layout.thermo_popup, null);

        closePopupBtn = (Button) customView.findViewById(R.id.closePopupBtn);

        //instantiate popup window
        popupWindow = new PopupWindow(customView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

        //display the popup window
        popupWindow.showAtLocation(customView, Gravity.CENTER, 0, 0);

        //close the popup window on button click
        closePopupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });*/
     }
}