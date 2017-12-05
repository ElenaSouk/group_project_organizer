package com.example.esoukhanov.group_project_organizer;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by milab on 2017-10-30.
 */

public class AutomobileDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "carDatabase";
    public static final int VERSION_NUM = 3; // change version_number and call onUpadate()
    public final static String KEY_ID = "id";
    public final static String KEY_DATE = "date";
    public final static String KEY_LITARS = "litars";
    public final static String KEY_PRICE = "price";
    public final static String KEY_KM = "km";
    public final static String TABLE_NAME = "Automobile";
    protected static final String ACTIVITY_NAME = "AutomobileDatabaseHelper";

    public AutomobileDatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION_NUM);
Log.d("Database operation", "Databese created");
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_AUTOMOBILE_TABLE ="CREATE TABLE " + TABLE_NAME +" ("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                + KEY_DATE + " TIMESTAMP,"
                + KEY_LITARS + " INTEGER,"
                + KEY_PRICE + " INTEGER,"
                + KEY_KM + " INTEGER"+ ")";
        db.execSQL(CREATE_AUTOMOBILE_TABLE);
        Log.i("ACTIVITY_NAME", "Calling onCreate");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //drop tble is eists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        //create new table
        onCreate(db);
        Log.i("ACTIVITY_NAME", "Calling onUpgrade, oldVersion=" + oldVersion + "newVersion=" + newVersion);
    }
}
