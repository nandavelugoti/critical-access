package org.test.client.mcopclient.model.calls;

public class CallConfig {
    private CallType callType;
    private MediaType mediaType;
    private FloorControlType floorControlType;
    private EmergencyType emergencyType;

    public CallConfig(MediaType mediaType, FloorControlType floorControlType, EmergencyType emergencyType, CallType callType) {
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
        return callType.getValue() | mediaType.getValue() | floorControlType.getValue() | emergencyType.getValue();
    }
}
