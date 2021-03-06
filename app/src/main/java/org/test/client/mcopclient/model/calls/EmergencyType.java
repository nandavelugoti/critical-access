package org.test.client.mcopclient.model.calls;

import org.test.client.mcopclient.ConstantsMCOP;

public enum EmergencyType {
    None(ConstantsMCOP.CallEventExtras.CallTypeEnum.None),
    Emergency(ConstantsMCOP.CallEventExtras.CallTypeEnum.Emergency),
    ImminentPeril(ConstantsMCOP.CallEventExtras.CallTypeEnum.Emergency),
    EmergencyAlert(ConstantsMCOP.CallEventExtras.CallTypeEnum.Emergency);

    ConstantsMCOP.CallEventExtras.CallTypeEnum emergencyType;

    EmergencyType(ConstantsMCOP.CallEventExtras.CallTypeEnum emergencyType) {
        this.emergencyType = emergencyType;
    }

    public ConstantsMCOP.CallEventExtras.CallTypeEnum getCallTypeEnum() {
        return emergencyType;
    }

    public int getValue() {
        return emergencyType.getValue();
    }
}
