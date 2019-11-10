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
    private static boolean isConnect=false;
    private static User user;
    private static final int DEFAULT_REGISTER_DELAY = 3000;
    private boolean registered = false;

    public static void initialize() {
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
        }
    }

    public static void connectService(){

    }

    private static void register() {
        if (MCOPConfigurationManager.isIdMSCMS()) {
            //IdMS
            try {
                if(mService!=null)
                    mService.loginMCOP();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            // CMS
            try {
                if(mService!=null)
                    mService.authorizeUser(null);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public static IMCOPsdk getService() {
        return mService;
    }
}
