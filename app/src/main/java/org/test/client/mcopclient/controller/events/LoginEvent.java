package org.test.client.mcopclient.controller.events;

import android.content.Intent;
import android.util.Log;

import org.test.client.mcopclient.BuildConfig;
import org.test.client.mcopclient.ConstantsMCOP;

import static org.test.client.mcopclient.ConstantsMCOP.ERROR_CODE_DEFAULT;
import static org.test.client.mcopclient.ConstantsMCOP.VALUE_BOOLEAN_DEFAULT;

public class LoginEvent implements EventListener {
    private final static String TAG = LoginEvent.class.getCanonicalName();

    @Override
    public void handleEvent(Intent action) {
        if (action.getIntExtra(ConstantsMCOP.LoginEventExtras.ERROR_CODE, ERROR_CODE_DEFAULT) != ERROR_CODE_DEFAULT) {
            // Error in LoginEvent
            String stringError = action.getStringExtra(ConstantsMCOP.LoginEventExtras.ERROR_STRING);
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
            } else {
                Log.e(TAG, "Error: Registration process");
            }
        }
    }
}