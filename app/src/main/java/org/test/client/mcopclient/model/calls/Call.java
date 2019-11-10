package org.test.client.mcopclient.model.calls;

import android.os.RemoteException;

import org.test.client.mcopclient.controller.MCOPServiceManager;

public class Call implements Callable {
    private String calleeId;
    private String sessionId;
    private CallConfig callConfig;

    Call(String id, CallConfig callConfig) {
        this.calleeId = id;
        this.callConfig = callConfig;
    }

    @Override
    public boolean call() throws RemoteException {
        return MCOPServiceManager.getService().makeCall(calleeId, callConfig.getCallTypeEnumValue());
    }

    @Override
    public boolean hangup() throws RemoteException {
        return MCOPServiceManager.getService().hangUpCall(sessionId);
    }

    @Override
    public boolean updateState(EmergencyType emergencyType) throws RemoteException {
        return MCOPServiceManager.getService().updateEmergencyState(sessionId, emergencyType.getValue());
    }

    @Override
    public boolean floorControlOperation(int requestType, String userID) throws RemoteException {
        return MCOPServiceManager.getService().floorControlOperation(sessionId, requestType, userID);
    }
}