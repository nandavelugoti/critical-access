package org.test.client.mcopclient.model.calls;

import android.os.RemoteException;

public interface Callable {
    boolean call() throws RemoteException;

    boolean hangup() throws RemoteException;

    boolean updateState(EmergencyType emergencyType) throws RemoteException;

    boolean floorControlOperation(int requestType, String UserID) throws RemoteException;
}
