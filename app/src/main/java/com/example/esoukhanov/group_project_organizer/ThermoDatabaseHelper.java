package com.example.esoukhanov.group_project_organizer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by esoukhanov on 2017-12-17.
 */
public class ThermoDatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Thermo.db";
    public static final int VERSION_NUM = 1;
    public final static String ID = "_id";
    public static final String TABLE_NAME = "Thermo_settings";
    public static final String COL_WEEK = "week_day";
    public static final String COL_MON = "morning";
    public static final String COL_AFTER = "afternoon";
    public static final String COL_EVE = "evening";

    // constructor  opens a database file “Thermo.db”
    public ThermoDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }

    //creates a table with a column for id of integers that autoincrement - KEY_ID
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " +TABLE_NAME+ "( "+ID+"  INTEGER PRIMARY KEY AUTOINCREMENT, "+
                COL_WEEK+" TEXT, "+COL_MON+" REAL, "+COL_AFTER+" REAL, "+COL_EVE+" REAL)" );
        //db = getWritableDatabase();
        Log.i("ThermoDatabaseHelper","Calling OnCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

}