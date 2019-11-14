package org.test.client.mcopclient.model.calls;

import org.test.client.mcopclient.ConstantsMCOP;

enum FloorControlType {
    WithFloorCtrl(ConstantsMCOP.CallEventExtras.CallTypeEnum.WithFloorCtrl),
    WithoutFloorCtrl(ConstantsMCOP.CallEventExtras.CallTypeEnum.WithoutFloorCtrl);

    ConstantsMCOP.CallEventExtras.CallTypeEnum floorControlType;

    FloorControlType(ConstantsMCOP.CallEventExtras.CallTypeEnum floorControlType) {
        this.floorControlType = floorControlType;
    }

    public ConstantsMCOP.CallEventExtras.CallTypeEnum getCallTypeEnum() {
        return floorControlType;
    }

    public int getValue() {
        return floorControlType.getValue();
    }

}
