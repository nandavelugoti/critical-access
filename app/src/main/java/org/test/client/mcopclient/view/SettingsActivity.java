package org.test.client.mcopclient.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.test.client.mcopclient.CriticalAccess;
import org.test.client.mcopclient.R;
import org.test.client.mcopclient.controller.MCOPConfigurationManager;
import org.test.client.mcopclient.controller.MCOPServiceManager;
import org.test.client.mcopclient.controller.events.AuthorizationRequestEvent;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public class SettingsActivity extends AppCompatActivity {
    private final static String TAG = AuthorizationRequestEvent.class.getCanonicalName();

    static TextView tvDisplayName, tvMcpttId;
    static Button btnLogin, btnLogout;
    static RadioButton rbIDMS, rbNoAuth, rbAuto, rbManual;
    private static Context ctx;
    private static final int AUTHETICATION_RESULT = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvDisplayName = findViewById(R.id.tv_display_name);
        tvMcpttId = findViewById(R.id.tv_mcptt_id);
        btnLogin = findViewById(R.id.btn_login);
        btnLogout = findViewById(R.id.btn_logout);
        rbIDMS = findViewById(R.id.rb_idms);
        rbNoAuth = findViewById(R.id.rb_no_auth);
        rbAuto = findViewById(R.id.rb_auto);
        rbManual = findViewById(R.id.rb_manual);

        updateUI();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public static void updateUI() {
        ((SettingsActivity) ctx).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tvDisplayName.setText(MCOPServiceManager.AddressBook.getCurrentUser().getDisplayName());
                tvMcpttId.setText(MCOPServiceManager.AddressBook.getCurrentUser().getMcpttID());

                btnLogin.setEnabled(!MCOPServiceManager.isUserLoggedIn());
                btnLogout.setEnabled(MCOPServiceManager.isUserLoggedIn());

                rbIDMS.setChecked(MCOPConfigurationManager.isIdMSCMS());
                rbNoAuth.setChecked(!MCOPConfigurationManager.isIdMSCMS());
                rbAuto.setChecked(MCOPConfigurationManager.isAutoRegister());
                rbManual.setChecked(!MCOPConfigurationManager.isAutoRegister());
            }
        });
    }

    public static void startLogin(String requestUri, String redirect) {
        Intent intent2 = new Intent(ctx, ScreenAuthenticationWebView.class);
        intent2.putExtra(ScreenAuthenticationWebView.DATA_URI_INTENT, requestUri.trim());
        intent2.putExtra(ScreenAuthenticationWebView.DATA_REDIRECTION_URI, redirect.trim());
        ((SettingsActivity) ctx).startActivityForResult(intent2, AUTHETICATION_RESULT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case AUTHETICATION_RESULT:
                if (resultCode == ScreenAuthenticationWebView.RETURN_ON_AUTHENTICATION_LISTENER_FAILURE) {
                    String dataError;
                    if (data != null &&
                            (dataError = data.getStringExtra(ScreenAuthenticationWebView.RETURN_ON_AUTHENTICATION_ERROR)) != null &&
                            dataError instanceof String) {
                        Log.e(TAG, "Authentication Error: " + dataError);
                    } else {
                        Log.e(TAG, "Error processing authentication.");
                    }
                } else if (resultCode == ScreenAuthenticationWebView.RETURN_ON_AUTHENTICATION_LISTENER_OK) {
                    String dataUri;
                    if (data != null &&
                            (dataUri = data.getStringExtra(ScreenAuthenticationWebView.RETURN_ON_AUTHENTICATION_RESPONSE)) != null &&
                            dataUri instanceof String) {
                        URI uri = null;
                        try {
                            uri = new URI(dataUri);
                            MCOPServiceManager.authorizeUser(uri);
                            Log.i(TAG, "Uri: " + uri.toString());
                        } catch (URISyntaxException e) {
                            Log.e(TAG, "Authentication Error: " + e.getMessage());
                            e.printStackTrace();
                        }
                    } else {
                        Log.e(TAG, "Error processing file to import profiles.");
                    }
                }
                break;
        }
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
