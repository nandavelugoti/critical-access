package org.test.client.mcopclient.controller.events;

import android.content.Intent;
import android.util.Log;

import org.test.client.mcopclient.ConstantsMCOP;

import static org.test.client.mcopclient.ConstantsMCOP.ERROR_CODE_DEFAULT;
import static org.test.client.mcopclient.ConstantsMCOP.VALUE_BOOLEAN_DEFAULT;

public class FloorControlEvent implements EventListener {
    private final static String TAG = FloorControlEvent.class.getCanonicalName();

    @Override
    public void handleEvent(Intent action) {
        String sessionID = null;
        if((action.getIntExtra(ConstantsMCOP.FloorControlEventExtras.ERROR_CODE,ERROR_CODE_DEFAULT))!=ERROR_CODE_DEFAULT){
            // Error in unLoginEvent
            sessionID=action.getStringExtra(ConstantsMCOP.FloorControlEventExtras.SESSION_ID);
            String stringError=action.getStringExtra(ConstantsMCOP.UnLoginEventExtras.ERROR_STRING);
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
                        break;
                    case idle:
                        Log.d(TAG,"TOKEN IDLE");
                        break;
                    case taken:
                        Log.d(TAG,"TOKEN TAKEN");
                        String userIDTaken=action.getStringExtra(ConstantsMCOP.FloorControlEventExtras.USER_ID);
                        String displayNameTaken=action.getStringExtra(ConstantsMCOP.FloorControlEventExtras.DISPLAY_NAME);
                        boolean allow_request=action.getBooleanExtra(ConstantsMCOP.FloorControlEventExtras.ALLOW_REQUEST,VALUE_BOOLEAN_DEFAULT);
                        break;
                    case denied:
                        Log.d(TAG,"TOKEN DENIED");
                        causeString=action.getStringExtra(ConstantsMCOP.FloorControlEventExtras.CAUSE_STRING);
                        causeInt=action.getIntExtra(ConstantsMCOP.FloorControlEventExtras.CAUSE_CODE,ERROR_CODE_DEFAULT);
                        break;
                    case revoked:
                        Log.d(TAG,"TOKEN REVOKED");
                        causeString=action.getStringExtra(ConstantsMCOP.FloorControlEventExtras.CAUSE_STRING);
                        causeInt=action.getIntExtra(ConstantsMCOP.FloorControlEventExtras.CAUSE_CODE,ERROR_CODE_DEFAULT);
                        break;
                    default:
                        break;
                }
            }catch (Exception e){

            }
        }
    }
}