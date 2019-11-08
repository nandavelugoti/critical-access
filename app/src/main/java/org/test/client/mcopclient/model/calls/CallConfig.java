package org.test.client.mcopclient.model.calls;

import org.test.client.mcopclient.model.ConstantsMCOP.CallEventExtras.CallTypeEnum;

import java.util.ArrayList;
import java.util.List;

enum MediaType {
    Audio (CallTypeEnum.Audio),
    Video (CallTypeEnum.Video),
    Data (CallTypeEnum.Data);

    private CallTypeEnum mediaType;

    MediaType(CallTypeEnum mediaType) {
        this.mediaType = mediaType;
    }

    public CallTypeEnum getCallTypeEnum() {
        return mediaType;
    }

    public int getValue() {
        return mediaType.getValue();
    }
}

enum FloorControlType {
    WithFloorCtrl (CallTypeEnum.WithFloorCtrl),
    WithoutFloorCtrl (CallTypeEnum.WithoutFloorCtrl);

    CallTypeEnum floorControlType;

    FloorControlType(CallTypeEnum floorControlType) {
        this.floorControlType = floorControlType;
    }

    public CallTypeEnum getCallTypeEnum() {
        return floorControlType;
    }

    public int getValue() {
        return floorControlType.getValue();
    }

}

enum EmergencyType {
    Emergency (CallTypeEnum.Emergency),
    ImminentPeril (CallTypeEnum.Emergency),
    EmergencyAlert (CallTypeEnum.Emergency);

    CallTypeEnum emergencyType;

    EmergencyType(CallTypeEnum emergencyType) {
        this.emergencyType = emergencyType;
    }

    public CallTypeEnum getCallTypeEnum() {
        return emergencyType;
    }

    public int getValue() {
        return emergencyType.getValue();
    }
}

enum CallType {
    None (CallTypeEnum.None),
    Private (CallTypeEnum.Private),
    Broadcast (CallTypeEnum.Broadcast),
    PrearrangedGroup (CallTypeEnum.PrearrangedGroup),
    ChatGroup (CallTypeEnum.ChatGroup),
    FirstToAnswer (CallTypeEnum.FirstToAnswer), //REL 14
    PrivateCallCallback (CallTypeEnum.PrivateCallCallback), //REL 14
    RemoteAmbientListening (CallTypeEnum.RemoteAmbientListening), //REL 14
    LocalAmbientListening (CallTypeEnum.LocalAmbientListening); //REL 14
    CallTypeEnum callType;

    CallType(CallTypeEnum callType) {
        this.callType = callType;
    }

    public CallTypeEnum getCallTypeEnum() {
        return callType;
    }

    public int getValue() {
        return callType.getValue();
    }
}

public class CallConfig {
    private CallType callType;
    private MediaType mediaType;
    private FloorControlType floorControlType;
    private EmergencyType emergencyType;

    CallConfig(MediaType mediaType, FloorControlType floorControlType, EmergencyType emergencyType, CallType callType) {
        this.mediaType = mediaType;
        this.floorControlType = floorControlType;
        this.emergencyType = emergencyType;
        this.callType = callType;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public FloorControlType getFloorControlType() {
        return floorControlType;
    }

    public EmergencyType getEmergencyType() {
        return emergencyType;
    }

    public CallType getCallType() {
        return callType;
    }

    public int getCallTypeEnum() {
        return  callType.getValue() | mediaType.getValue() | floorControlType.getValue() | emergencyType.getValue();
        //callType.getCallTypeEnum() | mediaType.getCallTypeEnum() | floorControlType.getCallTypeEnum() | emergencyType.getCallTypeEnum()
    }
}
