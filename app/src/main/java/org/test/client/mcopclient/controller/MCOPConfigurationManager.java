package org.test.client.mcopclient.controller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.test.client.mcopclient.CriticalAccess;
import org.test.client.mcopclient.controller.preferences.PreferencesManagerDefault;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MCOPConfigurationManager {
    private final static String TAG = MCOPConfigurationManager.class.getCanonicalName();

    private static final String PARAMETER_PROFILE = "parameters";
    private static final String PARAMETER_SAVE_PROFILE = "TAG.PARAMETER_SAVE_PROFILE";
    private static final String PARAMETER_CONFIG = "configuration";
    private static final String PARAMETER_CONFIG_IDMSCMS = "TAG.PARAMETER_CONFIG_IDMSCMS";
    private static final String PARAMETER_CONFIG_AUTOREGISTER = "TAG.PARAMETER_CONFIG_AUTOREGISTER";
    private static boolean IdMSCMS = false;
    private static boolean autoRegister = false;
    private static PreferencesManagerDefault preferencesManager = new PreferencesManagerDefault();
    private static Context ctx = null;

    public static void loadConfiguration(Context context){
        ctx = context;
        if(preferencesManager!=null){
            String idmscms = preferencesManager.getString(ctx, PARAMETER_CONFIG_IDMSCMS, "None");
            if (idmscms.equals("IdMS/CMS")) {
                IdMSCMS = true;
            } else {
                IdMSCMS = false;
            }
            String auto = preferencesManager.getString(ctx, PARAMETER_CONFIG_AUTOREGISTER, "Manual");
            if (auto.equals("Automatic")) {
                autoRegister = true;
            } else {
                autoRegister = false;
            }
        }
    }

    public static Map<String,String> getProfilesParameters(Intent intent){
        List<String> parameters = intent.getStringArrayListExtra(PARAMETER_PROFILE);
        Map<String,String> parametersMap=new HashMap<>();
        if(parameters!=null && !parameters.isEmpty()){
            Log.i(TAG,"External parameters");
        }else{
            Log.i(TAG,"No external parameters");
            parameters=loadParameters();
        }

        if(parameters!=null && !parameters.isEmpty())
            for (String parameter:parameters){
                Log.i(TAG,"parameter: "+parameter);
                String[] parametersSplit=parameter.split(":");
                if(parametersSplit!=null && parametersSplit.length==2){
                    parametersMap.put(parametersSplit[0],parametersSplit[1]);
                }
            }
        if(parametersMap!=null && !parametersMap.isEmpty()){
            saveParameters(parameters);
        }
        return parametersMap;
    }

    private static boolean saveParameters(List<String> parameters){
        if(preferencesManager!=null){
            return preferencesManager.putStringSet(ctx, PARAMETER_SAVE_PROFILE,new HashSet<String>(parameters));
        }
        return false;
    }

    private static ArrayList<String> loadParameters(){
        if(preferencesManager!=null){
            Set<String> stringSet=preferencesManager.getStringSet(ctx, PARAMETER_SAVE_PROFILE);
            if(stringSet!=null){
                return (new ArrayList<String>(stringSet));
            }
        }
        return null;
    }

    private static boolean saveConfiguration(){
        if(preferencesManager!=null){
            if (IdMSCMS) {
                preferencesManager.putString(ctx, PARAMETER_CONFIG_IDMSCMS, "IdMS/CMS");
            } else {
                preferencesManager.putString(ctx, PARAMETER_CONFIG_IDMSCMS, "None");
            }
            if (autoRegister) {
                preferencesManager.putString(ctx, PARAMETER_CONFIG_AUTOREGISTER, "Automatic");
            } else {
                preferencesManager.putString(ctx, PARAMETER_CONFIG_AUTOREGISTER, "Manual");
            }
            return true;
        }
        return false;
    }

    public static boolean isAutoRegister() {
        return autoRegister;
    }
    public static boolean isIdMSCMS() {
        return IdMSCMS;
    }
    public static void destroy() {}
}
