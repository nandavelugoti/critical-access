package org.test.client.mcopclient.controller.events;

import android.content.Intent;
import android.util.Log;

import org.test.client.mcopclient.ConstantsMCOP;
import org.test.client.mcopclient.controller.MCOPServiceManager;

import static org.test.client.mcopclient.ConstantsMCOP.ERROR_CODE_DEFAULT;
import static org.test.client.mcopclient.ConstantsMCOP.VALUE_BOOLEAN_DEFAULT;

public class UnLoginEvent implements EventListener {
    private final static String TAG = UnLoginEvent.class.getCanonicalName();

    @Override
    public void handleEvent(Intent action) {
        if (action.getIntExtra(ConstantsMCOP.UnLoginEventExtras.ERROR_CODE, ERROR_CODE_DEFAULT) != ERROR_CODE_DEFAULT) {
            // Error in unLoginEvent
            String stringError = action.getStringExtra(ConstantsMCOP.UnLoginEventExtras.ERROR_STRING);
        } else {
            // No error
            boolean success = false;
            if ((success = action.getBooleanExtra(ConstantsMCOP.UnLoginEventExtras.SUCCESS, VALUE_BOOLEAN_DEFAULT)) == true) {
                MCOPServiceManager.updateCurrentUser("N/A", "N/A");
            } else {
                Log.e(TAG, "Error: Unregistration process");
            }
        }
    }
}