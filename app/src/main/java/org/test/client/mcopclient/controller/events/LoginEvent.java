package org.test.client.mcopclient.controller.events;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.test.client.mcopclient.BuildConfig;
import org.test.client.mcopclient.ConstantsMCOP;
import org.test.client.mcopclient.CriticalAccess;
import org.test.client.mcopclient.controller.MCOPServiceManager;

import static org.test.client.mcopclient.ConstantsMCOP.ERROR_CODE_DEFAULT;
import static org.test.client.mcopclient.ConstantsMCOP.VALUE_BOOLEAN_DEFAULT;

public class LoginEvent implements EventListener {
    private final static String TAG = LoginEvent.class.getCanonicalName();

    @Override
    public void handleEvent(Intent action) {
        if (action.getIntExtra(ConstantsMCOP.LoginEventExtras.ERROR_CODE, ERROR_CODE_DEFAULT) != ERROR_CODE_DEFAULT) {
            // Error in LoginEvent
            String stringError = action.getStringExtra(ConstantsMCOP.LoginEventExtras.ERROR_STRING);
            Toast.makeText(CriticalAccess.getContext(), stringError, Toast.LENGTH_SHORT).show();

        } else {
            // No error
            boolean success = false;
            String mcptt_id = null;
            String displayName = null;
            if ((success = action.getBooleanExtra(ConstantsMCOP.LoginEventExtras.SUCCESS, VALUE_BOOLEAN_DEFAULT)) &&
                    (mcptt_id = action.getStringExtra(ConstantsMCOP.LoginEventExtras.MCPTT_ID)) != null
            ) {
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "Login success: " + success + " mcptt_id: " + mcptt_id);
                displayName = action.getStringExtra(ConstantsMCOP.LoginEventExtras.DISPLAY_NAME);
                MCOPServiceManager.updateCurrentUser(mcptt_id, displayName, true);
            } else {
                Log.e(TAG, "Error: Registration process");
            }
        }
    }
}