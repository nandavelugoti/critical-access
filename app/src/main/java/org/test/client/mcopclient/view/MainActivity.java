/*
 *
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

package org.test.client.mcopclient.view;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import org.test.client.mcopclient.BuildConfig;
import org.test.client.mcopclient.R;
import org.test.client.mcopclient.controller.MCOPServiceManager;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getCanonicalName();
    private static final int AUTHETICATION_RESULT = 101;
    private static final int GET_PERMISSION = 102;
    private static final String PARAMETER_PROFILE = "parameters";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setPermissions();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            ArrayList<String> strings = getIntent().getStringArrayListExtra(PARAMETER_PROFILE);
            MCOPServiceManager.initialize(strings);
        }
    }

    @Override
    protected void onDestroy() {
        if (BuildConfig.DEBUG) Log.i(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case AUTHETICATION_RESULT:
                if (resultCode == ScreenAuthenticationWebView.RETURN_ON_AUTHENTICATION_LISTENER_FAILURE) {
                    String dataError;
                    if (data != null &&
                            (dataError = data.getStringExtra(ScreenAuthenticationWebView.RETURN_ON_AUTHENTICATION_ERROR)) != null &&
                            dataError instanceof String) {
                        Log.e(TAG, "Authentication Error: " + dataError);
                    } else {
                        Log.e(TAG, "Error processing authentication.");
                    }
                } else if (resultCode == ScreenAuthenticationWebView.RETURN_ON_AUTHENTICATION_LISTENER_OK) {
                    String dataUri;
                    if (data != null &&
                            (dataUri = data.getStringExtra(ScreenAuthenticationWebView.RETURN_ON_AUTHENTICATION_RESPONSE)) != null &&
                            dataUri instanceof String) {
                        URI uri = null;
                        try {
                            uri = new URI(dataUri);
                            Log.i(TAG, "Uri: " + uri.toString());
                        } catch (URISyntaxException e) {
                            Log.e(TAG, "Authentication Error: " + e.getMessage());
                            e.printStackTrace();
                        }
                    } else {
                        Log.e(TAG, "Error processing file to import profiles.");
                    }
                }
                break;
        }
    }


    // END GUI

    /**
     * Set permissions for Android 6.0 or above
     */
    protected void setPermissions() {
        //Set permissions
        //READ_PHONE_STATE
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECORD_AUDIO)) {
                //Show an explanation to the user *asynchronously* -- don't block
                //this thread waiting for the user's response! After the user
                //sees the explanation, request the permission again.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE},
                        GET_PERMISSION);
            } else {
                //No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE},
                        GET_PERMISSION);

                //MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                //app-defined int constant. The callback method gets the
                //result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case GET_PERMISSION: {
                //If request is cancelled, result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Permission was granted, yay! Do the
                    //contacts-related task you need to do.
                    //API>22
                    setPermissionsWriteSetting();
                    MCOPServiceManager.connectService(null);
                } else {
                    setPermissions();
                    //Permission denied, boo! Disable the
                    //functionality that depends on this permission.
                }
                return;
            }
            default:
                break;
            //other 'case' lines to check for other
            //permissions this app might request
        }
    }

    /**
     * API>22
     */
    @TargetApi(Build.VERSION_CODES.M)
    protected void setPermissionsWriteSetting() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (!Settings.System.canWrite(this)) {
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + this.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }
}