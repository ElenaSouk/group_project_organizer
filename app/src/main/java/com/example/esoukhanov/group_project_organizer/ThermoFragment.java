package com.example.esoukhanov.group_project_organizer;

import android.app.Dialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.app.DialogFragment;
import android.widget.Toast;

/**
 * Main idea was taken from here:
 * https://developer.android.com/reference/android/app/DialogFragment.html
 */
public  class ThermoFragment extends DialogFragment {
        int mNum;
        static ThermoItem element;
        View v;
    /**
         * Create a new instance of MyDialogFragment, providing "num"
         * as an argument.
         */
        static ThermoFragment newInstance(ThermoItem e) {
            ThermoFragment f = new ThermoFragment();
            element = e;

            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mNum = 1;

            // Pick a style based on the num.
            int style = DialogFragment.STYLE_NORMAL, theme = 0;
            switch ((mNum-1)%6) {
                case 1: style = DialogFragment.STYLE_NO_TITLE; break;
                case 2: style = DialogFragment.STYLE_NO_FRAME; break;
                case 3: style = DialogFragment.STYLE_NO_INPUT; break;
                case 4: style = DialogFragment.STYLE_NORMAL; break;
                case 5: style = DialogFragment.STYLE_NORMAL; break;
                case 6: style = DialogFragment.STYLE_NO_TITLE; break;
                case 7: style = DialogFragment.STYLE_NO_FRAME; break;
                case 8: style = DialogFragment.STYLE_NORMAL; break;
            }
            switch ((mNum-1)%6) {
                case 4: theme = android.R.style.Theme_Holo; break;
                case 5: theme = android.R.style.Theme_Holo_Light_Dialog; break;
                case 6: theme = android.R.style.Theme_Holo_Light; break;
                case 7: theme = android.R.style.Theme_Holo_Light_Panel; break;
                case 8: theme = android.R.style.Theme_Holo_Light; break;
            }
            setStyle(style, theme);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            v = inflater.inflate(R.layout.thermo_popup, container, false);
            View tv = v.findViewById(R.id.text);
            // set the custom dialog components - text, image and button
            final Spinner weekday = (Spinner) v.findViewById(R.id.weekday);
            selectValue(weekday, element.getName());

            final EditText morningTemp = (EditText) v.findViewById(R.id.morning_temp);
            morningTemp.setText(element.getMorningTemp()+"");

            final EditText afternoonTemp = (EditText) v.findViewById(R.id.afternoon_temp);
            afternoonTemp.setText(element.getAfternoonTemp()+"");

            final EditText eveningTemp = (EditText) v.findViewById(R.id.evening_temp);
            eveningTemp.setText(element.getEveningTemp()+"");
            // Watch for button clicks.
            Button button = (Button)v.findViewById(R.id.dialogButtonOK);
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Snackbar.make(v,"@string/Temperature_is_changed.", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                    String weekday = element.getName();
                    ThermoQuery thermoQuery = new ThermoQuery();
                    thermoQuery.execute( weekday,  morningTemp.getText().toString(),
                            afternoonTemp.getText().toString(),  eveningTemp.getText().toString());

                }
            });
            Button buttonClose = (Button)v.findViewById(R.id.dialogButtonClose);
            buttonClose.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    ((ActivityHouseThermostat)getActivity()).refreshList();
                    ThermoFragment.this.dismiss();
                }
            });

            Button buttonNew = (Button)v.findViewById(R.id.dialogButtonNew);
            buttonNew.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {

                    Toast toast=Toast.makeText(getActivity() ,
                            "@string/Day_temperature_rules_were_changed",Toast.LENGTH_LONG);
                    toast.show();

                    String day = weekday.getSelectedItem().toString();
                    ThermoQuery thermoQuery = new ThermoQuery();
                    thermoQuery.execute( day,  morningTemp.getText().toString(),
                            afternoonTemp.getText().toString(),  eveningTemp.getText().toString());

                }
            });

            //http://www.truiton.com/2015/04/android-action-bar-dialog-using-toolbar/
            Toolbar toolbar = (Toolbar) v.findViewById(R.id.thermo_menu_items);
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    String title = "";


                    //Source: https://www.mkyong.com/android/android-custom-dialog-example/
                    final Dialog dialog = new Dialog(ThermoFragment.this.getActivity());
                    dialog.setContentView(R.layout.thermo_dialog);
                    //dialog.setTitle(title);

                    TextView text = (TextView) dialog.findViewById(R.id.text);

                    switch (item.getItemId()) {
                        case R.id.about:
                            String Author = getResources().getString(R.string.Author);
                            String Version = getResources().getString(R.string.Version);
                            dialog.setTitle(R.string.Information);
                            text.setText(Author + "\n"
                            + Version);


                            break;

                        case R.id.help:
                            dialog.setTitle(R.string.How_to);
                            String Save_Edit = getResources().getString(R.string.Save_Edit);
                            String Save_new_day = getResources().getString(R.string.Save_new_day);
                            String Close_Dialog = getResources().getString(R.string.Close_Dialog);
                            text.setText(Save_Edit + "\n" + Save_new_day + "\n" + Close_Dialog);


                            break;
                        default:
                            return false;
                    }

                    // set the custom dialog components - text, image and button



                    Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                    // if button is clicked, close the custom dialog
                    dialogButton.setOnClickListener(new Toolbar.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                    return true;
                }
            });
            toolbar.inflateMenu(R.menu.thermo_menu_items);
            //toolbar.setTitle(title);
            return v;
        }
    //https://stackoverflow.com/questions/11072576/set-selected-item-of-spinner-programmatically
    private void selectValue(Spinner spinner, Object value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(value)) {
                spinner.setSelection(i);
                break;
            }
        }
    }
    private class ThermoQuery extends AsyncTask<String, Integer, String> {

        ProgressBar normProgBar = (ProgressBar) v.findViewById(R.id.normProgBar);

        @Override
        protected String doInBackground(String... params) {
            SystemClock.sleep(500);
            publishProgress(25);
            ThermoDatabaseHelper tdb = new ThermoDatabaseHelper(getActivity());
            SQLiteDatabase db = tdb.getWritableDatabase();
            ContentValues cValue = new ContentValues();
            cValue.put(ThermoDatabaseHelper.COL_WEEK, params[0]);
            cValue.put(ThermoDatabaseHelper.COL_MON, params[1]);
            cValue.put(ThermoDatabaseHelper.COL_AFTER, params[2]);
            cValue.put(ThermoDatabaseHelper.COL_EVE, params[3]);
            db.update(ThermoDatabaseHelper.TABLE_NAME, cValue, ThermoDatabaseHelper.COL_WEEK+"='"+params[0]+"'", null);

            SystemClock.sleep(1000);
            publishProgress(50);
            //for(int i = 0; i < 1000000; i++);
            //((ActivityHouseThermostat)getActivity()).refreshList();
            SystemClock.sleep(1000);
            publishProgress(75);
            return "";
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            normProgBar.setVisibility(View.VISIBLE);
            super.onProgressUpdate(progress);
            normProgBar.setProgress(progress[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            normProgBar.setVisibility(View.INVISIBLE);
        }
    }
}
