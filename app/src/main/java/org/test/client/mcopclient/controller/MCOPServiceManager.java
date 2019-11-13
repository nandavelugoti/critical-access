package org.test.client.mcopclient.controller;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.mcopenplatform.muoapi.IMCOPsdk;
import org.test.client.mcopclient.BuildConfig;
import org.test.client.mcopclient.CriticalAccess;
import org.test.client.mcopclient.ConstantsMCOP;
import org.test.client.mcopclient.model.User;

import java.util.List;
import java.util.Map;

import static android.content.Context.BIND_AUTO_CREATE;

public class MCOPServiceManager {
    private final static String TAG = MCOPServiceManager.class.getCanonicalName();
    private static Intent serviceIntent;
    private static MCOPServiceConnection mConnection;
    private static User user;
    private static String currentProfile;
    private static Map<String, String> clients;
    private boolean registered = false;

    public static void initialize(List<String> profiles) {
        if (mConnection == null) {
            mConnection = new MCOPServiceConnection();
        }

        Map<String, String> parameterClients = MCOPConfigurationManager.getProfilesParameters(profiles);
        if(parameterClients!=null && !parameterClients.isEmpty())
            clients = parameterClients;

        MCOPConfigurationManager.loadConfiguration(CriticalAccess.getContext());

        if (user==null);
            user = new User();

        TelephonyManager tm = (TelephonyManager) CriticalAccess.getContext().getSystemService(Context.TELEPHONY_SERVICE);
        if (tm != null) {
            if (ActivityCompat.checkSelfPermission(CriticalAccess.getContext(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                String imei = tm.getDeviceId();
                String client = clients.get(imei);
                if(client!=null){
                    currentProfile=client;
                    Log.i(TAG,"currentProfile: "+ currentProfile);
                }
                connectService(currentProfile);
            }
        }
    }

    public static void connectService(String client){
        if(BuildConfig.DEBUG)Log.d(TAG,"connectService execute");
        if(!mConnection.isConnected()){
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
                    componentName = CriticalAccess.getContext().startForegroundService(serviceIntent);
                } else {
                    componentName = CriticalAccess.getContext().startService(serviceIntent);
                }
                if(componentName==null){
                    Log.e(TAG,"Starting Error: componentName is null");
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
                Log.i(TAG,"Bind Service: "+CriticalAccess.getContext().bindService(serviceIntent, mConnection, BIND_AUTO_CREATE));
        }
    }

    public static IMCOPsdk getService() {
        return mConnection.getService();
    }
}
