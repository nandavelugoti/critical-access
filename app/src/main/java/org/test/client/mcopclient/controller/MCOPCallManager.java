package org.test.client.mcopclient.controller;

import org.test.client.mcopclient.ConstantsMCOP;
import org.test.client.mcopclient.model.calls.Call;

import java.util.List;

public class MCOPCallManager {
    private final static String TAG = MCOPCallManager.class.getCanonicalName();
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
