package com.example.esoukhanov.group_project_organizer;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by esoukhanov on 2017-12-03.

Some ideas were taken from this resource:
http://www.vogella.com/tutorials/AndroidListView/article.html

 For custom dialog window
 https://www.mkyong.com/android/android-custom-dialog-example/
 */
public class ThermoListAdapter extends ArrayAdapter<ThermoItem> {

    private final List<ThermoItem> list;
    private final Activity context;

    public ThermoListAdapter(Activity context, List<ThermoItem> list) {
        super(context, R.layout.thermo_row, list);
        this.context = context;
        this.list = list;
    }

    static class ViewHolder {
        protected TextView text;
        protected TextView morningTemp;
        protected TextView afternoonTemp;
        protected TextView eveningTemp;
        protected ImageButton button;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;
        if (convertView == null) {
            LayoutInflater inflator = context.getLayoutInflater();
            view = inflator.inflate(R.layout.thermo_row, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.text = (TextView) view.findViewById(R.id.label);
            viewHolder.morningTemp = (TextView) view.findViewById(R.id.morning);
            viewHolder.afternoonTemp = (TextView) view.findViewById(R.id.afternoon);
            viewHolder.eveningTemp = (TextView) view.findViewById(R.id.evening);
            viewHolder.button = (ImageButton) view.findViewById(R.id.configButton);
            viewHolder.button.
            setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    final ThermoItem element = (ThermoItem) viewHolder.button
                            .getTag();
                    FragmentTransaction ft = context.getFragmentManager().beginTransaction();
                    /*Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                    if (prev != null) {
                        ft.remove(prev);
                    }*/
                    ft.addToBackStack(null);

                    // Create and show the dialog.
                    ThermoFragment newFragment = ThermoFragment.newInstance(element);
                    newFragment.show(ft, "dialog");
                }
            });
            view.setTag(viewHolder);
            viewHolder.button.setTag(list.get(position));
        } else {
            view = convertView;
            ((ViewHolder) view.getTag()).button.setTag(list.get(position));
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.text.setText(list.get(position).getName());
        holder.morningTemp.setText(list.get(position).getMorningTemp()+"");
        holder.afternoonTemp.setText(list.get(position).getAfternoonTemp()+"");
        holder.eveningTemp.setText(list.get(position).getEveningTemp()+"");
        return view;
    }


}