package org.test.client.mcopclient.controller;

import android.os.RemoteException;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import org.test.client.mcopclient.BuildConfig;
import org.test.client.mcopclient.ConstantsMCOP;
import org.test.client.mcopclient.R;
import org.test.client.mcopclient.model.calls.Call;
import org.test.client.mcopclient.model.calls.CallConfig;

import java.util.List;

public class MCOPCallManager {
    private final static String TAG = MCOPCallManager.class.getCanonicalName();
    private static List<Call> currentCalls = null;
    private MCOPServiceManager serviceManager = null;

    public static void makeCall(String id, CallConfig callConfig) {
        Call tempcall = new Call(id, callConfig);
        try {
            tempcall.call();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        currentCalls.add(tempcall);
    }

    public void hangup(int hangupIndex) {
        Call tempcall = currentCalls.get(hangupIndex);
        try {
            tempcall.hangup();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        currentCalls.remove(hangupIndex);
    }

    public static void startERState() {
        Log.d(TAG,"Start Emergency State");

    }

    public static void endERState() {
        Log.d(TAG,"End Emergency State");

    }

    public static void destroy() {

    }
}
