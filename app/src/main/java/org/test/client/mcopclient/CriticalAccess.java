package org.test.client.mcopclient;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class CriticalAccess extends Application {
    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    public static String getAbout(Context context) {
        if (context == null) return "";
        PackageInfo pInfo = null;
        String version = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            version = null;
        }
        String about = context.getString(R.string.app_name) + "\n";
        about += context.getString(R.string.Build_version) + ": " + version;
        about += "\n" + context.getString(R.string.Developed_by) + ": " + context.getString(R.string.web);
        about += "\n" + context.getString(R.string.copyright);

        return about;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }
}
