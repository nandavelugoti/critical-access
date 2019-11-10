package org.test.client.mcopclient.controller;

import android.content.Intent;

import org.mcopenplatform.muoapi.IMCOPCallback;

import java.util.List;

public class MCOPCallbackManager extends IMCOPCallback.Stub {
    private final static String TAG = MCOPCallbackManager.class.getCanonicalName();
    private static final int ERROR_CODE_DEFAULT = -1;

    @Override
    public void handleOnEvent(final List<Intent> actionList) {
        for (Intent action : actionList) {
            if (action != null && action.getAction() != null && !action.getAction().trim().isEmpty())
                MCOPEventManger.handleEvent(action);
        }
    }
}
