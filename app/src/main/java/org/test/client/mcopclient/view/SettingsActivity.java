package org.test.client.mcopclient.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.test.client.mcopclient.R;
import org.test.client.mcopclient.controller.MCOPServiceManager;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void btnLoginOnClick(View view) {
        MCOPServiceManager.login();
    }

    public void btnLogoutOnClick(View view) {
        MCOPServiceManager.logout();
    }
}
