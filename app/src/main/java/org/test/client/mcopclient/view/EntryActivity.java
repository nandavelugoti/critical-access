package org.test.client.mcopclient.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import org.test.client.mcopclient.controller.MCOPServiceManager;

import java.util.ArrayList;

public class EntryActivity extends Activity {
    private static final String PARAMETER_PROFILE = "parameters";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayList<String> strings = getIntent().getStringArrayListExtra(PARAMETER_PROFILE);
        MCOPServiceManager.initialize(strings);

        // launch a different activity
        Intent launchIntent = new Intent();
        Class<?> launchActivity;
        try {
            String className = getScreenClassName();
            launchActivity = Class.forName(className);
        } catch (ClassNotFoundException e) {
            launchActivity = HomePage.class;
        }
        launchIntent.setClass(getApplicationContext(), launchActivity);
        startActivity(launchIntent);

        finish();
    }

    /**
     * return Class name of Activity to show
     **/
    private String getScreenClassName() {
        // NOTE - Place logic here to determine which screen to show next
        // Default is used in this demo

        // Default view
        String activity = HomePage.class.getName();
        if (MCOPServiceManager.AddressBook.getCurrentUser() == null){
            // Has to login first
            activity = LoginActivity.class.getName();
        }
        return activity;
    }
}
