package org.test.client.mcopclient.controller;

import android.content.Intent;
import android.util.Log;

import org.test.client.mcopclient.ConstantsMCOP;
import org.test.client.mcopclient.ConstantsMCOP.ActionsCallBack;
import org.test.client.mcopclient.controller.events.AuthorizationRequestEvent;
import org.test.client.mcopclient.controller.events.CallEvent;
import org.test.client.mcopclient.controller.events.ConfigurationUpdateEvent;
import org.test.client.mcopclient.controller.events.EMBMSNotificationEvent;
import org.test.client.mcopclient.controller.events.EventListener;
import org.test.client.mcopclient.controller.events.FloorControlEvent;
import org.test.client.mcopclient.controller.events.GroupAffiliationEvent;
import org.test.client.mcopclient.controller.events.GroupInfoEvent;
import org.test.client.mcopclient.controller.events.LoginEvent;
import org.test.client.mcopclient.controller.events.SelectedContactChangeEvent;
import org.test.client.mcopclient.controller.events.UnLoginEvent;

import java.util.Objects;

public class MCOPEventManager {
    private final static String TAG = MCOPEventManager.class.getCanonicalName();

    private static AuthorizationRequestEvent mAuthorizationRequestEvent = new AuthorizationRequestEvent();
    private static CallEvent mCallEvent = new CallEvent();
    private static ConfigurationUpdateEvent mConfigurationUpdateEvent = new ConfigurationUpdateEvent();
    private static EMBMSNotificationEvent mEMBMSNotificationEvent = new EMBMSNotificationEvent();
    private static FloorControlEvent mFloorControlEvent = new FloorControlEvent();
    private static GroupInfoEvent mGroupInfoEvent = new GroupInfoEvent();
    private static GroupAffiliationEvent mGroupAffiliationEvent = new GroupAffiliationEvent();
    private static LoginEvent mLoginEvent = new LoginEvent();
    private static SelectedContactChangeEvent mSelectedContactChangeEvent = new SelectedContactChangeEvent();
    private static UnLoginEvent mUnLoginEvent = new UnLoginEvent();

    public static void handleEvent(Intent action) {
        try {
            ActionsCallBack eventType = ConstantsMCOP.ActionsCallBack.fromString(action.getAction());
            EventListener event = getEvent(Objects.requireNonNull(eventType));
            if (event != null)
                event.handleEvent(action);
        } catch (Exception ex) {
            Log.e(TAG, "Event Action Error: " + action.getAction() + " error:" + ex.getMessage());
        }
    }

    private static EventListener getEvent(ActionsCallBack eventType) {
        switch (eventType) {
            case authorizationRequestEvent:
                return mAuthorizationRequestEvent;
            case loginEvent:
                return mLoginEvent;
            case unLoginEvent:
                return mUnLoginEvent;
            case configurationUpdateEvent:
                return mConfigurationUpdateEvent;
            case callEvent:
                return mCallEvent;
            case floorControlEvent:
                return mFloorControlEvent;
            case groupInfoEvent:
                return mGroupInfoEvent;
            case groupAffiliationEvent:
                return mGroupAffiliationEvent;
            case selectedContactChangeEvent:
                return mSelectedContactChangeEvent;
            case eMBMSNotificationEvent:
                return mEMBMSNotificationEvent;
            case none:
            default:
                return null;
        }
    }
}
