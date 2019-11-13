package org.test.client.mcopclient.model.calls;

import android.os.RemoteException;

import org.test.client.mcopclient.ConstantsMCOP;
import org.test.client.mcopclient.controller.MCOPServiceManager;

public class Call implements Callable {
    private String calleeId;
    private String sessionId;
    private CallConfig callConfig;

    Call() {}

    public Call(String id, CallConfig callConfig) {
        this.calleeId = id;
        this.callConfig = new CallConfig(callConfig.getMediaType(),callConfig.getFloorControlType(),callConfig.getEmergencyType(),callConfig.getCallType());
        this.sessionId = ConstantsMCOP.CallEventExtras.SESSION_ID;
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