package org.test.client.mcopclient.controller;

import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import org.test.client.mcopclient.R;
import org.test.client.mcopclient.model.ConstantsMCOP;
import org.test.client.mcopclient.model.calls.Call;

import java.util.List;

public class CallManager {
    private final static String TAG = CallManager.class.getCanonicalName();
    private static List<Call> currentCalls = null;
    private MCOPServiceManager serviceManager = null;

    public static void makeCall(String id, ConstantsMCOP.CallEventExtras.CallTypeEnum callType) {
    }


    public static void startERState() {

    }

    public static void endERState() {

    }

    public static void destroy() {

    }
}
