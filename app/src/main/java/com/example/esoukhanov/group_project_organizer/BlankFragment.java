package com.example.esoukhanov.group_project_organizer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class BlankFragment extends Fragment {
    private boolean devicePhone;


    public BlankFragment() {
        devicePhone = true;
    }
    @SuppressLint("ValidFragment")
    public BlankFragment(Automobile automobile) {
        devicePhone = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        final Bundle args = this.getArguments();
        TextView dateText =(TextView) view.findViewById(R.id.dateHere);
        TextView litarsText =(TextView) view.findViewById(R.id.litarsHere);
        TextView priceText =(TextView) view.findViewById(R.id.priceHere);
        TextView kmText =(TextView) view.findViewById(R.id.kmHere);
        TextView idText = (TextView) view.findViewById(R.id.asd);
        // Setting boxes with texts from bundles
        dateText.setText(args.getString("iDate"));
        litarsText.setText(args.getString("iLitars"));
        priceText.setText(args.getString("iPrice"));
        kmText.setText(args.getString("iKm"));
        final Long lineID = args.getLong("lineID");
        final int lineViewID = args.getInt("lineViewID");
        idText.setText(Long.toString(lineID));

        Button deleteButton = (Button)view.findViewById(R.id.deleteHere);
        //Delete button handler
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (devicePhone) {
                    //Working with a phone
                    Intent intent = new Intent();
                    intent.putExtra("data", args);
                    getActivity().setResult(737, intent);
                    getActivity().finish();
                } else{
                    //If device is a tablet
                    ((Automobile)getActivity()).eraseMessage(lineViewID, lineID);
                }
            }
        });

        return view;
    }

}
