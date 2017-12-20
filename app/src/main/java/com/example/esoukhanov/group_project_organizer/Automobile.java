package com.example.esoukhanov.group_project_organizer;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class Automobile extends FragmentActivity {
protected ListView list_view;
protected EditText e_date;
protected EditText e_litars;
protected EditText e_price;
protected EditText e_km;
protected Button add, curentPrice;
protected ArrayList<ListItim> array = new ArrayList<ListItim>();
protected ListItim itim;
protected ListItimAdapter messageAdapter;
    protected Cursor cursor;
    protected   boolean fl;
    protected FrameLayout frame;
 protected DatePickerDialog datePickerDialog;
    protected static final String ACTIVITY_NAME = "Automobile";
    protected AutomobileDatabaseHelper mydbase;
    protected SQLiteDatabase dbase;
    protected Context context;
    protected ContentValues cv;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automobile);

        frame=(FrameLayout)findViewById(R.id.frame);
        fl = frame!=null;

        fab= findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(Automobile.this);
                builder.setMessage(R.string.dialog_message2);
                builder.setTitle(R.string.dialog_title2);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.cancel();
                    }
                }).show();

            }
        });

        e_date = (EditText)findViewById(R.id.date);
        //e_date.setFocusable(false);
        e_litars =(EditText) findViewById(R.id.litars);
        e_price =(EditText) findViewById(R.id.price);
        e_km =(EditText) findViewById(R.id.km);
        add =(Button) findViewById(R.id.add);
        curentPrice = (Button) findViewById(R.id.Price);

        list_view =(ListView) findViewById(R.id.list_view);
        messageAdapter = new ListItimAdapter(this);
        list_view.setAdapter(messageAdapter);

        context = getApplicationContext();
        mydbase = new AutomobileDatabaseHelper(context) ;
        dbase = mydbase.getReadableDatabase();
        cv= new ContentValues();


       populate();

        e_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(Automobile.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                e_date.setText(dayOfMonth + "/"
                                        + (monthOfYear + 1) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = e_date.getText().toString();
                String litars = e_litars.getText().toString();
                String price = e_price.getText().toString();
                String km = e_km.getText().toString();

                if (!date.isEmpty() && !litars.isEmpty() && !price.isEmpty() && !km.isEmpty() ) {
                    array.add(itim);
                    //sort();
                   // messageAdapter.notifyDataSetChanged(); //this restarts the process of getCount()/getView()

                    ContentValues values = new ContentValues();
                    values.put(AutomobileDatabaseHelper.KEY_DATE, date);
                    values.put(AutomobileDatabaseHelper.KEY_LITARS, litars);
                    values.put(AutomobileDatabaseHelper.KEY_PRICE, price);
                    values.put(AutomobileDatabaseHelper.KEY_KM, km);

                    dbase.insert(AutomobileDatabaseHelper.TABLE_NAME, null, values);
                    e_date.setText("");
                    e_litars.setText("");
                    e_price.setText("");
                    e_km.setText("");
                  }else{
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Automobile.this);
                    builder.setMessage(R.string.dialog_message);
                    builder.setTitle(R.string.dialog_title);
                    builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                           // User cancelled the dialog
                            dialog.cancel();
                        }
                    });
                    builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                            Automobile.this.finish();
                        }
                    })
                            .show();
                }

                Snackbar.make(v,"One row added" ,Snackbar.LENGTH_LONG).show();
                populate();
            }
        });
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                long itemID;

                Log.i("Automobile", "I=="+position+" l=="+id);

                itemID = messageAdapter.getItemId(position);
                String date= cursor.getString((cursor.getColumnIndex(AutomobileDatabaseHelper.KEY_DATE)));
                String litars= cursor.getString((cursor.getColumnIndex(AutomobileDatabaseHelper.KEY_LITARS)));
                String price= cursor.getString((cursor.getColumnIndex(AutomobileDatabaseHelper.KEY_PRICE)));
                String km= cursor.getString((cursor.getColumnIndex(AutomobileDatabaseHelper.KEY_KM)));
                Log.i("Automobile", "ID=="+itemID+" ItemMessage=="+date);
                array.add(itim);
                //Checking for phone
                Bundle args = new Bundle();
                args.putInt("lineViewID",position);
                args.putLong("lineID", itemID);
                args.putString("iDate",date);
                args.putString("iLitars",litars);
                args.putString("iPrice",price);
                args.putString("iKm",km);

                if (!fl){
                    Log.i("Automobile", "==Processing Fragments for a Phone");
                    // Create fragment and give it an argument for the selected article
//                    MessageFragment newFragment = new MessageFragment();
                    Intent intent = new Intent(getApplicationContext(), ItimDelete.class);
                    intent.putExtra("data", args);
                    startActivityForResult(intent, 730);
                }
                // Checking for Tablet
                else {
                    Log.i("ChatWindow", "==Processing Fragments for a Tablet");
                    BlankFragment newFragment = new BlankFragment(Automobile.this);
                    newFragment.setArguments(args);

                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                    // Replace whatever is in the fragment_container view with this fragment,
                    // and add the transaction to the back stack so the user can navigate
                    transaction.replace(R.id.frame, newFragment);
////        transaction.addToBackStack(null);
//        // Commit the transaction
                    transaction.commit();
                }
            }
        });
        curentPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(ACTIVITY_NAME, "User clicked Weather Forecast");
                Intent i = new Intent(Automobile.this, Honda_Types.class);
                startActivity(i);
            }
        });
    }
    public void populate(){
        array.clear();
        cursor = dbase.rawQuery("select * from " + AutomobileDatabaseHelper.TABLE_NAME, null);
        cursor .moveToFirst();
        while(!cursor.isAfterLast() ){
            //String date= cursor.getString((cursor.getColumnIndex(AutomobileDatabaseHelper.KEY_DATE)));
            // String litars= cursor.getString((cursor.getColumnIndex(AutomobileDatabaseHelper.KEY_LITARS)));
            // String price= cursor.getString((cursor.getColumnIndex(AutomobileDatabaseHelper.KEY_PRICE)));
            // String km= cursor.getString((cursor.getColumnIndex(AutomobileDatabaseHelper.KEY_KM)));
            itim = new ListItim (cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
            array.add(itim);
            Log.i("Automobile", "SQL MESSAGE:" + cursor.getString(cursor.getColumnIndex(AutomobileDatabaseHelper.KEY_DATE)));
            cursor.moveToNext();
        }
       sort();
        messageAdapter.notifyDataSetChanged();
    }
    //On result return handler
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 730) {
            Log.i("ChatWindow", "==Returned to ChatWindow.onActivityResult");
            if (resultCode==737){
                Bundle args =  data.getBundleExtra("data");

                Long databaseKeyID = args.getLong("lineID");
                int lineViewID = args.getInt("lineViewID");
                Log.i("ChatWindow", "==Database deletion. lineID="+databaseKeyID+", lineViewID="+lineViewID);
                eraseMessage(lineViewID, databaseKeyID);
            }
        }
    }
    protected void eraseMessage(int lineViewID, Long lineID){
        // Deleting from Arraylist
       // array.remove(lineViewID);
        // Deleting row from database
        dbase.delete(AutomobileDatabaseHelper.TABLE_NAME, AutomobileDatabaseHelper.KEY_ID + "=" + lineID, null);
        //Update Cursor
        //cursor = dbase.rawQuery("SELECT * FROM "+AutomobileDatabaseHelper.TABLE_NAME, null);
       // messageAdapter.notifyDataSetChanged();
        populate();
    }

    private class ListItimAdapter extends ArrayAdapter<ListItim> {
        public ListItimAdapter(Context ctx) {
            super(ctx, 0);
        }

        public int getCount() {
            return array.size();
        }

        @Override
        public ListItim getItem(int position) {
            return array.get(position);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = Automobile.this.getLayoutInflater();
            View result = null;

                result = inflater.inflate(R.layout.automobile_row, null);


            ListItim li = getItem(position);
            String date= li.getDate();
            String litres = li.getLitars();
            String price = li.getPrice();
            String km = li.getLitars();

            TextView d = result.findViewById(R.id.t_d);
            d.setText(String.valueOf(date) ); // get the string at position
            TextView l = result.findViewById(R.id.t_l);
            l.setText(String.valueOf(litres)); // get the string at position

            TextView p = result.findViewById(R.id.t_p);
            p.setText(String.valueOf(price)); // get the string at position
            TextView k = result.findViewById(R.id.t_km);
            k.setText(String.valueOf(km)); // get the string at position
            Log.i("Automobile", "Create view");
            return result;

        }
        @Override
        public long getItemId(int position){
            cursor.moveToPosition(position);
            String x;
            x =cursor.getString(cursor.getColumnIndex(AutomobileDatabaseHelper.KEY_ID));
            return Long.parseLong(x);
        }
    }


    protected void onDestroy() {
        super.onDestroy();
        dbase.close();
        cursor.close();
        cv.clear();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
    public void sort(){
        //Sorting the Array
        Collections.sort(array, new MyComparator());
    }
    //Custom Comparator
    private class MyComparator implements Comparator<ListItim> {

        @Override
        public int compare(ListItim o1, ListItim o2) {
            SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
            String inputString1 = o1.getDate();
            String inputString2 = o2.getDate();
            //		String inputString1 = "23/01/1997";
//		String inputString2 = "27/04/1997";
            long diff = 0;

            try {
                Date date1 = myFormat.parse(inputString1);
                Date date2 = myFormat.parse(inputString2);
                diff = date2.getTime() - date1.getTime();
            } catch (ParseException e) {
                e.printStackTrace();

            }
            return (int) diff;
        }
    }
}
