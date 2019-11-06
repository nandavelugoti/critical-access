package org.test.client.mcopclient.controller;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import org.mcopenplatform.muoapi.IMCOPCallback;
import org.mcopenplatform.muoapi.IMCOPsdk;
import org.test.client.mcopclient.BuildConfig;
import org.test.client.mcopclient.CriticalAccess;
import org.test.client.mcopclient.model.ConstantsMCOP;
import org.test.client.mcopclient.model.User;
import org.test.client.mcopclient.view.MainActivity;

public class MCOPServiceManager {
    private final static String TAG = MCOPServiceManager.class.getCanonicalName();

    private static ServiceConnection mConnection;
    private static IMCOPsdk mService;
    private static IMCOPCallback mMCOPCallback;
    private boolean mERState = false;
    private boolean isConnect=false;
    private static final int ERROR_CODE_DEFAULT=-1;
    private static final int AUTHETICATION_RESULT=101;
    private static final int GET_PERMISSION = 102;
    private static final boolean VALUE_BOOLEAN_DEFAULT=false;
    private static User user;
    private static final int DEFAULT_REGISTER_DELAY = 3000;
    private boolean registered = false;
    private enum State {
        GRANTED,
        IDLE,
        TAKEN,
        NONE
    }
    private State mState = State.NONE;
    private enum CallType {
        PRIVATE,
        GROUP
    }
    private CallType mCallType = CallType.GROUP;

    private static final String ACTION_BUTTON_PTT_DOWN_BITTIUM="com.elektrobit.pttbutton.PTTBUTTON_DOWN";
    private static final String ACTION_BUTTON_PTT_UP_BITTIUM="com.elektrobit.pttbutton.PTTBUTTON_UP";
    private static final String ACTION_BUTTON_PTT_LONG_PRESS_BITTIUM="com.elektrobit.pttbutton.PTTBUTTON_LONG_PRESS";

    public void initialize() {
        if(mConnection==null)
            mMCOPCallback = new MCOPCallbackManager();
            mConnection = new ServiceConnection() {

                @Override
                public void onServiceConnected(ComponentName className, IBinder service) {
                    Log.d(TAG,"Service binded! "+className.getPackageName()+"\n");
                    Toast.makeText(CriticalAccess.getContext(),"Binded to MCOP SDK",Toast.LENGTH_SHORT).show();
                    mService = IMCOPsdk.Stub.asInterface(service);

                    try {
                        Log.d(TAG,"execute registerCallback "+mMCOPCallback);
                        mService.registerCallback(mMCOPCallback);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    isConnect=true;

                    // Auto Registration
                    if (MCOPConfigurationManager.isAutoRegister) {
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                register();
                            }
                        }, DEFAULT_REGISTER_DELAY);
                    }
                }

                @Override
                public void onServiceDisconnected(ComponentName className) {
                    mService = null;
                    // This method is only invoked when the service quits from the other end or gets killed
                    // Invoking exit() from the AIDL interface makes the Service kill itself, thus invoking this.
                    Log.d(TAG,"Service disconnected.\n");
                    isConnect=false;
                }
            };
    }
    private BroadcastReceiver mButtonPTTBroadCastRecvMCPTT = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG,"PTT button");
            final String action = intent.getAction();
            if(action.compareTo(ACTION_BUTTON_PTT_DOWN_BITTIUM)==0
            ){

                Log.d(TAG,"PTT button Down");
                if(mState== MainActivity.State.IDLE && !showIdsOperationFloorControl(getApplicationContext(), true)){
                    Log.e(TAG,"Error: the device can´t request the Token now");
                }else if(mState==null || mState== MainActivity.State.NONE){
                    makeCall();
                }
            }else if(action.compareTo(ACTION_BUTTON_PTT_UP_BITTIUM)==0 && mState== MainActivity.State.GRANTED){
                Log.d(TAG,"PTT button Up");
                if(!showIdsOperationFloorControl(getApplicationContext(), false)){
                    Log.e(TAG,"Error: the device can´t release the Token now");
                }
            }else if(action.compareTo(ACTION_BUTTON_PTT_LONG_PRESS_BITTIUM)==0){
                Log.d(TAG,"Long PTT button press");
            }
        }
    };

    private void makeCall(){
        if (mERState == false) {
            // Non-Emergency Calls
            if (mCallType == CallType.GROUP) {
                // Group Call
                try {
                    Log.d(TAG,"Call type: " + mCallType);
                    if(mService!=null)
                        mService.makeCall(
                                selGroup, //DEFAULT_GROUP,
                                ConstantsMCOP.CallEventExtras.CallTypeEnum.Audio.getValue() |
                                        ConstantsMCOP.CallEventExtras.CallTypeEnum.WithFloorCtrl.getValue() |
                                        ConstantsMCOP.CallEventExtras.CallTypeEnum.PrearrangedGroup.getValue());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else if (mCallType == CallType.PRIVATE) {
                // Private Call
                try {
                    Log.d(TAG,"Call type: " + mCallType);
                    if(mService!=null)
                        mService.makeCall(
                                selUser, //DEFAULT_PRIVATE_CALL,
                                ConstantsMCOP.CallEventExtras.CallTypeEnum.Audio.getValue() |
                                        ConstantsMCOP.CallEventExtras.CallTypeEnum.WithFloorCtrl.getValue() |
                                        ConstantsMCOP.CallEventExtras.CallTypeEnum.Private.getValue());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        } else {
            // Emergency Calls
            if (mCallType == CallType.GROUP) {
                // Emergency Group Call
                try {
                    Log.d(TAG,"Call type: Emergency " + mCallType);
                    if(mService!=null)
                        mService.makeCall(
                                selGroup, //DEFAULT_GROUP,
                                ConstantsMCOP.CallEventExtras.CallTypeEnum.Audio.getValue() |
                                        ConstantsMCOP.CallEventExtras.CallTypeEnum.WithFloorCtrl.getValue() |
                                        ConstantsMCOP.CallEventExtras.CallTypeEnum.PrearrangedGroup.getValue() |
                                        ConstantsMCOP.CallEventExtras.CallTypeEnum.Emergency.getValue());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else if (mCallType == CallType.PRIVATE) {
                // Private Call
                try {
                    Log.d(TAG,"Call type: Emergency " + mCallType);
                    if(mService!=null)
                        mService.makeCall(
                                selUser, //DEFAULT_PRIVATE_CALL,
                                ConstantsMCOP.CallEventExtras.CallTypeEnum.Audio.getValue() |
                                        ConstantsMCOP.CallEventExtras.CallTypeEnum.WithFloorCtrl.getValue() |
                                        ConstantsMCOP.CallEventExtras.CallTypeEnum.Private.getValue() |
                                        ConstantsMCOP.CallEventExtras.CallTypeEnum.Emergency.getValue());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
        btn_hangup.setEnabled(true);
    }

    public static void connectService(String client){
        if(BuildConfig.DEBUG)Log.d(TAG,"connectService execute");
        if(!isConnect){
            serviceIntent = new Intent()
                    .setComponent(new ComponentName(
                            "org.mcopenplatform.muoapi",
                            "org.mcopenplatform.muoapi.MCOPsdk"));

            if(client!=null && !client.trim().isEmpty()){
                Log.i(TAG,"Current profile: "+currentProfile);
                serviceIntent.putExtra("PROFILE_SELECT", currentProfile!=null?currentProfile:client);
            }

            serviceIntent.putExtra(ConstantsMCOP.MBMS_PLUGIN_PACKAGE_ID, "com.expway.embmsserver");
            serviceIntent.putExtra(ConstantsMCOP.MBMS_PLUGIN_SERVICE_ID, "com.expway.embmsserver.MCOP");

            try{
                ComponentName componentName;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    componentName = this.startForegroundService(serviceIntent);
                } else {
                    componentName = this.startService(serviceIntent);
                }
                if(componentName==null){
                    Log.e(TAG,"Starting Error: "+componentName.getPackageName());
                }else if(serviceIntent==null){
                    Log.e(TAG,"serviceIntent Error: "+componentName.getPackageName());
                }else if(mConnection==null){
                    Log.e(TAG,"mConnection Error: "+componentName.getPackageName());
                }else{

                }
            }catch (Exception e){
                if(BuildConfig.DEBUG)Log.w(TAG,"Error in start service: "+e.getMessage());
            }
            if(mConnection!=null)
                Log.i(TAG,"Bind Service: "+bindService(serviceIntent, mConnection, BIND_AUTO_CREATE));
        }
    }

    private void register() {
        if (IdMSCMS) {
            try {
                if(mService!=null)
                    mService.loginMCOP();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else { // CMS
            try {
                if(mService!=null)
                    mService.authorizeUser(null);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
