package org.test.client.mcopclient.controller;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
        if(mConnection==null) {
            mMCOPCallback = new MCOPCallbackManager();
            mConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName className, IBinder service) {
                    Log.d(TAG, "Service binded! " + className.getPackageName() + "\n");
                    Toast.makeText(CriticalAccess.getContext(), "Binded to MCOP SDK", Toast.LENGTH_SHORT).show();
                    mService = IMCOPsdk.Stub.asInterface(service);

                    try {
                        Log.d(TAG, "execute registerCallback " + mMCOPCallback);
                        mService.registerCallback(mMCOPCallback);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    isConnect = true;

                    // Auto Registration
                    if (MCOPConfigurationManager.isAutoRegister()) {
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
                    Log.d(TAG, "Service disconnected.\n");
                    isConnect = false;
                }
            };
            final IntentFilter intentFilter2 = new IntentFilter();
            intentFilter2.addAction(ACTION_BUTTON_PTT_DOWN_BITTIUM);
            intentFilter2.addAction(ACTION_BUTTON_PTT_UP_BITTIUM);
            intentFilter2.addAction(ACTION_BUTTON_PTT_LONG_PRESS_BITTIUM);
        }
    }

    // PTT button on Bittium Devices
    private BroadcastReceiver mButtonPTTBroadCastRecvMCPTT = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
        }
    };

    private void makeCall(){
        if (mERState == false) {
            // Non-Emergency Calls
            if (mCallType == CallType.GROUP) {
                // Group Call

            } else if (mCallType == CallType.PRIVATE) {
                // Private Call

            }
        } else {
            // Emergency Calls
            if (mCallType == CallType.GROUP) {
                // Emergency Group Call

            } else if (mCallType == CallType.PRIVATE) {
                // Private Call

            }
        }
    }

    public static void connectService(String client){
    }

    private void register() {
        if (MCOPConfigurationManager.isIdMSCMS()) {
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
