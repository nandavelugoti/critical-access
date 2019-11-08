package org.test.client.mcopclient.model.calls;

import org.test.client.mcopclient.model.ConstantsMCOP;

enum CallType {
    None (ConstantsMCOP.CallEventExtras.CallTypeEnum.None),
    Private (ConstantsMCOP.CallEventExtras.CallTypeEnum.Private),
    Broadcast (ConstantsMCOP.CallEventExtras.CallTypeEnum.Broadcast),
    PrearrangedGroup (ConstantsMCOP.CallEventExtras.CallTypeEnum.PrearrangedGroup),
    ChatGroup (ConstantsMCOP.CallEventExtras.CallTypeEnum.ChatGroup),
    FirstToAnswer (ConstantsMCOP.CallEventExtras.CallTypeEnum.FirstToAnswer), //REL 14
    PrivateCallCallback (ConstantsMCOP.CallEventExtras.CallTypeEnum.PrivateCallCallback), //REL 14
    RemoteAmbientListening (ConstantsMCOP.CallEventExtras.CallTypeEnum.RemoteAmbientListening), //REL 14
    LocalAmbientListening (ConstantsMCOP.CallEventExtras.CallTypeEnum.LocalAmbientListening); //REL 14
    ConstantsMCOP.CallEventExtras.CallTypeEnum callType;

    CallType(ConstantsMCOP.CallEventExtras.CallTypeEnum callType) {
        this.callType = callType;
    }

    public ConstantsMCOP.CallEventExtras.CallTypeEnum getCallTypeEnum() {
        return callType;
    }

    public int getValue() {
        return callType.getValue();
    }
}
