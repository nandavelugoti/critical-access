package org.test.client.mcopclient.controller;

import android.os.RemoteException;

import org.test.client.mcopclient.ConstantsMCOP;
import org.test.client.mcopclient.model.Group;
import org.test.client.mcopclient.model.User;
import org.test.client.mcopclient.model.calls.Call;
import org.test.client.mcopclient.model.calls.CallConfig;
import org.test.client.mcopclient.model.calls.CallType;
import org.test.client.mcopclient.model.calls.EmergencyType;
import org.test.client.mcopclient.model.calls.FloorControlType;
import org.test.client.mcopclient.model.calls.MediaType;
import org.test.client.mcopclient.model.calls.StatusTokenType;
import org.test.client.mcopclient.view.DialogMenu;
import org.test.client.mcopclient.view.HomePage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MCOPCallManager {
    private final static String TAG = MCOPCallManager.class.getCanonicalName();
    private static Set<Call> allCalls = new HashSet<>();

    public static Call getCurrentCall() {
        return currentCall;
    }

    private static Call currentCall;
    private static Map<String, Call> sessionCallMap = new HashMap<>();
    private static Group currentGroup;
    public static Group getCurrentGroup() {
        return  currentGroup;
    }

    public static void setCurrentGroup(Group group) {
        currentGroup = group;
    }

    public static User getTokenHolder() {
        return tokenHolder;
    }

    public static void setTokenHolder(User tokenHolder) {
        MCOPCallManager.tokenHolder = tokenHolder;
    }

    private static User tokenHolder;

    public static void setCurrentStatusToken(StatusTokenType currentStatusToken) {
        MCOPCallManager.currentStatusToken = currentStatusToken;
    }

    private static StatusTokenType currentStatusToken = StatusTokenType.NONE;

    private static boolean isSpeakerphoneOn = false;
    private static boolean isAmbientOn = false;
    private static boolean isVideoCall = false;
    private static boolean isERState = false;
    private static boolean isIPState = false;

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

    public static void updateState(ConstantsMCOP.EmergencyTypeEnum emergencyType) {
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
            if(sessionCall == null) {
                sessionCall = new Call(callerId, null);
            }
            sessionCall.setSessionId(sessionId);
            sessionCallMap.put(sessionId, sessionCall);
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
                            FloorControlOperationTypeEnum.MCPTT_Request : ConstantsMCOP.
                            FloorControlEventExtras.FloorControlOperationTypeEnum.MCPTT_Release,
                    null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public static void startERState() {
        MCOPCallManager.updateState(ConstantsMCOP.EmergencyTypeEnum.EMERGENCY);
        HomePage.updateERUI();
    }

    public static void endERState() {
        MCOPCallManager.updateState(ConstantsMCOP.EmergencyTypeEnum.EMERGENCY);
        HomePage.updateERUI();
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

    public static void hangup(String id) {
        for(Call call : allCalls) {
            if(call.getId().equals(id)) {
                try {
                    call.hangup();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static boolean getIsSpeakerphoneOn() {
        return isSpeakerphoneOn;
    }

    public static void setIsSpeakerphoneOn(boolean isSpeakerphoneOn) {
        MCOPCallManager.isSpeakerphoneOn = isSpeakerphoneOn;
    }

    public static boolean getIsAmbientOn() {
        return isAmbientOn;
    }

    public static void setIsAmbientOn(boolean isAmbientOn) {
        MCOPCallManager.isAmbientOn = isAmbientOn;
    }

    public static boolean getIsRecordingCall() {
        return isVideoCall;
    }

    public static void setIsVideoCall(boolean isVideoCall) {
        MCOPCallManager.isVideoCall = isVideoCall;
    }

    public static boolean getIsERState() {
        return isERState;
    }

    public static void setIsERState(boolean isERState) {
        MCOPCallManager.isERState = isERState;
    }

    public static boolean getIsIPState() {
        return isIPState;
    }

    public static void setIsIPState(boolean isIPState) {
        MCOPCallManager.isIPState = isIPState;
    }

    public static void toggleRecordCall() {
        isVideoCall = !isVideoCall;
    }

    public static void togglePerilCall() {
        isIPState = !isIPState;
    }

    public static void toggleSpeaker() {
        isSpeakerphoneOn = !isSpeakerphoneOn;
    }

    public static CallConfig getCallConfig() {
        CallConfig currentConfig = null;
        MediaType mediaType = isVideoCall ? MediaType.Video : MediaType.Audio;
        FloorControlType floorControlType = FloorControlType.WithFloorCtrl;
        EmergencyType emergencyType = isERState ? EmergencyType.Emergency : EmergencyType.None;
        if(isIPState)
            emergencyType = EmergencyType.ImminentPeril;
        CallType callType = CallType.PrearrangedGroup;
        currentConfig = new CallConfig(mediaType, floorControlType, emergencyType, callType);
        return  currentConfig;
    }

    public static void toggleAmbientCall() {
        isAmbientOn = !isAmbientOn;
    }
}
