/*
 *
 *  Copyright (C) 2018 Eduardo Zarate Lasurtegui
 *   Copyright (C) 2018, University of the Basque Country (UPV/EHU)
 *
 *  Contact for licensing options: <licensing-mcpttclient(at)mcopenplatform(dot)com>
 *
 *  This file is part of MCOP MCPTT Client
 *
 *  This is free software: you can redistribute it and/or modify it under the terms of
 *  the GNU General Public License as published by the Free Software Foundation, either version 3
 *  of the License, or (at your option) any later version.
 *
 *  This is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 *  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *  See the GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License along
 *  with this program; if not, write to the Free Software Foundation, Inc.,
 *  59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */

package org.test.client.mcopclient.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.Set;


public class PreferencesManager {
    protected static String TAG = PreferencesManager.class.getCanonicalName();
    protected static PreferencesManager mPreferencesManager=null;
    private  String PREFERENCE_ID=TAG+"PREFERENCE_ID";
    public static final String  STRING_DEFAULT=TAG+".STRING_DEFAULT";



    SharedPreferences sharedPref;
    public PreferencesManager(String preference_ID){
        this();
        this.PREFERENCE_ID=preference_ID;
    }
    public PreferencesManager(){
        super();
    }

    protected SharedPreferences createSharedPreferences(Context context){
        return createSharedPreferences(context, PREFERENCE_ID);
    }

    protected SharedPreferences createSharedPreferences(Context context,String preference_ID){
        if(context==null){
            Log.e(TAG,"Error save data 1");
            return null;
        }
        this.PREFERENCE_ID=preference_ID;
        sharedPref = context.getSharedPreferences(this.PREFERENCE_ID, Context.MODE_PRIVATE);
        return sharedPref;
    }

    /*
    public static PreferencesManager getInstance(){
        if(mPreferencesManager==null){
            mPreferencesManager=new PreferencesManager();
        }
        return mPreferencesManager;
    }
    */

    public boolean putStringSet(Context context,String key,Set<String> data){
        if(context==null || key==null || data==null){
            Log.e(TAG, "Some parameter is null object in putString.");
            return false;
        }
        SharedPreferences.Editor editor = createSharedPreferences(context).edit();
        editor.putStringSet(key, data);
        return editor.commit();
    }

    public Set<String> getStringSet(Context context,String key){
        return getStringSet(context, key, STRING_DEFAULT);
    }

    public Set<String> getStringSet(Context context,String key,String defaultString){
        if(context==null || key==null){
            Log.e(TAG,"Some parameter is null object in getString.");
            return null;
        }
        SharedPreferences  sharedPreferences= createSharedPreferences(context);
        return sharedPreferences.getStringSet(key, null);

    }

    public boolean putString(Context context,String key,String data){
        if(context==null || key==null || data==null){
            Log.e(TAG, "Some parameter is null object in putString.");
            return false;
        }
        SharedPreferences.Editor editor = createSharedPreferences(context).edit();
        editor.putString(key, data);
        return editor.commit();
    }

    public String getString(Context context,String key){
        return getString(context, key, STRING_DEFAULT);

    }

    public String getString(Context context,String key,String defaultString){
        if(context==null || key==null){
            Log.e(TAG,"Some parameter is null object in getString.");
            return null;
        }
        SharedPreferences  sharedPreferences= createSharedPreferences(context);
        return sharedPreferences.getString(key, defaultString);

    }

    public boolean putInt(Context context,String key,int data){
        if(context==null || key==null){
            Log.e(TAG,"Some parameter is null object in putInt.");
            return false;
        }
        SharedPreferences.Editor editor = createSharedPreferences(context).edit();
        editor.putInt(key, data);
        return editor.commit();
    }

    public int getInt(Context context,String key){
        if(context==null || key==null){
            Log.e(TAG,"Some parameter is null object in getInt.");
            return -1;
        }
        SharedPreferences.Editor editor = createSharedPreferences(context).edit();
        return createSharedPreferences(context).getInt(key, -1);

    }

    public boolean putLong(Context context,String key,long data){
        if(context==null || key==null){
            Log.e(TAG,"Some parameter is null object in putLong.");
            return false;
        }
        SharedPreferences.Editor editor = createSharedPreferences(context).edit();
        editor.putLong(key, data);
        return editor.commit();
    }

    public long getLong(Context context,String key){
        if(context==null || key==null){
            Log.e(TAG,"Some parameter is null object in getLong.");
            return -1;
        }
        SharedPreferences sharedPreferences= createSharedPreferences(context);
        return sharedPreferences.getLong(key, -1);

    }


    public boolean putFloat(Context context,String key,float data){
        if(context==null || key==null){
            Log.e(TAG,"Some parameter is null object in putFloat.");
            return false;
        }
        SharedPreferences.Editor editor = createSharedPreferences(context).edit();
        editor.putFloat(key, data);
        return editor.commit();
    }

    public float getFloat(Context context,String key){
        if(context==null || key==null){
            Log.e(TAG,"Some parameter is null object in getLong.");
            return -1;
        }
        SharedPreferences sharedPreferences= createSharedPreferences(context);
        return sharedPreferences.getFloat(key, -1);

    }
}
