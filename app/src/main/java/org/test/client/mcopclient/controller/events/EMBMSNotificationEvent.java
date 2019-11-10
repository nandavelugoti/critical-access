package org.test.client.mcopclient.controller.events;

import android.content.Intent;
import android.os.Build;
import android.util.Log;

import org.test.client.mcopclient.BuildConfig;
import org.test.client.mcopclient.ConstantsMCOP;

import static org.test.client.mcopclient.ConstantsMCOP.ERROR_CODE_DEFAULT;

public class EMBMSNotificationEvent implements EventListener {
    private final static String TAG = EMBMSNotificationEvent.class.getCanonicalName();

    @Override
    public void handleEvent(Intent action) {
        if (BuildConfig.DEBUG) Log.d(TAG, "Receipt eMBMS Notification Event");
        String TMGI = null;
        String areaList = null;
        int eventTypeInt = action.getIntExtra(ConstantsMCOP.EMBMSNotificationEventExtras.EVENT_TYPE, ERROR_CODE_DEFAULT);
        ConstantsMCOP.EMBMSNotificationEventExtras.EMBMSNotificationEventEventTypeEnum eventTypeEmbms = null;
        String sessionID = null;
        if (eventTypeInt != ERROR_CODE_DEFAULT &&
                (eventTypeEmbms = ConstantsMCOP.EMBMSNotificationEventExtras.EMBMSNotificationEventEventTypeEnum.fromInt(eventTypeInt)) != null) {
            switch (eventTypeEmbms) {
                case none:
                    break;
                case eMBMSAvailable:
                    if (BuildConfig.DEBUG) Log.d(TAG, "Receipt eMBMS available");
                    break;
                case UndereMBMSCoverage:
                    if (BuildConfig.DEBUG) Log.d(TAG, "Receipt eMBMS under coverage");
                    TMGI = action.getStringExtra(ConstantsMCOP.EMBMSNotificationEventExtras.TMGI);
                    sessionID = action.getStringExtra(ConstantsMCOP.EMBMSNotificationEventExtras.SESSION_ID);
                    areaList = action.getStringExtra(ConstantsMCOP.EMBMSNotificationEventExtras.AREA_LIST);
                    break;
                case eMBMSBearerInUse:
                    if (BuildConfig.DEBUG) Log.d(TAG, "Receipt eMBMS bearer in use");
                    TMGI = action.getStringExtra(ConstantsMCOP.EMBMSNotificationEventExtras.TMGI);
                    sessionID = action.getStringExtra(ConstantsMCOP.EMBMSNotificationEventExtras.SESSION_ID);
                    areaList = action.getStringExtra(ConstantsMCOP.EMBMSNotificationEventExtras.AREA_LIST);

                    break;
                case eMBMSBearerNotInUse:
                    if (BuildConfig.DEBUG) Log.d(TAG, "Receipt eMBMS bearer not in use");
                    TMGI = action.getStringExtra(ConstantsMCOP.EMBMSNotificationEventExtras.TMGI);
                    sessionID = action.getStringExtra(ConstantsMCOP.EMBMSNotificationEventExtras.SESSION_ID);
                    areaList = action.getStringExtra(ConstantsMCOP.EMBMSNotificationEventExtras.AREA_LIST);

                    break;
                case NoeMBMSCoverage:
                    if (BuildConfig.DEBUG) Log.d(TAG, "Receipt eMBMS not under coverage");
                    TMGI = action.getStringExtra(ConstantsMCOP.EMBMSNotificationEventExtras.TMGI);
                    sessionID = action.getStringExtra(ConstantsMCOP.EMBMSNotificationEventExtras.SESSION_ID);
                    areaList = action.getStringExtra(ConstantsMCOP.EMBMSNotificationEventExtras.AREA_LIST);
                    break;
                case eMBMSNotAvailable:
                    if (BuildConfig.DEBUG) Log.d(TAG, "Receipt eMBMS not available");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    }
                    break;
                default:
                    break;
            }
        }
    }
}