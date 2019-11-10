package org.test.client.mcopclient.model.calls;

import org.test.client.mcopclient.model.ConstantsMCOP.CallEventExtras.CallTypeEnum;

import java.util.ArrayList;
import java.util.List;

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

    public int getCallTypeEnumValue() {
        return  callType.getValue() | mediaType.getValue() | floorControlType.getValue() | emergencyType.getValue();
    }
}
