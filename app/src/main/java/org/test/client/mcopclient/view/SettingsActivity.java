package org.test.client.mcopclient.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.test.client.mcopclient.CriticalAccess;
import org.test.client.mcopclient.R;
import org.test.client.mcopclient.controller.MCOPConfigurationManager;
import org.test.client.mcopclient.controller.MCOPServiceManager;

public class SettingsActivity extends AppCompatActivity {
    RadioButton rbIDMS, rbNoAuth, rbAuto, rbManual;
    private static Context ctx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        setContentView(R.layout.activity_settings);
        rbIDMS = findViewById(R.id.rb_idms);
        rbNoAuth = findViewById(R.id.rb_no_auth);
        rbAuto = findViewById(R.id.rb_auto);
        rbManual = findViewById(R.id.rb_manual);

        rbIDMS.setChecked(MCOPConfigurationManager.isIdMSCMS());
        rbNoAuth.setChecked(!MCOPConfigurationManager.isIdMSCMS());

        rbAuto.setChecked(MCOPConfigurationManager.isAutoRegister());
        rbManual.setChecked(!MCOPConfigurationManager.isAutoRegister());
    }

    public void btnLoginOnClick(View view) {
        MCOPServiceManager.login();
    }

    public void btnLogoutOnClick(View view) {
        MCOPServiceManager.logout();
    }

    public void rbIdMSClick(View view) {
        MCOPConfigurationManager.setIdMSCMS(rbIDMS.isChecked());
    }

    public void rbNoAuthOnClick(View view) {
        MCOPConfigurationManager.setIdMSCMS(!rbNoAuth.isChecked());
    }

    public void rbAutoOnClick(View view) {
        MCOPConfigurationManager.setAutoRegister(rbAuto.isChecked());
    }

    public void rbManualOnClick(View view) {
        MCOPConfigurationManager.setAutoRegister(!rbManual.isChecked());
    }
}
