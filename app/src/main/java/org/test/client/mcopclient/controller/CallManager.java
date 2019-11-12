package org.test.client.mcopclient.controller;


import org.test.client.mcopclient.model.ConstantsMCOP;
import org.test.client.mcopclient.model.User;
import org.test.client.mcopclient.model.calls.Call;
import org.test.client.mcopclient.model.calls.CallConfig;
import org.test.client.mcopclient.model.calls.CallType;
import org.test.client.mcopclient.model.calls.EmergencyType;
import org.test.client.mcopclient.model.calls.FloorControlType;
import org.test.client.mcopclient.model.calls.MediaType;

import java.util.ArrayList;
import java.util.List;

public class CallManager {

    public List<Call> callList = new ArrayList<>();
    private final static String TAG = CallManager.class.getCanonicalName();
    private static List<Call> currentCalls = null;
    private MCOPServiceManager serviceManager = null;


    public static void startERState() {

    }

    public static void endERState() {

    }

    public static void destroy() {

    }

    public void makecall(String id, CallConfig callConfig){
        CallType callType;
        MediaType mediaType;
        FloorControlType floorControlType;
        EmergencyType emergencyType;

//        callType = callConfig.getCallType();
//        mediaType = callConfig.getMediaType();
//        floorControlType = callConfig.getFloorControlType();
//        emergencyType = callConfig.getEmergencyType();

        Call tempcall(id, callConfig);
        tempcall.call();
        callList.add(tempcall);

    }

    public void hangup(int hangupIndex) {
        Call tempcall = callList.get(hangupIndex);
        tempcall.hangup();
        callList.remove(hangupIndex);
    }
}
