package org.test.client.mcopclient.controller;

import org.test.client.mcopclient.model.User;
import org.test.client.mcopclient.model.calls.Call;

import java.util.ArrayList;
import java.util.List;

public class CallManager {

    public List<Call> callList = new ArrayList<>();

    CallManager() {

    }

    public void makecall(){

        Call tempcall();
        tempcall.call();
        callList.add(tempcall);

    }

    public void hangup(int hangupIndex) {
        Call tempcall = callList.get(hangupIndex);
        tempcall.hangup();
        callList.remove(hangupIndex);
    }
}
