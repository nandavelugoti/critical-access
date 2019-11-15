package org.test.client.mcopclient.controller;

import android.content.Context;
import android.media.AudioManager;

import org.test.client.mcopclient.CriticalAccess;

public class MCOPAudioManager {
    private final static String TAG = MCOPAudioManager.class.getCanonicalName();

    public static void setSpeakerphoneOn() {
        AudioManager mAudioManager = (AudioManager) CriticalAccess.getContext().getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.setSpeakerphoneOn(true);
    }

    public static void setSpeakerphoneOff() {
        AudioManager mAudioManager = (AudioManager) CriticalAccess.getContext().getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.setSpeakerphoneOn(false);
    }
}
