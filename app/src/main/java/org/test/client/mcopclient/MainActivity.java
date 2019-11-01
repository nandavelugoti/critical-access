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

package org.test.client.mcopclient;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
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

import org.mcopenplatform.muoapi.IMCOPCallback;
import org.mcopenplatform.muoapi.IMCOPsdk;
import org.test.client.mcopclient.datatype.UserData;
import org.test.client.mcopclient.preference.PreferencesManagerDefault;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class MainActivity extends AppCompatActivity {
    private final static String TAG = MainActivity.class.getCanonicalName();

    private ServiceConnection mConnection;
    private IMCOPsdk mService;
    private IMCOPCallback mMCOPCallback;
    private boolean isConnect=false;
    private static final int ERROR_CODE_DEFAULT=-1;
    private static final int AUTHETICATION_RESULT=101;
    private static final int GET_PERMISSION = 102;
    private static final boolean VALUE_BOOLEAN_DEFAULT=false;
    private static UserData userData;
    private static final int DEFAULT_REGISTER_DELAY = 3000;

    private static final String ACTION_BUTTON_PTT_DOWN_BITTIUM="com.elektrobit.pttbutton.PTTBUTTON_DOWN";
    private static final String ACTION_BUTTON_PTT_UP_BITTIUM="com.elektrobit.pttbutton.PTTBUTTON_UP";
    private static final String ACTION_BUTTON_PTT_LONG_PRESS_BITTIUM="com.elektrobit.pttbutton.PTTBUTTON_LONG_PRESS";
    private BroadcastReceiver mButtonPTTBroadCastRecvMCPTT;

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
    private PreferencesManagerDefault preferencesManager;
    private static final String PARAMETER_PROFILE = "parameters";
    private static final String PARAMETER_SAVE_PROFILE = "TAG.PARAMETER_SAVE_PROFILE";
    private static final String PARAMETER_CONFIG = "configuration";
    private static final String PARAMETER_CONFIG_IDMSCMS = "TAG.PARAMETER_CONFIG_IDMSCMS";
    private static final String PARAMETER_CONFIG_AUTOREGISTER = "TAG.PARAMETER_CONFIG_AUTOREGISTER";
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
    private boolean IdMSCMS = false;
    private boolean autoRegister = false;
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
    private boolean mERState = false;
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
        isSpeakerphoneOn = false;
        AudioManager mAudioManager;
        mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.setSpeakerphoneOn(isSpeakerphoneOn);

        //Dissable logging messages. Enable just for debugging
        text_info.setVisibility((View.GONE));
        text_error.setVisibility((View.GONE));
        text_affiliation.setVisibility((View.GONE));

        preferencesManager=new PreferencesManagerDefault();
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
        ArrayList<String> strings=getIntent().getStringArrayListExtra(PARAMETER_PROFILE);
        Map<String, String> parameterClients= getProfilesParameters(strings);
        if(parameterClients!=null && !parameterClients.isEmpty())
            clients=parameterClients;

        loadConfiguration();

        if(userData==null);
        userData=new UserData();

        mMCOPCallback=new IMCOPCallback.Stub() {
            @Override
            public void handleOnEvent(final List<Intent> actionList) throws RemoteException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for(Intent action:actionList){
                            int codeError=-1;
                            int eventTypeInt=-1;
                            String stringError=null;
                            String sessionID=null;
                            int callType=0;
                            if(action!=null && action.getAction()!=null && !action.getAction().trim().isEmpty())
                                try {

                                    switch (ConstantsMCOP.ActionsCallBack.fromString(action.getAction())){
                                        case none:
                                            if(BuildConfig.DEBUG)Log.d(TAG,"none");
                                            break;
                                        case authorizationRequestEvent:
                                            codeError=-1;
                                            if((codeError=action.getIntExtra(ConstantsMCOP.AuthorizationRequestExtras.ERROR_CODE,ERROR_CODE_DEFAULT))!=ERROR_CODE_DEFAULT){
                                                // Error in authorizationRequestEvent
                                                stringError=action.getStringExtra(ConstantsMCOP.AuthorizationRequestExtras.ERROR_STRING);
                                                showLastError("authorizationRequestEvent",codeError,stringError);
                                            }else  {
                                                // No error
                                                String requestUri=null;
                                                String redirect=null;
                                                if((requestUri=action.getStringExtra(ConstantsMCOP.AuthorizationRequestExtras.REQUEST_URI))!=null &&
                                                        (redirect=action.getStringExtra(ConstantsMCOP.AuthorizationRequestExtras.REDIRECT_URI))!=null
                                                        ){
                                                    if(BuildConfig.DEBUG)Log.d(TAG,"onAuthentication URI: "+requestUri+" redirectionURI: "+redirect);
                                                    Intent intent2 = new Intent(getApplicationContext(), ScreenAuthenticationWebView.class);
                                                    intent2.putExtra(ScreenAuthenticationWebView.DATA_URI_INTENT,requestUri.trim());
                                                    intent2.putExtra(ScreenAuthenticationWebView.DATA_REDIRECTION_URI,redirect.trim());
                                                    startActivityForResult(intent2,AUTHETICATION_RESULT);
                                                }
                                            }
                                            break;
                                        case loginEvent:
                                            codeError=-1;
                                            if((codeError=action.getIntExtra(ConstantsMCOP.LoginEventExtras.ERROR_CODE,ERROR_CODE_DEFAULT))!=ERROR_CODE_DEFAULT){
                                                // Error in LoginEvent
                                                stringError=action.getStringExtra(ConstantsMCOP.LoginEventExtras.ERROR_STRING);
                                                showLastError("LoginEvent",codeError,stringError);
                                            }else  {
                                                // No error
                                                boolean success=false;
                                                String mcptt_id=null;
                                                String displayName=null;
                                                if((success=action.getBooleanExtra(ConstantsMCOP.LoginEventExtras.SUCCESS,VALUE_BOOLEAN_DEFAULT))==true &&
                                                        (mcptt_id=action.getStringExtra(ConstantsMCOP.LoginEventExtras.MCPTT_ID))!=null
                                                        ){
                                                    if(BuildConfig.DEBUG)Log.d(TAG,"Login success: "+success+" mcptt_id: "+mcptt_id);
                                                    displayName=action.getStringExtra(ConstantsMCOP.LoginEventExtras.DISPLAY_NAME);
                                                    isRegisted(success,mcptt_id,displayName);
                                                }else{
                                                    Log.e(TAG,"Error: Registration process");
                                                }
                                            }
                                            break;
                                        case unLoginEvent:
                                            codeError=-1;
                                            if((codeError=action.getIntExtra(ConstantsMCOP.UnLoginEventExtras.ERROR_CODE,ERROR_CODE_DEFAULT))!=ERROR_CODE_DEFAULT){
                                                // Error in unLoginEvent
                                                stringError=action.getStringExtra(ConstantsMCOP.UnLoginEventExtras.ERROR_STRING);
                                                showLastError("unLoginEvent",codeError,stringError);
                                            }else  {
                                                // No error
                                                boolean success=false;
                                                if((success=action.getBooleanExtra(ConstantsMCOP.UnLoginEventExtras.SUCCESS,VALUE_BOOLEAN_DEFAULT))==true){
                                                    unRegisted(success);
                                                }else{
                                                    Log.e(TAG,"Error: Unregistration process");
                                                }
                                            }
                                            break;
                                        case configurationUpdateEvent:
                                            break;
                                        case callEvent:
                                            codeError=-1;
                                            eventTypeInt=action.getIntExtra(ConstantsMCOP.CallEventExtras.EVENT_TYPE,ERROR_CODE_DEFAULT);
                                            ConstantsMCOP.CallEventExtras.CallEventEventTypeEnum eventTypeCall=null;

                                            if(eventTypeInt!=ERROR_CODE_DEFAULT &&
                                                    (eventTypeCall=ConstantsMCOP.CallEventExtras.CallEventEventTypeEnum.fromInt(eventTypeInt))!=null ){
                                                switch (eventTypeCall) {
                                                    case NONE:
                                                        break;
                                                    case INCOMING:
                                                        Log.d(TAG,"STATE: INCOMING");
                                                        stringError=action.getStringExtra(ConstantsMCOP.CallEventExtras.ERROR_STRING);
                                                        sessionID=action.getStringExtra(ConstantsMCOP.CallEventExtras.SESSION_ID);
                                                        String callerID=action.getStringExtra(ConstantsMCOP.CallEventExtras.CALLER_USERID);
                                                        callType=action.getIntExtra(ConstantsMCOP.CallEventExtras.CALL_TYPE,ERROR_CODE_DEFAULT);
                                                        if (CallEvent.validationCallType(callType) == CallEvent.CallTypeValidEnum.AudioWithFloorCtrlPrivateEmergency) {
                                                            Log.d(TAG,"Prearranged Emergency Group Call");
                                                            startERState();
                                                        } else if (CallEvent.validationCallType(callType) == CallEvent.CallTypeValidEnum.AudioWithFloorCtrlPrivateEmergency) {
                                                            Log.d(TAG,"Private Emergency Call");
                                                            startERState();
                                                        }
                                                        if(sessionID!=null)userData.addSessionID(sessionID);
                                                        showData("callEvent ("+sessionID+")","INCOMING -> "+callerID);
                                                        text_talking.setVisibility((View.VISIBLE));
                                                        text_callingid.setVisibility((View.VISIBLE));
                                                        text_callingid.setText(callerID);
                                                        spinnerGroups.setEnabled(false);
                                                        spinnerUsers.setEnabled(false);
                                                        switchCompat.setEnabled(false);
                                                        showIdsAcceptCall(getApplicationContext(), sessionID);
                                                        break;
                                                    case RINGING:
                                                        Log.d(TAG,"STATE: RINGING");
                                                        sessionID=action.getStringExtra(ConstantsMCOP.CallEventExtras.SESSION_ID);
                                                        spinnerGroups.setEnabled(false);
                                                        spinnerUsers.setEnabled(false);
                                                        switchCompat.setEnabled(false);
                                                        showData("callEvent ("+sessionID+")","RINGING");
                                                        if(sessionID!=null)userData.addSessionID(sessionID);
                                                        break;
                                                    case INPROGRESS:
                                                        Log.d(TAG,"STATE: INPROGRESS");
                                                        sessionID=action.getStringExtra(ConstantsMCOP.CallEventExtras.SESSION_ID);
                                                        spinnerGroups.setEnabled(false);
                                                        spinnerUsers.setEnabled(false);
                                                        switchCompat.setEnabled(false);
                                                        btn_er.setEnabled(false);
                                                        showData("callEvent ("+sessionID+")","INPROGRESS");
                                                        if(sessionID!=null)userData.addSessionID(sessionID);
                                                        break;
                                                    case CONNECTED:
                                                        Log.d(TAG,"STATE: CONNECTED");
                                                        sessionID=action.getStringExtra(ConstantsMCOP.CallEventExtras.SESSION_ID);
                                                        callType=action.getIntExtra(ConstantsMCOP.CallEventExtras.CALL_TYPE,ERROR_CODE_DEFAULT);
                                                        if (CallEvent.validationCallType(callType) == CallEvent.CallTypeValidEnum.AudioWithFloorCtrlPrearrangedGroupEmergency) {
                                                            Log.d(TAG,"Prearranged Emergency Group Call");
                                                            startERState();
                                                        } else if (CallEvent.validationCallType(callType) == CallEvent.CallTypeValidEnum.AudioWithFloorCtrlPrivateEmergency) {
                                                            Log.d(TAG,"Private Emergency Call");
                                                            startERState();
                                                        }
                                                        showData("callEvent ("+sessionID+")","CONNECTED");
                                                        if(sessionID!=null)userData.addSessionID(sessionID);
                                                        btn_er.setEnabled(false);
                                                        break;
                                                    case TERMINATED:
                                                        Log.d(TAG,"STATE: TERMINATED");
                                                        sessionID=action.getStringExtra(ConstantsMCOP.CallEventExtras.SESSION_ID);
                                                        spinnerGroups.setEnabled(true);
                                                        spinnerUsers.setEnabled(true);
                                                        switchCompat.setEnabled(true);
                                                        switch_group.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
                                                        switch_private.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
                                                        showData("callEvent ("+sessionID+")","TERMINATED");
                                                        if(sessionID!=null)userData.removeSessionID(sessionID);
                                                        if (mERState == false) {
                                                            endERState();
                                                        }
                                                        btn_call.setBackgroundResource(R.drawable.token_default);
                                                        mState = State.NONE;
                                                        btn_call.setEnabled(true);
                                                        btn_hangup.setEnabled(false);
                                                        btn_er.setEnabled(true);
                                                        text_talking.setVisibility((View.INVISIBLE));
                                                        text_callingid.setVisibility((View.INVISIBLE));
                                                        break;
                                                    case ERROR:
                                                        Log.e(TAG,"STATE: ERROR");
                                                        if((codeError=action.getIntExtra(ConstantsMCOP.CallEventExtras.ERROR_CODE,ERROR_CODE_DEFAULT))!=ERROR_CODE_DEFAULT){
                                                            // Error in callEvent
                                                            stringError=action.getStringExtra(ConstantsMCOP.CallEventExtras.ERROR_STRING);
                                                            sessionID=action.getStringExtra(ConstantsMCOP.CallEventExtras.SESSION_ID);
                                                            showLastError("callEvent ("+sessionID+")",codeError,stringError);
                                                        }
                                                        if(sessionID!=null)userData.addSessionID(sessionID);
                                                        break;
                                                    case UPDATE:
                                                        Log.d(TAG,"STATE: UPDATE");
                                                        sessionID=action.getStringExtra(ConstantsMCOP.CallEventExtras.SESSION_ID);
                                                        int updateCallType=action.getIntExtra(ConstantsMCOP.CallEventExtras.CALL_TYPE,ERROR_CODE_DEFAULT);
                                                        showData("callEvent ("+sessionID+")","UPDATE -> CallType: "+updateCallType);
                                                        if(sessionID!=null)userData.addSessionID(sessionID);
                                                        break;
                                                    default:
                                                        showLastError("callEvent:",999,"RECEIVE EVENT NO VALID");
                                                        break;
                                                }
                                            }else{
                                                showLastError("callEvent:",999,"RECEIVE EVENT NO VALID");
                                            }
                                            break;
                                        case floorControlEvent:
                                            codeError=-1;
                                            if((codeError=action.getIntExtra(ConstantsMCOP.FloorControlEventExtras.ERROR_CODE,ERROR_CODE_DEFAULT))!=ERROR_CODE_DEFAULT){
                                                // Error in unLoginEvent
                                                sessionID=action.getStringExtra(ConstantsMCOP.FloorControlEventExtras.SESSION_ID);
                                                stringError=action.getStringExtra(ConstantsMCOP.UnLoginEventExtras.ERROR_STRING);
                                                showLastError("floorControlEvent("+sessionID+")",codeError,stringError);
                                            }else  {
                                                // No error
                                                boolean success=false;
                                                String eventFloorControl=action.getStringExtra(ConstantsMCOP.FloorControlEventExtras.FLOOR_CONTROL_EVENT);
                                                String causeString=null;
                                                int causeInt=-1;
                                                try{
                                                    sessionID=action.getStringExtra(ConstantsMCOP.CallEventExtras.SESSION_ID);
                                                    switch (ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum.fromString(eventFloorControl)) {
                                                        case none:
                                                            break;
                                                        case granted:
                                                            Log.d(TAG,"TOKEN GRANTED");
                                                            int durationGranted=action.getIntExtra(ConstantsMCOP.FloorControlEventExtras.DURATION_TOKEN,ERROR_CODE_DEFAULT);
                                                            showData("floorControl ("+sessionID+")"," granted "+"-> Duration: "+durationGranted);
                                                            btn_call.setBackgroundResource(R.drawable.token_green);
                                                            mState = State.GRANTED;
                                                            btn_call.setEnabled(true);
                                                            btn_hangup.setEnabled(true);
                                                            break;
                                                        case idle:
                                                            Log.d(TAG,"TOKEN IDLE");
                                                            showData("floorControl ("+sessionID+")"," idle");
                                                            btn_call.setBackgroundResource(R.drawable.token_gray);
                                                            mState = State.IDLE;
                                                            btn_call.setEnabled(true);
                                                            btn_hangup.setEnabled(true);
                                                            text_talking.setVisibility((View.INVISIBLE));
                                                            text_callingid.setVisibility((View.INVISIBLE));
                                                            break;
                                                        case taken:
                                                            Log.d(TAG,"TOKEN TAKEN");
                                                            String userIDTaken=action.getStringExtra(ConstantsMCOP.FloorControlEventExtras.USER_ID);
                                                            String displayNameTaken=action.getStringExtra(ConstantsMCOP.FloorControlEventExtras.DISPLAY_NAME);
                                                            boolean allow_request=action.getBooleanExtra(ConstantsMCOP.FloorControlEventExtras.ALLOW_REQUEST,VALUE_BOOLEAN_DEFAULT);
                                                            showData("floorControl ("+sessionID+")"," granted "+"-> userIDTaken(allowRequest="+allow_request+"):("+userIDTaken+":"+displayNameTaken+")");
                                                            mState = State.TAKEN;
                                                            btn_call.setEnabled(false);
                                                            btn_hangup.setEnabled(true);
                                                            btn_call.setBackgroundResource(R.drawable.token_red);
                                                            text_talking.setVisibility((View.VISIBLE));
                                                            text_callingid.setVisibility((View.VISIBLE));
                                                            text_callingid.setText(userIDTaken);
                                                            break;
                                                        case denied:
                                                            Log.d(TAG,"TOKEN DENIED");
                                                            causeString=action.getStringExtra(ConstantsMCOP.FloorControlEventExtras.CAUSE_STRING);
                                                            causeInt=action.getIntExtra(ConstantsMCOP.FloorControlEventExtras.CAUSE_CODE,ERROR_CODE_DEFAULT);
                                                            showData("floorControl ("+sessionID+")"," denied "+"-> cause("+causeInt+":"+causeString+")");
                                                            break;
                                                        case revoked:
                                                            Log.d(TAG,"TOKEN REVOKED");
                                                            causeString=action.getStringExtra(ConstantsMCOP.FloorControlEventExtras.CAUSE_STRING);
                                                            causeInt=action.getIntExtra(ConstantsMCOP.FloorControlEventExtras.CAUSE_CODE,ERROR_CODE_DEFAULT);
                                                            showData("floorControl ("+sessionID+")"," revoked "+"-> cause("+causeInt+":"+causeString+")");
                                                            break;
                                                        default:
                                                            break;
                                                    }
                                                }catch (Exception e){

                                                }
                                            }

                                            break;
                                        case groupInfoEvent:
                                            break;
                                        case groupAffiliationEvent:
                                            codeError=-1;
                                            eventTypeInt=action.getIntExtra(ConstantsMCOP.GroupAffiliationEventExtras.EVENT_TYPE,ERROR_CODE_DEFAULT);
                                            ConstantsMCOP.GroupAffiliationEventExtras.GroupAffiliationEventTypeEnum eventTypeAffiliation=null;
                                            if(eventTypeInt!=ERROR_CODE_DEFAULT &&
                                                    (eventTypeAffiliation=ConstantsMCOP.GroupAffiliationEventExtras.GroupAffiliationEventTypeEnum.fromInt(eventTypeInt))!=null ){
                                                switch (eventTypeAffiliation) {
                                                    case GROUP_AFFILIATION_UPDATE:
                                                        Map<String, Integer> groups=(HashMap<String, Integer>)action.getSerializableExtra(ConstantsMCOP.GroupAffiliationEventExtras.GROUPS_LIST);
                                                        if(groups!=null)
                                                            showGroups(groups);
                                                        break;
                                                    case GROUP_AFFILIATION_ERROR:
                                                        if((codeError=action.getIntExtra(ConstantsMCOP.GroupAffiliationEventExtras.ERROR_CODE,ERROR_CODE_DEFAULT))!=ERROR_CODE_DEFAULT){
                                                            // Error in unLoginEvent
                                                            stringError=action.getStringExtra(ConstantsMCOP.GroupAffiliationEventExtras.ERROR_STRING);
                                                            String groupID=action.getStringExtra(ConstantsMCOP.GroupAffiliationEventExtras.GROUP_ID);
                                                            showLastError("groupAffiliationEvent ("+groupID+")",codeError,stringError);
                                                        }
                                                        break;
                                                    case REMOTE_AFFILIATION:
                                                        break;
                                                    default:
                                                        showLastError("groupAffiliationEvent:",999,"INVALID RECEIVE EVENT");
                                                        break;
                                                }
                                            }else{
                                                showLastError("groupAffiliationEvent:",999,"INVALID RECEIVE EVENT");
                                            }

                                            break;
                                        case selectedContactChangeEvent:
                                            break;
                                        case eMBMSNotificationEvent:
                                            if(BuildConfig.DEBUG)Log.d(TAG,"Receipt eMBMS Notification Event");
                                            codeError=-1;
                                            String TMGI = null;
                                            String areaList =null;
                                            eventTypeInt=action.getIntExtra(ConstantsMCOP.EMBMSNotificationEventExtras.EVENT_TYPE,ERROR_CODE_DEFAULT);
                                            ConstantsMCOP.EMBMSNotificationEventExtras.EMBMSNotificationEventEventTypeEnum eventTypeEmbms = null;
                                            if(eventTypeInt!=ERROR_CODE_DEFAULT &&
                                                            (eventTypeEmbms=ConstantsMCOP.EMBMSNotificationEventExtras.EMBMSNotificationEventEventTypeEnum.fromInt(eventTypeInt))!=null ){
                                                switch (eventTypeEmbms) {
                                                            case none:
                                                                        break;
                                                            case eMBMSAvailable:
                                                                        if(BuildConfig.DEBUG)Log.d(TAG,"Receipt eMBMS available");
                                                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                                                reg_eMBMS.setEnabled(true);
                                                                        }
                                                                        break;
                                                            case UndereMBMSCoverage:
                                                                        if(BuildConfig.DEBUG)Log.d(TAG,"Receipt eMBMS under coverage");
                                                                        TMGI = action.getStringExtra(ConstantsMCOP.EMBMSNotificationEventExtras.TMGI);
                                                                        sessionID = action.getStringExtra(ConstantsMCOP.EMBMSNotificationEventExtras.SESSION_ID);
                                                                        areaList = action.getStringExtra(ConstantsMCOP.EMBMSNotificationEventExtras.AREA_LIST);
                                                                        break;
                                                            case eMBMSBearerInUse:
                                                                        if(BuildConfig.DEBUG)Log.d(TAG,"Receipt eMBMS bearer in use");
                                                                        TMGI = action.getStringExtra(ConstantsMCOP.EMBMSNotificationEventExtras.TMGI);
                                                                        sessionID = action.getStringExtra(ConstantsMCOP.EMBMSNotificationEventExtras.SESSION_ID);
                                                                        areaList = action.getStringExtra(ConstantsMCOP.EMBMSNotificationEventExtras.AREA_LIST);
                                                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                                            reg_eMBMS.setEnabled(true);
                                                                        } else {
                                                                            reg_eMBMS.setEnabled(true);
                                                                        }
                                                                        break;
                                                            case eMBMSBearerNotInUse:
                                                                        if(BuildConfig.DEBUG)Log.d(TAG,"Receipt eMBMS bearer not in use");
                                                                        TMGI = action.getStringExtra(ConstantsMCOP.EMBMSNotificationEventExtras.TMGI);
                                                                        sessionID = action.getStringExtra(ConstantsMCOP.EMBMSNotificationEventExtras.SESSION_ID);
                                                                        areaList = action.getStringExtra(ConstantsMCOP.EMBMSNotificationEventExtras.AREA_LIST);
                                                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                                            reg_eMBMS.setEnabled(false);
                                                                        } else {
                                                                            reg_eMBMS.setEnabled(false);
                                                                        }
                                                                        break;
                                                            case NoeMBMSCoverage:
                                                                        if(BuildConfig.DEBUG)Log.d(TAG,"Receipt eMBMS not under coverage");
                                                                        TMGI = action.getStringExtra(ConstantsMCOP.EMBMSNotificationEventExtras.TMGI);
                                                                        sessionID = action.getStringExtra(ConstantsMCOP.EMBMSNotificationEventExtras.SESSION_ID);
                                                                        areaList = action.getStringExtra(ConstantsMCOP.EMBMSNotificationEventExtras.AREA_LIST);
                                                                        break;
                                                            case eMBMSNotAvailable:
                                                                        if(BuildConfig.DEBUG)Log.d(TAG,"Receipt eMBMS not available");
                                                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                                            reg_eMBMS.setBackgroundColor(getColor(R.color.unregistered));
                                                                        }
                                                                        break;
                                                            default:
                                                                        showLastError("eMBMSNotificationEvent:",999,"INVALID RECEIVE EVENT");
                                                                        break;
                                                        }
                                            }else{
                                                showLastError("eMBMSNotificationEvent:",999,"INVALID RECEIVE EVENT");
                                            }
                                            break;
                                    }
                                }catch (Exception ex){
                                    Log.e(TAG,"Event Action Error: "+action.getAction()+" error:"+ex.getMessage());
                                }
                        }
                    }
                });
            }
        };

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

        // PTT button on Bittium Devices
        mButtonPTTBroadCastRecvMCPTT=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Log.d(TAG,"PTT button");
                final String action = intent.getAction();
                if(action.compareTo(ACTION_BUTTON_PTT_DOWN_BITTIUM)==0
                         ){

                    Log.d(TAG,"PTT button Down");
                    if(mState==State.IDLE && !showIdsOperationFloorControl(getApplicationContext(), true)){
                        Log.e(TAG,"Error: the device can´t request the Token now");
                    }else if(mState==null || mState==State.NONE){
                        makeCall();
                    }
                }else if(action.compareTo(ACTION_BUTTON_PTT_UP_BITTIUM)==0 && mState==State.GRANTED){
                    Log.d(TAG,"PTT button Up");
                    if(!showIdsOperationFloorControl(getApplicationContext(), false)){
                        Log.e(TAG,"Error: the device can´t release the Token now");
                    }
                }else if(action.compareTo(ACTION_BUTTON_PTT_LONG_PRESS_BITTIUM)==0){
                    Log.d(TAG,"Long PTT button press");
                }
            }
        };
        final IntentFilter intentFilter2 = new IntentFilter();
        intentFilter2.addAction(ACTION_BUTTON_PTT_DOWN_BITTIUM);
        intentFilter2.addAction(ACTION_BUTTON_PTT_UP_BITTIUM);
        intentFilter2.addAction(ACTION_BUTTON_PTT_LONG_PRESS_BITTIUM);

        if(mConnection==null)
            mConnection = new ServiceConnection() {

                @Override
                public void onServiceConnected(ComponentName className, IBinder service) {
                    Log.d(TAG,"Service binded! "+className.getPackageName()+"\n");
                    Toast.makeText(getApplicationContext(),"Binded to MCOP SDK",Toast.LENGTH_SHORT).show();
                    mService = IMCOPsdk.Stub.asInterface(service);

                    try {
                        Log.d(TAG,"execute registerCallback "+mMCOPCallback);
                        mService.registerCallback(mMCOPCallback);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    isConnect=true;

                    // Auto Registration
                    if (autoRegister) {
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
                    connectService(currentProfile);
                }else{
                    connectService();
                }
            }
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
                            (dataError= data.getStringExtra(org.test.client.mcopclient.ScreenAuthenticationWebView.RETURN_ON_AUTHENTICATION_ERROR))!=null &&
                            dataError instanceof String) {
                        Log.e(TAG,"Authentication Error: "+dataError);
                    }else{
                        Log.e(TAG,"Error processing authentication.");
                    }
                }else if ( resultCode == ScreenAuthenticationWebView.RETURN_ON_AUTHENTICATION_LISTENER_OK) {
                    String dataUri;
                    if (data != null &&
                            (dataUri= data.getStringExtra(org.test.client.mcopclient.ScreenAuthenticationWebView.RETURN_ON_AUTHENTICATION_RESPONSE))!=null &&
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

    private Map<String,String> getProfilesParameters(List<String> parameters){
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

    private boolean saveParameters(List<String> parameters){
        if(preferencesManager!=null){
            return preferencesManager.putStringSet(this, PARAMETER_SAVE_PROFILE,new HashSet<String>(parameters));
        }
        return false;
    }

    private ArrayList<String> loadParameters(){
        if(preferencesManager!=null){
            Set<String> stringSet=preferencesManager.getStringSet(this, PARAMETER_SAVE_PROFILE);
            if(stringSet!=null){
                return (new ArrayList<String>(stringSet));
            }
        }
        return null;
    }

    private boolean saveConfiguration(){
        if(preferencesManager!=null){
            if (IdMSCMS) {
                preferencesManager.putString(this, PARAMETER_CONFIG_IDMSCMS, "IdMS/CMS");
            } else {
                preferencesManager.putString(this, PARAMETER_CONFIG_IDMSCMS, "None");
            }
            if (autoRegister) {
                preferencesManager.putString(this, PARAMETER_CONFIG_AUTOREGISTER, "Automatic");
            } else {
                preferencesManager.putString(this, PARAMETER_CONFIG_AUTOREGISTER, "Manual");
            }
            return true;
        }
        return false;
    }

    private void loadConfiguration(){
        if(preferencesManager!=null){
            String idmscms = preferencesManager.getString(this, PARAMETER_CONFIG_IDMSCMS, "None");
            if (idmscms.equals("IdMS/CMS")) {
                IdMSCMS = true;
            } else {
                IdMSCMS = false;
            }
            String auto = preferencesManager.getString(this, PARAMETER_CONFIG_AUTOREGISTER, "Manual");
            if (auto.equals("Automatic")) {
                autoRegister = true;
            } else {
                autoRegister = false;
            }
        }
    }

    private void connectService(){
        connectService(null);
    }

    private void connectService(String client){
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
}