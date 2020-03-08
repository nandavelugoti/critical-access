package org.test.client.mcopclient.controller.events;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import org.test.client.mcopclient.ConstantsMCOP;
import org.test.client.mcopclient.CriticalAccess;
import org.test.client.mcopclient.controller.MCOPCallManager;
import org.test.client.mcopclient.controller.MCOPServiceManager;
import org.test.client.mcopclient.model.AddressBook;
import org.test.client.mcopclient.model.User;
import org.test.client.mcopclient.model.calls.StatusTokenType;
import org.test.client.mcopclient.view.HomePage;

import static org.test.client.mcopclient.ConstantsMCOP.ERROR_CODE_DEFAULT;
import static org.test.client.mcopclient.ConstantsMCOP.VALUE_BOOLEAN_DEFAULT;

public class FloorControlEvent implements EventListener {
    private final static String TAG = FloorControlEvent.class.getCanonicalName();

    @Override
    public void handleEvent(Intent action) {
        String sessionID = null;
        if ((action.getIntExtra(ConstantsMCOP.FloorControlEventExtras.ERROR_CODE, ERROR_CODE_DEFAULT)) != ERROR_CODE_DEFAULT) {
            // Error in unLoginEvent
            sessionID = action.getStringExtra(ConstantsMCOP.FloorControlEventExtras.SESSION_ID);
            String stringError = action.getStringExtra(ConstantsMCOP.UnLoginEventExtras.ERROR_STRING);
            Toast.makeText(CriticalAccess.getContext(), stringError, Toast.LENGTH_SHORT).show();
        } else {
            // No error
            boolean success = false;
            String eventFloorControl = action.getStringExtra(ConstantsMCOP.FloorControlEventExtras.FLOOR_CONTROL_EVENT);
            String causeString = null;
            int causeInt = -1;
            try {
                sessionID = action.getStringExtra(ConstantsMCOP.CallEventExtras.SESSION_ID);
                switch (ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum.fromString(eventFloorControl)) {
                    case none:
                        MCOPCallManager.setCurrentStatusToken(StatusTokenType.NONE);
                        MCOPCallManager.setTokenHolder(new User("N/A", "N/A"));
                        break;
                    case granted:
                        Log.d(TAG, "TOKEN GRANTED");
                        MCOPCallManager.setCurrentStatusToken(StatusTokenType.GRANTED);
                        int durationGranted = action.getIntExtra(ConstantsMCOP.FloorControlEventExtras.DURATION_TOKEN, ERROR_CODE_DEFAULT);
                        Toast.makeText(CriticalAccess.getContext(), "Duration Granted: " + durationGranted, Toast.LENGTH_SHORT).show();
                        MCOPCallManager.setTokenHolder(MCOPServiceManager.AddressBook.getCurrentUser());
                        break;
                    case idle:
                        Log.d(TAG, "TOKEN IDLE");
                        MCOPCallManager.setCurrentStatusToken(StatusTokenType.IDLE);
                        MCOPCallManager.setTokenHolder(new User("N/A", "N/A"));
                        break;
                    case taken:
                        Log.d(TAG, "TOKEN TAKEN");
                        String userIDTaken = action.getStringExtra(ConstantsMCOP.FloorControlEventExtras.USER_ID);
                        String displayNameTaken = action.getStringExtra(ConstantsMCOP.FloorControlEventExtras.DISPLAY_NAME);
                        boolean allow_request = action.getBooleanExtra(ConstantsMCOP.FloorControlEventExtras.ALLOW_REQUEST, VALUE_BOOLEAN_DEFAULT);
                        MCOPCallManager.setCurrentStatusToken(StatusTokenType.TAKEN);
                        MCOPCallManager.setTokenHolder(new User(userIDTaken, displayNameTaken));
                        break;
                    case denied:
                        Log.d(TAG, "TOKEN DENIED");
                        causeString = action.getStringExtra(ConstantsMCOP.FloorControlEventExtras.CAUSE_STRING);
                        causeInt = action.getIntExtra(ConstantsMCOP.FloorControlEventExtras.CAUSE_CODE, ERROR_CODE_DEFAULT);
                        Toast.makeText(CriticalAccess.getContext(), "Token Denied: " + causeString + " - " + causeInt, Toast.LENGTH_SHORT).show();
                        break;
                    case revoked:
                        Log.d(TAG, "TOKEN REVOKED");
                        causeString = action.getStringExtra(ConstantsMCOP.FloorControlEventExtras.CAUSE_STRING);
                        causeInt = action.getIntExtra(ConstantsMCOP.FloorControlEventExtras.CAUSE_CODE, ERROR_CODE_DEFAULT);
                        Toast.makeText(CriticalAccess.getContext(), "Token Revoked: " + causeString + " - " + causeInt, Toast.LENGTH_SHORT).show();

                        break;
                    default:
                        break;
                }
                HomePage.updateBtnPTT();
                HomePage.updateCallerId();
                HomePage.updateCaller();
            } catch (Exception e) {

            }
        }
    }
}