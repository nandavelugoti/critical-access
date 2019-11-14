package org.test.client.mcopclient.controller;

import android.os.RemoteException;

import org.test.client.mcopclient.ConstantsMCOP;
import org.test.client.mcopclient.model.Group;
import org.test.client.mcopclient.model.User;
import org.test.client.mcopclient.model.calls.Call;
import org.test.client.mcopclient.model.calls.CallConfig;
import org.test.client.mcopclient.model.calls.EmergencyType;
import org.test.client.mcopclient.model.calls.StatusTokenType;
import org.test.client.mcopclient.view.DialogMenu;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MCOPCallManager {
    private final static String TAG = MCOPCallManager.class.getCanonicalName();
    private static Set<Call> allCalls = null;
    private static Call currentCall;
    private static Map<String, Call> sessionCallMap = new HashMap<>();
    private static boolean isERState = false;
    private static StatusTokenType currentStatusToken = StatusTokenType.NONE;

    public static void makePrivateCall(User user, CallConfig callConfig) {
        currentCall = new Call(user.getMcpttID(), callConfig);
        allCalls.add(currentCall);
        makeCurrentCall();
    }

    public static void makeGroupCall(Group group, CallConfig config) {
        currentCall = new Call(group.getMcpttID(), config);
        allCalls.add(currentCall);
        makeCurrentCall();
    }

    private static void makeCurrentCall() {
        try {
            currentCall.call();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void updateState(EmergencyType emergencyType) {
        try {
            currentCall.updateState(emergencyType);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void mapSessionToCall(String sessionId, String callerId) {
        if(!sessionCallMap.containsKey(sessionId)) {
            Call sessionCall = null;
            for (Call call : allCalls) {
                if (call.getId().equals(callerId))
                    sessionCall = call;
            }
            if (sessionCall != null) {
                sessionCall.setSessionId(sessionId);
                sessionCallMap.put(sessionId, sessionCall);
            }
        }
    }

    public static void floorControlOperation(boolean request) {
        try {
            String[] sessionIds = new String[sessionCallMap.size()];
            sessionCallMap.keySet().toArray(sessionIds);
            final int[] index = {0};

            if(sessionCallMap.size() > 1) {
                DialogMenu mDialogIds = DialogMenu.newInstance(sessionIds, null);
                mDialogIds.setOnClickItemListener(new DialogMenu.OnClickListener() {
                    @Override
                    public void onClickItem(int item) {
                        index[0] = item;
                    }
                });
                //mDialogIds.show(CriticalAccess.getContext().getSupportFragmentManager(), "SimpleDialog");
            }
            Call selectedCall = sessionCallMap.get(sessionIds[index[0]]);
            if(selectedCall != null)
                selectedCall.floorControlOperation(request ? ConstantsMCOP.FloorControlEventExtras.
                            FloorControlOperationTypeEnum.MCPTT_Request.getValue() : ConstantsMCOP.
                            FloorControlEventExtras.FloorControlOperationTypeEnum.MCPTT_Release.getValue(),
                    null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private static void startERState() {

    }

    private static void endERState() {

    }

    public static StatusTokenType getCurrentStatusToken() {
        return currentStatusToken;
    }

    public static boolean toggleERState() {
        isERState=!isERState;
        if (isERState) {
            startERState();
        } else {
            endERState();
        }
        return isERState;
    }

    public static void hangup(Group group) {
        for(Call call : allCalls) {
            if(call.getId().equals(group.getMcpttID())) {
                try {
                    call.hangup();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
