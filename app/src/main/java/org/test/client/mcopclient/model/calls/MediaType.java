package org.test.client.mcopclient.model.calls;

import org.test.client.mcopclient.ConstantsMCOP;

enum MediaType {
    Audio (ConstantsMCOP.CallEventExtras.CallTypeEnum.Audio),
    Video (ConstantsMCOP.CallEventExtras.CallTypeEnum.Video),
    Data (ConstantsMCOP.CallEventExtras.CallTypeEnum.Data);

    private ConstantsMCOP.CallEventExtras.CallTypeEnum mediaType;

    MediaType(ConstantsMCOP.CallEventExtras.CallTypeEnum mediaType) {
        this.mediaType = mediaType;
    }

    public ConstantsMCOP.CallEventExtras.CallTypeEnum getCallTypeEnum() {
        return mediaType;
    }

    public int getValue() {
        return mediaType.getValue();
    }
}