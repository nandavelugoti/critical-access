package org.test.client.mcopclient.controller;

import android.content.Intent;
import android.os.Build;
import android.os.RemoteException;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;

import org.mcopenplatform.muoapi.IMCOPCallback;
import org.test.client.mcopclient.BuildConfig;
import org.test.client.mcopclient.CriticalAccess;
import org.test.client.mcopclient.view.MainActivity;
import org.test.client.mcopclient.R;
import org.test.client.mcopclient.view.ScreenAuthenticationWebView;
import org.test.client.mcopclient.model.ConstantsMCOP;
import org.test.client.mcopclient.model.calls.CallEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MCOPCallbackManager extends IMCOPCallback.Stub {
    private final static String TAG = MCOPCallbackManager.class.getCanonicalName();
    @Override
    public void handleOnEvent(final List<Intent> actionList) {
        for(Intent action : actionList){
            int codeError=-1;
            int eventTypeInt=-1;
            String stringError=null;
            String sessionID=null;
            int callType=0;
            if(action!=null && action.getAction()!=null && !action.getAction().trim().isEmpty())
                try {

                    switch (ConstantsMCOP.ActionsCallBack.fromString(action.getAction())){
                        case none:
                            if(BuildConfig.DEBUG) Log.d(TAG,"none");
                            break;
                        case authorizationRequestEvent:
                            codeError=-1;
                            if((codeError=action.getIntExtra(ConstantsMCOP.AuthorizationRequestExtras.ERROR_CODE,ERROR_CODE_DEFAULT))!=ERROR_CODE_DEFAULT){
                                // Error in authorizationRequestEvent
                                stringError=action.getStringExtra(ConstantsMCOP.AuthorizationRequestExtras.ERROR_STRING);
                            }else  {
                                // No error
                                String requestUri=null;
                                String redirect=null;
                                if((requestUri=action.getStringExtra(ConstantsMCOP.AuthorizationRequestExtras.REQUEST_URI))!=null &&
                                        (redirect=action.getStringExtra(ConstantsMCOP.AuthorizationRequestExtras.REDIRECT_URI))!=null
                                ){
                                    if(BuildConfig.DEBUG)Log.d(TAG,"onAuthentication URI: "+requestUri+" redirectionURI: "+redirect);
                                    Intent intent2 = new Intent(CriticalAccess.getContext(), ScreenAuthenticationWebView.class);
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
                                        mState = MainActivity.State.NONE;
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
                                            mState = MainActivity.State.GRANTED;
                                            btn_call.setEnabled(true);
                                            btn_hangup.setEnabled(true);
                                            break;
                                        case idle:
                                            Log.d(TAG,"TOKEN IDLE");
                                            showData("floorControl ("+sessionID+")"," idle");
                                            btn_call.setBackgroundResource(R.drawable.token_gray);
                                            mState = MainActivity.State.IDLE;
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
                                            mState = MainActivity.State.TAKEN;
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

                                        break;
                                    case eMBMSBearerNotInUse:
                                        if(BuildConfig.DEBUG)Log.d(TAG,"Receipt eMBMS bearer not in use");
                                        TMGI = action.getStringExtra(ConstantsMCOP.EMBMSNotificationEventExtras.TMGI);
                                        sessionID = action.getStringExtra(ConstantsMCOP.EMBMSNotificationEventExtras.SESSION_ID);
                                        areaList = action.getStringExtra(ConstantsMCOP.EMBMSNotificationEventExtras.AREA_LIST);

                                        break;
                                    case NoeMBMSCoverage:
                                        if(BuildConfig.DEBUG)Log.d(TAG,"Receipt eMBMS not under coverage");
                                        TMGI = action.getStringExtra(ConstantsMCOP.EMBMSNotificationEventExtras.TMGI);
                                        sessionID = action.getStringExtra(ConstantsMCOP.EMBMSNotificationEventExtras.SESSION_ID);
                                        areaList = action.getStringExtra(ConstantsMCOP.EMBMSNotificationEventExtras.AREA_LIST);
                                        break;
                                    case eMBMSNotAvailable:

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
}
