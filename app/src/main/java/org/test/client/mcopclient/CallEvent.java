package org.test.client.mcopclient;

import org.test.client.mcopclient.ConstantsMCOP.CallEventExtras.CallTypeEnum;

public class CallEvent{
    public enum CallTypeValidEnum {
        NONE(0),
        AudioWithoutFloorCtrlPrivate(CallTypeEnum.Audio.getValue() | CallTypeEnum.WithoutFloorCtrl.getValue()| CallTypeEnum.Private.getValue()),
        AudioWithFloorCtrlPrivate(CallTypeEnum.Audio.getValue() | CallTypeEnum.WithFloorCtrl.getValue()| CallTypeEnum.Private.getValue()),
        AudioWithFloorCtrlPrivateEmergency (CallTypeEnum.Audio.getValue() | CallTypeEnum.WithFloorCtrl.getValue()| CallTypeEnum.Private.getValue()| CallTypeEnum.Emergency.getValue()),
        AudioWithFloorCtrlPrearrangedGroupEmergency (CallTypeEnum.Audio.getValue() | CallTypeEnum.WithFloorCtrl.getValue()| CallTypeEnum.PrearrangedGroup.getValue()| CallTypeEnum.Emergency.getValue()),
        AudioWithFloorCtrlPrearrangedGroupImminentPeril (CallTypeEnum.Audio.getValue() | CallTypeEnum.WithFloorCtrl.getValue()| CallTypeEnum.PrearrangedGroup.getValue()| CallTypeEnum.ImminentPeril.getValue()),
        AudioWithFloorCtrlPrearrangedGroup(CallTypeEnum.Audio.getValue() | CallTypeEnum.WithFloorCtrl.getValue()| CallTypeEnum.PrearrangedGroup.getValue()),
        AudioWithFloorCtrlChatGroupEmergency (CallTypeEnum.Audio.getValue() | CallTypeEnum.WithFloorCtrl.getValue()| CallTypeEnum.ChatGroup.getValue()| CallTypeEnum.Emergency.getValue()),
        AudioWithFloorCtrlChatGroupImminentPeril (CallTypeEnum.Audio.getValue() | CallTypeEnum.WithFloorCtrl.getValue()| CallTypeEnum.ChatGroup.getValue()| CallTypeEnum.ImminentPeril.getValue()),
        AudioWithFloorCtrlChatGroup(CallTypeEnum.Audio.getValue() | CallTypeEnum.WithFloorCtrl.getValue()| CallTypeEnum.ChatGroup.getValue()),
        AudioWithFloorCtrlBroadcastpEmergency (CallTypeEnum.Audio.getValue() | CallTypeEnum.WithFloorCtrl.getValue()| CallTypeEnum.Broadcast.getValue()| CallTypeEnum.Emergency.getValue()),
        AudioWithFloorCtrlBroadcastImminentPeril (CallTypeEnum.Audio.getValue() | CallTypeEnum.WithFloorCtrl.getValue()| CallTypeEnum.Broadcast.getValue()| CallTypeEnum.ImminentPeril.getValue()),
        AudioWithFloorCtrlBroadcast(CallTypeEnum.Audio.getValue() | CallTypeEnum.WithFloorCtrl.getValue()| CallTypeEnum.Broadcast.getValue()),
        AudioWithFloorCtrlFirstToAnswer(CallTypeEnum.Audio.getValue() | CallTypeEnum.WithFloorCtrl.getValue()| CallTypeEnum.FirstToAnswer.getValue()),
        AudioWithFloorCtrlPrivateCallCallback(CallTypeEnum.Audio.getValue() | CallTypeEnum.WithFloorCtrl.getValue()| CallTypeEnum.PrivateCallCallback.getValue()),
        AudioWithFloorCtrlRemoteAmbientListening(CallTypeEnum.Audio.getValue() | CallTypeEnum.WithFloorCtrl.getValue()| CallTypeEnum.RemoteAmbientListening.getValue()),
        AudioWithFloorCtrlLocalAmbientListening(CallTypeEnum.Audio.getValue() | CallTypeEnum.WithFloorCtrl.getValue()| CallTypeEnum.LocalAmbientListening.getValue()),
        VideoAudioWithFloorCtrlPrivate(CallTypeEnum.Video.getValue() | CallTypeEnum.Audio.getValue() | CallTypeEnum.WithFloorCtrl.getValue()| CallTypeEnum.Private.getValue()),
        VideoAudioWithFloorCtrlPrivateEmergency (CallTypeEnum.Video.getValue() |CallTypeEnum.Audio.getValue() | CallTypeEnum.WithFloorCtrl.getValue()| CallTypeEnum.Private.getValue()| CallTypeEnum.Emergency.getValue()),
        VideoAudioWithFloorCtrlPrearrangedGroupEmergency (CallTypeEnum.Video.getValue() |CallTypeEnum.Audio.getValue() | CallTypeEnum.WithFloorCtrl.getValue()| CallTypeEnum.PrearrangedGroup.getValue()| CallTypeEnum.Emergency.getValue()),
        VideoAudioWithFloorCtrlPrearrangedGroupImminentPeril (CallTypeEnum.Video.getValue() |CallTypeEnum.Audio.getValue() | CallTypeEnum.WithFloorCtrl.getValue()| CallTypeEnum.PrearrangedGroup.getValue()| CallTypeEnum.ImminentPeril.getValue()),
        VideoAudioWithFloorCtrlPrearrangedGroup(CallTypeEnum.Video.getValue() |CallTypeEnum.Audio.getValue() | CallTypeEnum.WithFloorCtrl.getValue()| CallTypeEnum.PrearrangedGroup.getValue()),
        VideoAudioWithFloorCtrlChatGroupEmergency (CallTypeEnum.Video.getValue() |CallTypeEnum.Audio.getValue() | CallTypeEnum.WithFloorCtrl.getValue()| CallTypeEnum.ChatGroup.getValue()| CallTypeEnum.Emergency.getValue()),
        VideoAudioWithFloorCtrlChatGroupImminentPeril (CallTypeEnum.Video.getValue() |CallTypeEnum.Audio.getValue() | CallTypeEnum.WithFloorCtrl.getValue()| CallTypeEnum.ChatGroup.getValue()| CallTypeEnum.ImminentPeril.getValue()),
        VideoAudioWithFloorCtrlChatGroup(CallTypeEnum.Video.getValue() |CallTypeEnum.Audio.getValue() | CallTypeEnum.WithFloorCtrl.getValue()| CallTypeEnum.ChatGroup.getValue()),
        VideoAudioWithFloorCtrlBroadcastpEmergency (CallTypeEnum.Video.getValue() |CallTypeEnum.Audio.getValue() | CallTypeEnum.WithFloorCtrl.getValue()| CallTypeEnum.Broadcast.getValue()| CallTypeEnum.Emergency.getValue()),
        VideoAudioWithFloorCtrlBroadcastImminentPeril (CallTypeEnum.Video.getValue() |CallTypeEnum.Audio.getValue() | CallTypeEnum.WithFloorCtrl.getValue()| CallTypeEnum.Broadcast.getValue()| CallTypeEnum.ImminentPeril.getValue()),
        VideoAudioWithFloorCtrlBroadcast(CallTypeEnum.Video.getValue() |CallTypeEnum.Audio.getValue() | CallTypeEnum.WithFloorCtrl.getValue()| CallTypeEnum.Broadcast.getValue()),
        VideoAudioWithFloorCtrlFirstToAnswer(CallTypeEnum.Video.getValue() |CallTypeEnum.Audio.getValue() | CallTypeEnum.WithFloorCtrl.getValue()| CallTypeEnum.FirstToAnswer.getValue()),
        VideoAudioWithFloorCtrlPrivateCallCallback(CallTypeEnum.Video.getValue() |CallTypeEnum.Audio.getValue() | CallTypeEnum.WithFloorCtrl.getValue()| CallTypeEnum.PrivateCallCallback.getValue()),
        VideoAudioWithFloorCtrlRemoteAmbientListening(CallTypeEnum.Video.getValue() |CallTypeEnum.Audio.getValue() | CallTypeEnum.WithFloorCtrl.getValue()| CallTypeEnum.RemoteAmbientListening.getValue()),
        VideoAudioWithFloorCtrlLocalAmbientListening(CallTypeEnum.Video.getValue() | CallTypeEnum.Audio.getValue() | CallTypeEnum.WithFloorCtrl.getValue()| CallTypeEnum.LocalAmbientListening.getValue());
        private int code;
        CallTypeValidEnum(int code) {
            this.code = code;
        }
        public int getValue() {
            return code;
        }
    }

    public static CallEvent.CallTypeValidEnum validationCallType(int type){
        if(type<=0)return null;
        for(CallEvent.CallTypeValidEnum data:CallEvent.CallTypeValidEnum.values()){
            if(data.getValue()==type) {
                return data;
            }
        }
        return null;
    }
}