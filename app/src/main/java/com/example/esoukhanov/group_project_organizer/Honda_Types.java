package com.example.esoukhanov.group_project_organizer;

import android.app.Activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class Honda_Types extends Activity {

    private String [] names = {"Accord (1976–present)",
            "Amaze (2013–present)",
            "Avancier (2016–present)",
            "Ballade (1980–1991, 2014–present)",
            "Brio (2011–present)",
            "BR-V (2015–present)",
            "City (1981–present)",
            "Civic (1972–present)",
            "Civic Tourer (2014–present)",
            "Civic Type R (1997-2010, 2015–present)",
            "Clarity (2008-2014, 2016–present)",
            "Crider (2013–present)",
            "CR-V (1996–present)",
            "CR-V S (2012–present)",
            "Elysion (2003–present)",
            "Fit/Jazz (2001–present)",
            "Fit Hybrid (2011–present)",
            "Fit Shuttle (2011–present)",
            "Freed (2008–present)",
            "Freed Spike (2008–present)",
            "Gienia (2016-present)",
            "Grace (2014–present)",
            "Greiz (2015–present)",
            "Hobio (2003–present)",
            "HR-V (1999-2005, 2015–present)",
            "Jade (2013–present)",
            "Jazz RS (2005–present)",
            "Legend (1985–2012, 2014–present)",
            "MC-β (2013–present)",
            "Mobilio (2001-2008, 2014–present)",
            "Mobilio RS (2014–present)",
            "NSX (1990-2005, 2016–present)",
            "N-Box (2012–present)",
            "N-Box+ (2012–present)",
            "N-Box Slash (2014–present)",
            "N-One (2012–present)",
            "N-WGN (2012–present)",
            "Odyssey/Shuttle (international market) (1995–present)",
            "Pilot (2003–present)",
            "Ridgeline (2006-2014, 2016–present)",
            "S660 (2014–present)",
            "Shuttle (1994–present)",
            "StepWGN (1996–present)",
            "StepWGN Spada (2009–present)",
            "UR-V (2017-present)",
            "Vamos (1970-1973, 1999–present)",
            "Vezel (2013–present)",
            "WR-V (2017-present)"};
    protected ListView lv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_honda__types);

        lv= (ListView) findViewById(R.id.lView);
        lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, new ArrayList<String>()));
        new MyClass().execute();
    }

     class MyClass extends AsyncTask<Void, String, String>{
        ArrayAdapter<String> ada;
         protected ProgressBar pb;
        int counter;

         @Override
         protected void onPreExecute() {
             ada= (ArrayAdapter<String>)lv.getAdapter();
             pb= (ProgressBar)findViewById(R.id.progress);
             pb.setMax(45);
             pb.setProgress(0);
             pb.setVisibility(View.VISIBLE);
             counter=0;
         }

         @Override
         protected String doInBackground(Void... voids) {

             for(String Name: names){
                 publishProgress(Name);
                 try {
                     Thread.sleep(500);
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             }
             return "All names added";
         }


         @Override
         protected void onProgressUpdate(String... values) {
            ada.add(values[0]);
            counter++;
            pb.setProgress(counter);

         }

         @Override
         protected void onPostExecute(String result) {
             Toast.makeText(getApplicationContext(), result,Toast.LENGTH_LONG).show();
             pb.setVisibility(View.GONE);
         }
     }
}
