package org.test.client.mcopclient.model.calls;

import android.os.RemoteException;
import android.util.Log;

import org.mcopenplatform.muoapi.IMCOPsdk;
import org.test.client.mcopclient.R;
import org.test.client.mcopclient.model.User;
import org.test.client.mcopclient.view.MainActivity;
import org.test.client.mcopclient.model.ConstantsMCOP;

public class Call implements Callable {
    final static String TAG = MainActivity.class.getCanonicalName();
    private String selUser = "sip:mcptt_id_iit2_A@organization.org";
    private String selGroup = "sip:iit2_group@organization.org";

    private IMCOPsdk mService;
    private enum CallType {
        PRIVATE,
        GROUP,
        BROADCAST,
        // AMBIENT,
        CHATGROUP
    }
    private CallType mCallType = CallType.GROUP;

    private int priority;  // 0 = Normal Call, 1 = Imminent Peril, 2 = Emergency Call
    private int status;  // 0 = ringing, 1 = accepted, 2 = rejected, 3 = active
    private User caller;
    private

    Call(User callerid) {

        this.caller = callerid;

    }

    public int getStatus() {
        return status;
    }

    @Override
    public void call() {
        if (priority == 0) {
            // Normal Calls
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
            } else if (mCallType == CallType.BROADCAST) {
                // Broadcast Call
                try {
                    Log.d(TAG,"Call type: " + mCallType);
                    if(mService!=null)
                        mService.makeCall(
                                selGroup,
                                ConstantsMCOP.CallEventExtras.CallTypeEnum.Audio.getValue() |
                                        ConstantsMCOP.CallEventExtras.CallTypeEnum.WithFloorCtrl.getValue() |
                                        ConstantsMCOP.CallEventExtras.CallTypeEnum.Broadcast.getValue());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else if (mCallType == CallType.CHATGROUP) {
                // Chatgroup Call
                try {
                    Log.d(TAG,"Call type: " + mCallType);
                    if(mService!=null)
                        mService.makeCall(
                                selGroup,
                                ConstantsMCOP.CallEventExtras.CallTypeEnum.Audio.getValue() |
                                        ConstantsMCOP.CallEventExtras.CallTypeEnum.WithFloorCtrl.getValue() |
                                        ConstantsMCOP.CallEventExtras.CallTypeEnum.ChatGroup.getValue());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        } else if (priority == 1) {
            // Imminent Peril
            if (mCallType == CallType.GROUP) {
                // Group Call
                try {
                    Log.d(TAG,"Call type: Emergency " + mCallType);
                    if(mService!=null)
                        mService.makeCall(
                                selGroup, //DEFAULT_GROUP,
                                ConstantsMCOP.CallEventExtras.CallTypeEnum.Audio.getValue() |
                                        ConstantsMCOP.CallEventExtras.CallTypeEnum.WithFloorCtrl.getValue() |
                                        ConstantsMCOP.CallEventExtras.CallTypeEnum.PrearrangedGroup.getValue() |
                                        ConstantsMCOP.CallEventExtras.CallTypeEnum.ImminentPeril.getValue());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else if (mCallType == CallType.BROADCAST) {
                // Broadcast Call
                try {
                    Log.d(TAG,"Call type: " + mCallType);
                    if(mService!=null)
                        mService.makeCall(
                                selGroup,
                                ConstantsMCOP.CallEventExtras.CallTypeEnum.Audio.getValue() |
                                        ConstantsMCOP.CallEventExtras.CallTypeEnum.WithFloorCtrl.getValue() |
                                        ConstantsMCOP.CallEventExtras.CallTypeEnum.Broadcast.getValue() |
                                        ConstantsMCOP.CallEventExtras.CallTypeEnum.ImminentPeril.getValue());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else if (mCallType == CallType.CHATGROUP) {
                // Chatgroup Call
                try {
                    Log.d(TAG,"Call type: " + mCallType);
                    if(mService!=null)
                        mService.makeCall(
                                selGroup,
                                ConstantsMCOP.CallEventExtras.CallTypeEnum.Audio.getValue() |
                                        ConstantsMCOP.CallEventExtras.CallTypeEnum.WithFloorCtrl.getValue() |
                                        ConstantsMCOP.CallEventExtras.CallTypeEnum.ChatGroup.getValue() |
                                        ConstantsMCOP.CallEventExtras.CallTypeEnum.ImminentPeril.getValue());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        } else if (priority == 2) {
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
            } else if (mCallType == CallType.BROADCAST) {
                // Broadcast Call
                try {
                    Log.d(TAG,"Call type: " + mCallType);
                    if(mService!=null)
                        mService.makeCall(
                                selGroup,
                                ConstantsMCOP.CallEventExtras.CallTypeEnum.Audio.getValue() |
                                        ConstantsMCOP.CallEventExtras.CallTypeEnum.WithFloorCtrl.getValue() |
                                        ConstantsMCOP.CallEventExtras.CallTypeEnum.Broadcast.getValue() |
                                        ConstantsMCOP.CallEventExtras.CallTypeEnum.Emergency.getValue());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else if (mCallType == CallType.CHATGROUP) {
                // Chatgroup Call
                try {
                    Log.d(TAG,"Call type: " + mCallType);
                    if(mService!=null)
                        mService.makeCall(
                                selGroup,
                                ConstantsMCOP.CallEventExtras.CallTypeEnum.Audio.getValue() |
                                        ConstantsMCOP.CallEventExtras.CallTypeEnum.WithFloorCtrl.getValue() |
                                        ConstantsMCOP.CallEventExtras.CallTypeEnum.ChatGroup.getValue() |
                                        ConstantsMCOP.CallEventExtras.CallTypeEnum.Emergency.getValue());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void hangup(String sessionid) {
        try {
            if(mService!=null)
                mService.hangUpCall(sessionid);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update() {
        //priority = updatedPriority;
    }
}