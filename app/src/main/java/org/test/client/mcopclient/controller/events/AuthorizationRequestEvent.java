package org.test.client.mcopclient.controller.events;

import android.content.Intent;
import android.util.Log;

import org.test.client.mcopclient.BuildConfig;
import org.test.client.mcopclient.ConstantsMCOP;
import org.test.client.mcopclient.CriticalAccess;
import org.test.client.mcopclient.view.ScreenAuthenticationWebView;

import static org.test.client.mcopclient.ConstantsMCOP.ERROR_CODE_DEFAULT;

public class AuthorizationRequestEvent implements EventListener {
    private final static String TAG = AuthorizationRequestEvent.class.getCanonicalName();

    @Override
    public void handleEvent(Intent action) {
        if ((action.getIntExtra(ConstantsMCOP.AuthorizationRequestExtras.ERROR_CODE, ERROR_CODE_DEFAULT)) != ERROR_CODE_DEFAULT) {
            // Error in authorizationRequestEvent
            if (BuildConfig.DEBUG)
                Log.d(TAG, action.getStringExtra(ConstantsMCOP.AuthorizationRequestExtras.ERROR_STRING));
        } else {
            // No error
            String requestUri = null;
            String redirect = null;
            if ((requestUri = action.getStringExtra(ConstantsMCOP.AuthorizationRequestExtras.REQUEST_URI)) != null &&
                    (redirect = action.getStringExtra(ConstantsMCOP.AuthorizationRequestExtras.REDIRECT_URI)) != null
            ) {
                if (BuildConfig.DEBUG)
                    Log.d(TAG, "onAuthentication URI: " + requestUri + " redirectionURI: " + redirect);
                Intent intent2 = new Intent(CriticalAccess.getContext(), ScreenAuthenticationWebView.class);
                intent2.putExtra(ScreenAuthenticationWebView.DATA_URI_INTENT, requestUri.trim());
                intent2.putExtra(ScreenAuthenticationWebView.DATA_REDIRECTION_URI, redirect.trim());
            }
        }
    }
}
