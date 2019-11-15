package org.test.client.mcopclient.model.calls;

import android.os.RemoteException;

import org.test.client.mcopclient.ConstantsMCOP;

public interface Callable {
    boolean call() throws RemoteException;

    boolean hangup() throws RemoteException;

    boolean updateState(ConstantsMCOP.EmergencyTypeEnum emergencyType) throws RemoteException;

    boolean floorControlOperation(ConstantsMCOP.FloorControlEventExtras.FloorControlOperationTypeEnum requestType, String UserID) throws RemoteException;
}
