package org.test.client.mcopclient.model.calls;

import android.os.RemoteException;

import org.test.client.mcopclient.ConstantsMCOP;
import org.test.client.mcopclient.controller.MCOPServiceManager;

public class Call implements Callable {
    public String getId() {
        return calleeId;
    }

    private String calleeId;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public CallConfig getCallConfig() {
        return callConfig;
    }

    public void setCallConfig(CallConfig callConfig) {
        this.callConfig = callConfig;
    }

    private String sessionId;
    private CallConfig callConfig;

    public Call(String id, CallConfig callConfig) {
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
    public boolean updateState(ConstantsMCOP.EmergencyTypeEnum emergencyType) throws RemoteException {
        return MCOPServiceManager.getService().updateEmergencyState(sessionId, emergencyType.getValue());
    }

    @Override
    public boolean floorControlOperation(ConstantsMCOP.FloorControlEventExtras.FloorControlOperationTypeEnum requestType, String userID) throws RemoteException {
        return MCOPServiceManager.getService().floorControlOperation(sessionId, requestType.getValue(), userID);
    }
}