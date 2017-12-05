package com.example.esoukhanov.group_project_organizer;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class ItimDelete extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itim_delete);
        Bundle extras = getIntent().getExtras();
        Bundle args = extras.getBundle("data");
        // Otherwise, we're in the one-pane layout and must swap frags...
        // Create fragment and give it an argument for the selected article
        BlankFragment newFragment = new BlankFragment();
        newFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.emptyFrame, newFragment);
        transaction.addToBackStack(null);
//        // Commit the transaction
        transaction.commit();
    }
}
