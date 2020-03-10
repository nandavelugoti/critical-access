package org.test.client.mcopclient.controller;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import org.mcopenplatform.muoapi.IMCOPCallback;
import org.mcopenplatform.muoapi.IMCOPsdk;
import org.test.client.mcopclient.CriticalAccess;

import java.net.URI;


public class MCOPServiceConnection implements ServiceConnection {
    private final static String TAG = MCOPServiceConnection.class.getCanonicalName();
    private static final int DEFAULT_REGISTER_DELAY = 3000;

    private IMCOPsdk mService;
    private boolean isConnected = false;

    @Override
    public void onServiceConnected(ComponentName className, IBinder service) {
        Log.d(TAG, "Service binded! " + className.getPackageName() + "\n");
        Toast.makeText(CriticalAccess.getContext(), "Binded to MCOP SDK", Toast.LENGTH_SHORT).show();
        mService = IMCOPsdk.Stub.asInterface(service);
        IMCOPCallback mMCOPCallback = new MCOPCallbackManager();

        try {
            Log.d(TAG, "execute registerCallback " + mMCOPCallback);
            mService.registerCallback(mMCOPCallback);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        isConnected = true;

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
        isConnected = false;
    }

    public void register() {
        if (MCOPConfigurationManager.isIdMSCMS()) {
            //IdMS
            try {
                if (mService != null)
                    mService.loginMCOP();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            // CMS
            try {
                if (mService != null)
                    mService.authorizeUser(MCOPServiceManager.AddressBook.getCurrentUser().getMcpttID());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void unRegister() {
        try {
            if(mService!=null) {
                mService.unLoginMCOP();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    IMCOPsdk getService() {
        return mService;
    }

    boolean isConnected() {
        return isConnected;
    }

    public void authorizeUser(URI uri) {
        try {
            mService.authorizeUser(uri.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
