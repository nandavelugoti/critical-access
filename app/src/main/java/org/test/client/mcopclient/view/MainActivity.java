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
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.Settings;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.test.client.mcopclient.BuildConfig;
import org.test.client.mcopclient.R;
import org.test.client.mcopclient.controller.MCOPConfigurationManager;
import org.test.client.mcopclient.controller.MCOPServiceManager;
import org.test.client.mcopclient.model.ConstantsMCOP;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getCanonicalName();
    private Button btn_register;
    private Button btn_unregister;
    private TextView text_info;
    private TextView text_error;
    private TextView text_affiliation;
    private TextView text_status;
    private Button btn_hangup;
    private DialogMenu mDialogIds;
    private DialogAlert mDialogAlert;
    private Button btn_accept;
    private Button reg_status;
    private Button reg_eMBMS;
    private Button btn_call;
    private Button btn_speaker;
    private Button btn_er;
    private TextView text_talking;
    private TextView text_callingid;
    private TextView switch_private;
    private TextView switch_group;
    private Toolbar toolbar;
    private TextView text_emergency;

    private Map<String, String> clients;
    private DialogMenu mDialogMenu;
    private String currentProfile;
    private SwitchCompat switchCompat;
    private Spinner spinnerGroups;
    private Spinner spinnerUsers;
    private MenuItem itemIdMSCSM;
    private MenuItem itemAutoReg;
    private MenuItem itemExit;
    private Boolean isSpeakerphoneOn;
    private ArrayList<String> groupsCurrent;
    private Intent serviceIntent;
    private String[] regConfig = {"IdMS/CMS", "None"};
    private String[] autoReg = {"Manual", "Automatic"};


    private String selGroup = "sip:DEMO_group@organization.org";
    private String selUser = "sip:mcptt_id_DEMO_A@organization.org";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setPermissions();

        toolbar = (Toolbar) findViewById(R.id.screen_menu_toolbar);
        setSupportActionBar(toolbar);
        btn_register=(Button)findViewById(R.id.btn_register);
        btn_unregister=(Button)findViewById(R.id.btn_unregister);
        text_info=(TextView)findViewById(R.id.text_info);
        text_error=(TextView)findViewById(R.id.text_error);
        text_affiliation=(TextView)findViewById(R.id.text_affiliation);
        text_status=(TextView)findViewById(R.id.text_status);
        btn_hangup=(Button)findViewById(R.id.btn_hangup);
        btn_accept=(Button)findViewById(R.id.btn_accept);
        reg_status=(Button)findViewById(R.id.reg_status);
        reg_eMBMS=(Button)findViewById(R.id.reg_eMBMS);
        btn_call=(Button)findViewById(R.id.btn_call);
        btn_speaker=(Button)findViewById(R.id.btn_speaker);
        btn_er=(Button)findViewById(R.id.btn_er);
        text_talking=(TextView)findViewById(R.id.text_talking);
        text_callingid=(TextView)findViewById(R.id.text_callingid);
        switch_private=(TextView)findViewById(R.id.switch_private);
        switch_group=(TextView)findViewById(R.id.switch_group);
        switchCompat=(SwitchCompat)findViewById(R.id.switch_call);
        spinnerGroups=(Spinner)findViewById(R.id.spinnerGroups);
        spinnerUsers=(Spinner)findViewById(R.id.spinnerUsers);
        text_emergency=(TextView)findViewById(R.id.emergency);

        btn_unregister.setEnabled(false);
        btn_call.setEnabled(false);
        btn_call.setBackgroundResource(R.drawable.token_inactive);
        btn_register.setEnabled(true);
        btn_accept.setEnabled(false);
        btn_hangup.setEnabled(false);
        reg_status.setEnabled(false);
        reg_eMBMS.setEnabled(false);
        btn_speaker.setEnabled(false);
        btn_er.setEnabled(false);
        text_talking.setVisibility((View.INVISIBLE));
        text_callingid.setVisibility((View.INVISIBLE));
        switchCompat.setEnabled(false);
        switch_group.setTextColor(ContextCompat.getColor(this, R.color.background));
        switch_private.setTextColor(ContextCompat.getColor(this, R.color.background));
        spinnerGroups.setEnabled(false);
        spinnerGroups.setAdapter(null);
        spinnerUsers.setEnabled(false);
        spinnerUsers.setAdapter(null);
        spinnerUsers.setVisibility((View.GONE));
        spinnerGroups.setVisibility((View.VISIBLE));

        //Dissable logging messages. Enable just for debugging
        text_info.setVisibility((View.GONE));
        text_error.setVisibility((View.GONE));
        text_affiliation.setVisibility((View.GONE));

        clients = new HashMap<>();
        List<String> usersCurrent = new ArrayList<String>();

        // User list
        // EDIT THIS LIST WITH THE PROVIDED USERNAMES
        usersCurrent.add("sip:mcptt_id_DEMO_A@organization.org");
        usersCurrent.add("sip:mcptt_id_DEMO_B@organization.org");
        usersCurrent.add("sip:mcptt_id_DEMO_C@organization.org");
        usersCurrent.add("sip:mcptt_id_DEMO_D@organization.org");
        usersCurrent.add("sip:mcptt_id_DEMO_E@organization.org");

        // Group list
        // EDIT THIS LIST WITH THE PROVIDED GROUP NAME(s)
        groupsCurrent = new ArrayList<String>();
        groupsCurrent.add("sip:DEMO_group@organization.org");
        groupsCurrent.add("sip:DEMO_group2@organization.org");

        // Adapter for User Spinner
        ArrayAdapter<String> userAdaptor = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item, usersCurrent);
        // Drop down layout style - list view with radio button
        userAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUsers.setAdapter(userAdaptor);
        loadGroups();

        // Load Profiles
        Map<String, String> parameterClients= MCOPConfigurationManager.getProfilesParameters(getIntent());
        if(parameterClients!=null && !parameterClients.isEmpty())
            clients=parameterClients;

        MCOPConfigurationManager.loadConfiguration(this);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });

        btn_unregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if(mService!=null) {
                        mService.unLoginMCOP();
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        });

        btn_hangup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(BuildConfig.DEBUG)Log.d(TAG,"onClick btn_hangup");
                showIds(getApplicationContext());
            }
        });

        btn_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Auto-accept used
                //showIdsAcceptCall(getApplicationContext());
            }
        });

        btn_er.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               // Set Emergency state
               if (mERState == false) {
                   mERState = true;
                   startERState();
               } else {
                   mERState = false;
                   endERState();
               }
           }
        });

        btn_speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AudioManager mAudioManager;
                mAudioManager =  (AudioManager)getSystemService(Context.AUDIO_SERVICE);
                if(isSpeakerphoneOn){
                    isSpeakerphoneOn=false;
                    Log.d(TAG, "Speaker OFF");
                    btn_speaker.setText(R.string.btn_speaker_off);
                }else{
                    isSpeakerphoneOn=true;
                    Log.d(TAG, "Speaker ON");
                    btn_speaker.setText(R.string.btn_speaker_on);
                }
                mAudioManager.setSpeakerphoneOn(isSpeakerphoneOn);
            }
        });

        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    // Switch is on. Private Call
                    mCallType = CallType.PRIVATE;
                    spinnerGroups.setVisibility((View.GONE));
                    spinnerUsers.setVisibility((View.VISIBLE));
                    spinnerGroups.setEnabled(false);
                    spinnerUsers.setEnabled(true);
                }
                else {
                    // Switch is off. Group Call
                    mCallType = CallType.GROUP;
                    spinnerUsers.setVisibility((View.GONE));
                    spinnerGroups.setVisibility((View.VISIBLE));
                    spinnerUsers.setEnabled(false);
                    spinnerGroups.setEnabled(true);
                }
            }
        });

        spinnerUsers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selUser = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerGroups.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selGroup = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_call.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(mState != State.NONE && event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (mState == State.IDLE) {
                        //Request token
                        Log.d(TAG,"TOKEN REQUEST");
                        showIdsOperationFloorControl(getApplicationContext(), true);
                    }
                }else if (mState != State.NONE && event.getAction() == MotionEvent.ACTION_UP) {
                    if (mState == State.GRANTED) {
                        //Release token
                        Log.d(TAG,"TOKEN RELEASE");
                        showIdsOperationFloorControl(getApplicationContext(),false);
                    }
                } else if (event.getAction() == MotionEvent.ACTION_DOWN){
                    makeCall();
                }
                return true;
            }
        });

        final IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction(ACTION_BUTTON_PTT_DOWN_BITTIUM);
        intentFilter2.addAction(ACTION_BUTTON_PTT_UP_BITTIUM);
        intentFilter2.addAction(ACTION_BUTTON_PTT_LONG_PRESS_BITTIUM);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            if(tm!=null) {
                String imei = tm.getDeviceId();
                String client=clients.get(imei);
                if(client!=null){
                    this.currentProfile=client;
                    Log.i(TAG,"currentProfile: "+ currentProfile);
                    MCOPServiceManager.connectService(currentProfile);
                }else{
                    connectService();
                }
            }
        }
    }



    private void loadGroups(){
        // Adapter por Group Spinner
        ArrayAdapter<String> groupAdaptor = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_item, groupsCurrent);
        // Drop down layout style - list view with radio button
        groupAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGroups.setAdapter(groupAdaptor);
    }

    private void startERState() {
        // Start Emergency State
        Log.d(TAG,"Start Emergency State");
        Toolbar toolbar = (Toolbar) findViewById(R.id.screen_menu_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorER));
        AppBarLayout appbarLayout = (AppBarLayout) findViewById(R.id.screen_menu_toolbar_AppBarLayout);
        appbarLayout.setBackgroundColor(getResources().getColor(R.color.colorER));
        text_emergency.setTextColor(getResources().getColor(R.color.colorER));
    }

    private void endERState() {
        // End Emergency State
        Log.d(TAG,"End Emergency State");
        Toolbar toolbar = (Toolbar) findViewById(R.id.screen_menu_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        AppBarLayout appbarLayout = (AppBarLayout) findViewById(R.id.screen_menu_toolbar_AppBarLayout);
        appbarLayout.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        text_emergency.setTextColor(getResources().getColor(R.color.background));
    }

    private void showIds(final Context context){
        if(userData.getSessionIDs()==null)return;
        final String[] strings=userData.getSessionIDs().toArray(new String[userData.getSessionIDs().size()]);
        if(strings==null || strings.length==0){
            if(BuildConfig.DEBUG)Log.d(TAG,"Error showIds");
            return;
        }
        if(strings.length==1) {
            try {
                if (mService != null)
                    mService.hangUpCall(strings[0]);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            mDialogIds = DialogMenu.newInstance(strings, null);
            mDialogIds.setOnClickItemListener(new DialogMenu.OnClickListener() {
                @Override
                public void onClickItem(int item) {
                    if (item >= 0 && strings.length > item) {
                        try {
                            if (mService != null)
                                mService.hangUpCall(strings[item]);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            mDialogIds.show(getSupportFragmentManager(), "SimpleDialog");
        }
    }

    private boolean showIdsOperationFloorControl(final Context context, final boolean request){
        Log.i(TAG,"Send floor control operation : "+((request)?"request":"release"));
        if(userData.getSessionIDs()==null)return false;
        final String[] strings=userData.getSessionIDs().toArray(new String[userData.getSessionIDs().size()]);
        if(strings==null || strings.length==0)return false;
        if(strings.length==1) {
            try {
                if (mService != null){
                    mService.floorControlOperation(
                            strings[0],
                            request ? ConstantsMCOP.FloorControlEventExtras.FloorControlOperationTypeEnum.MCPTT_Request.getValue() : ConstantsMCOP.FloorControlEventExtras.FloorControlOperationTypeEnum.MCPTT_Release.getValue(),
                            null);
                }
                Log.i(TAG,"Send floor control operation 2: "+((request)?"request":"release"));

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            mDialogIds = DialogMenu.newInstance(strings, null);
            mDialogIds.setOnClickItemListener(new DialogMenu.OnClickListener() {
                @Override
                public void onClickItem(int item) {
                    if (item >= 0 && strings.length > item) {
                        try {
                            if (mService != null){
                                mService.floorControlOperation(
                                        strings[item],
                                        request ? ConstantsMCOP.FloorControlEventExtras.FloorControlOperationTypeEnum.MCPTT_Request.getValue() : ConstantsMCOP.FloorControlEventExtras.FloorControlOperationTypeEnum.MCPTT_Release.getValue(),
                                        null);
                                Log.i(TAG,"Send floor control operation 3: "+((request)?"request":"release"));
                            }

                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            mDialogIds.show(getSupportFragmentManager(), "SimpleDialog");
        }
        return true;
    }

    private void showIdsAcceptCall(final Context context, String sessionID){
        if(userData.getSessionIDs()==null)return;
        final String[] strings=userData.getSessionIDs().toArray(new String[userData.getSessionIDs().size()]);
        if(strings==null)return;
        try {
            if(mService!=null)
                mService.acceptCall(sessionID);
                btn_call.setBackgroundResource(R.drawable.token_red);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy(){
        if(BuildConfig.DEBUG)Log.i(TAG,"onDestroy");
        super.onDestroy();
        isConnect=false;
        if(mConnection!=null && isConnect) {
            try {
                unbindService(mConnection);
            } catch (Exception e) {
                Log.e(TAG, "Error unbinding Service");
            }
        }
        if(serviceIntent!=null);
        mConnection=null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case AUTHETICATION_RESULT:
                if ( resultCode == ScreenAuthenticationWebView.RETURN_ON_AUTHENTICATION_LISTENER_FAILURE) {
                    String dataError;
                    if (data != null &&
                            (dataError= data.getStringExtra(ScreenAuthenticationWebView.RETURN_ON_AUTHENTICATION_ERROR))!=null &&
                            dataError instanceof String) {
                        Log.e(TAG,"Authentication Error: "+dataError);
                    }else{
                        Log.e(TAG,"Error processing authentication.");
                    }
                }else if ( resultCode == ScreenAuthenticationWebView.RETURN_ON_AUTHENTICATION_LISTENER_OK) {
                    String dataUri;
                    if (data != null &&
                            (dataUri= data.getStringExtra(ScreenAuthenticationWebView.RETURN_ON_AUTHENTICATION_RESPONSE))!=null &&
                            dataUri instanceof String) {
                        URI uri = null;
                        try {
                            uri = new URI(dataUri);
                            Log.i(TAG, "Uri: " + uri.toString());
                            try {
                                if(mService!=null)
                                    mService.authorizeUser(dataUri);
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        } catch (URISyntaxException e) {
                            Log.e(TAG,"Authentication Error: "+e.getMessage());
                            e.printStackTrace();
                        }
                    }else{
                        Log.e(TAG,"Error processing file to import profiles.");
                    }
                }
                break;
        }
    }

    // START GUI
    private void unRegisted(boolean success){
        userData.setRegisted(false);
        userData.setDisplayName(null);
        userData.setMcpttID(null);
        text_info.setText("UNREGISTERED");
        text_status.setText(getString(R.string.text_status));
        btn_call.setEnabled(false);
        btn_call.setBackgroundResource(R.drawable.token_inactive);
        btn_unregister.setEnabled(false);
        btn_register.setEnabled(true);
        btn_er.setEnabled(false);
        reg_status.setEnabled(false);
        switchCompat.setEnabled(false);
        switch_group.setTextColor(ContextCompat.getColor(this, R.color.background));
        switch_private.setTextColor(ContextCompat.getColor(this, R.color.background));
        spinnerGroups.setEnabled(false);
        spinnerUsers.setEnabled(false);
        btn_speaker.setEnabled(false);
        isSpeakerphoneOn=false;
        btn_speaker.setText(R.string.btn_speaker_off);
        registered = false;
        invalidateOptionsMenu();
    }

    private void isRegisted(boolean success,String mcpttID,String displayName){
        userData.setRegisted(success);
        if(mcpttID!=null)
            userData.setMcpttID(mcpttID);
        if(displayName!=null){
            userData.setDisplayName(displayName);
        }
        text_info.setText("REGISTERED - MCPTT ID: " + mcpttID + " DISPLAY NAME: " + displayName);
        text_status.setText(displayName);
        btn_unregister.setEnabled(true);
        btn_register.setEnabled(false);
        btn_call.setBackgroundResource(R.drawable.token_default);
        reg_status.setEnabled(true);
        btn_er.setEnabled(true);
        btn_call.setEnabled(true);
        switchCompat.setEnabled(true);
        switchCompat.setChecked(false);
        switch_group.setTextColor(ContextCompat.getColor(this, R.color.white));
        switch_private.setTextColor(ContextCompat.getColor(this, R.color.white));
        spinnerGroups.setEnabled(true);
        spinnerUsers.setEnabled(false);
        spinnerUsers.setVisibility((View.GONE));
        spinnerGroups.setVisibility((View.VISIBLE));
        btn_speaker.setEnabled(true);
        registered = true;
        invalidateOptionsMenu();
    }

    private void showData(String eventType, String data){
        text_info.setText(eventType + ": " + data);
    }

    private void showLastError(String from, int code, String errorString){
        Log.e(TAG,"ERROR " + from + ": " + code + " " + errorString);
        text_info.setText("ERROR " + from + ": " + code + " " + errorString);
    }

    private void showGroups(Map<String, Integer> groups){
        String result="";
        if(groups!=null){
            groupsCurrent=new ArrayList<>();
            for (String groupID:groups.keySet()){
                String type="";
                switch (ConstantsMCOP.GroupAffiliationEventExtras.GroupAffiliationStateEnum.fromInt(groups.get(groupID))){
                    case notaffiliated:
                        type="notaffiliated";
                        break;
                    case affiliating:
                        type="affiliating";
                        break;
                    case affiliated:
                        type="affiliated";
                        groupsCurrent.add(groupID);
                        break;
                    case deaffiliating:
                        type="deaffiliating";
                        break;
                }
                result=result+"groupID:"+groupID+":"+type+"\n";
            }
            loadGroups();
        }
        text_affiliation.setText("List Affiliation Groups: \n"+result);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        itemIdMSCSM = menu.findItem(R.id.action_registration);
        itemAutoReg = menu.findItem(R.id.action_auto_reg);
        itemExit = menu.findItem(R.id.action_exit);
        if (registered) {
            itemIdMSCSM.setVisible(false);
            itemAutoReg.setVisible(false);
            itemExit.setVisible(false);
        } else {
            itemIdMSCSM.setVisible(true);
            itemAutoReg.setVisible(true);
            itemExit.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean result;
        result = super.onOptionsItemSelected(item);
        // Handle action bar item clicks. The action bar will
        // automatically handle clicks on the Home/Up button, as long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        // No inspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_registration:
                if (BuildConfig.DEBUG) Log.d(TAG, "Selected Registration configuration");
                mDialogMenu = DialogMenu.newInstance(regConfig, IdMSCMS?"IdMS/CMS":"None");
                mDialogMenu.setOnClickItemListener(new DialogMenu.OnClickListener() {
                    @Override
                    public void onClickItem(int item) {
                        if(item>=0 && regConfig.length>item){
                            Log.d(TAG,"Selected registration configuration: " + regConfig[item]);
                            if (regConfig[item].equals("IdMS/CMS")) {
                                IdMSCMS = true;
                                Toast.makeText(getApplicationContext(),"IdMS/CMS Registration",Toast.LENGTH_SHORT).show();
                            } else if (regConfig[item].equals("None")) {
                                IdMSCMS = false;
                                Toast.makeText(getApplicationContext(),"None",Toast.LENGTH_SHORT).show();
                            }
                        }
                        saveConfiguration();
                    }
                });
                mDialogMenu.show(getSupportFragmentManager(), "SimpleDialog");
                break;
            case R.id.action_auto_reg:
                if (BuildConfig.DEBUG) Log.d(TAG, "Selected Auto-Registration");
                mDialogMenu = DialogMenu.newInstance(autoReg, autoRegister?"Automatic":"Manual");
                mDialogMenu.setOnClickItemListener(new DialogMenu.OnClickListener() {
                    @Override
                    public void onClickItem(int item) {
                        if(item>=0 && autoReg.length>item){
                            Log.d(TAG,"Selected registration configuration: " + autoReg[item]);
                            if (autoReg[item].equals("Automatic")) {
                                autoRegister = true;
                                Toast.makeText(getApplicationContext(),"Auto-Registration",Toast.LENGTH_SHORT).show();
                                register();
                            } else if (autoReg[item].equals("Manual")) {
                                autoRegister = false;
                                Toast.makeText(getApplicationContext(),"Manual Registration",Toast.LENGTH_SHORT).show();
                            }
                        }
                        saveConfiguration();
                    }
                });
                mDialogMenu.show(getSupportFragmentManager(), "SimpleDialog");
                break;
            case R.id.action_about:
                if (BuildConfig.DEBUG) Log.d(TAG, "Selected About");
                mDialogAlert = DialogAlert.newInstance(getAbout(this), getString(R.string.option_about), false);
                mDialogAlert.show(getSupportFragmentManager(), "SimpleDialog");
                break;
            case R.id.action_exit:
                if (BuildConfig.DEBUG) Log.d(TAG, "Selected Exit");
                saveConfiguration();
                if (mService != null){
                    try {
                        mService.unLoginMCOP();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                android.os.Process.killProcess(android.os.Process.myPid());
                break;
            default:
                break;
        }
        saveConfiguration();
        return result;
    }

    public static String getAbout(Context context){
        if(context==null)return "";
        PackageInfo pInfo = null;
        String version = null;
        try {
            pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            version=null;
        }
        String about=context.getString(R.string.app_name) + "\n";
        about+=context.getString(R.string.Build_version)+": " + version;
        about+="\n"+context.getString(R.string.Developed_by)+": " + context.getString(R.string.web);
        about+="\n"+context.getString(R.string.copyright);

        return about;
    }
    // END GUI

    /**
     * Set permissions for Android 6.0 or above
     */
    protected void setPermissions(){
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
                        new String[]{Manifest.permission.RECORD_AUDIO,Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA,Manifest.permission.READ_PHONE_STATE},
                        GET_PERMISSION);
            } else {
                //No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.RECORD_AUDIO,Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA,Manifest.permission.READ_PHONE_STATE},
                        GET_PERMISSION);

                //MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                //app-defined int constant. The callback method gets the
                //result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions,grantResults);
        switch (requestCode) {
            case GET_PERMISSION: {
                //If request is cancelled, result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Permission was granted, yay! Do the
                    //contacts-related task you need to do.
                    //API>22
                    setPermissionsWriteSetting();
                    connectService();
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
    protected void setPermissionsWriteSetting(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Settings.System.canWrite(this) ){
                //Do stuff here
            } else {
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:"+  this.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }





    private void connectService(){
        connectService(null);
    }


}