/*
  Copyright (C) 2019, University of the Basque Country (UPV/EHU)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/
package org.mcopenplatform.muoapi;

import org.mcopenplatform.muoapi.IMCOPCallback;

/**
 * AIDL definition {@link https://developer.android.com/guide/components/aidl.html}
 * Used as a callback for MCOP SDK server-client communication, and for MCPTT (Mission Critical Push to Talk) Services.
 * @version 0.1
 */
interface IMCOPsdk {
    /**
     * Allows to synchronously get the capabilities from the different MCPTT SDK plugins.
     * @return Returns an structure with all the capabilities.
     * @throws android.os.RemoteException
     */
    String getMCOPCapabilities();



     /**
         * This method starts the MCPTT System Login procedure.
         * No input parameters.
         * In case of re-execution it restarts the login procedure.
         *
         * @return  Indicates if the Login procedure can be started.
         * @see org.mcopenplatform.muoapi.ConstantsMCOP.AuthorizationRequestExtras org.mcopenplatform.muoapi.ConstantsMCOP.AuthorizationRequestExtras (Definition of all the asynchronous response values of this method)
         * @see org.mcopenplatform.muoapi.ConstantsMCOP.AuthorizationRequestExtras org.mcopenplatform.muoapi.ConstantsMCOP.LoginEventExtras (Definition of all the asynchronous response values of this method and the errors)
         *
         */
    boolean loginMCOP();

    /**
     * This method starts the MCPTT System unLogin procedure.
     * No input parameters.
     * In case of re-execution it restarts the unlogin procedure.
     *
     * @return  Indicates if the unLogin procedure can be started.
     * @see org.mcopenplatform.muoapi.ConstantsMCOP.AuthorizationRequestExtras org.mcopenplatform.muoapi.ConstantsMCOP.AuthorizationRequestExtras (Definition of all the asynchronous response values of this method)
     * @see org.mcopenplatform.muoapi.ConstantsMCOP.AuthorizationRequestExtras org.mcopenplatform.muoapi.ConstantsMCOP.LoginEventExtras (Definition of all the asynchronous response values of this method and the errors)
     *
     */
     boolean unLoginMCOP();

     /**
     * Once the authentication procedure has been carried out against a third party,
     * this method must be called to continue with the Login process started
     * with loginMCOP() method, defined in this AIDL.
     *
     * Before making the call to this method, the client must use the "request uri", obtained in the callback "org.mcopenplatform.muoapi.ConstantsMCOP.ActionsCallBack.authorizationRequestEvent",
     * to authenticate against a third party, so that the SDK can verify the authentication with the "url" input parameter.
     *
     * @return  Indicates if the Login procedure can be started.
     * @see org.mcopenplatform.muoapi.ConstantsMCOP.AuthorizationRequestExtras org.mcopenplatform.muoapi.ConstantsMCOP.AuthorizationRequestExtras (Definition of all the asynchronous response values of this method and the errors)
     * @see org.mcopenplatform.muoapi.ConstantsMCOP.AuthorizationRequestExtras org.mcopenplatform.muoapi.ConstantsMCOP.LoginEventExtras (Definition of all the asynchronous response values of this method and the errors)
     * @param url. String with URL format where the parameters are indicated so that the SDK can authenticate the user.
     */
    boolean authorizeUser(String url);

    /**
     *
     * Method to make MCPTT type calls. Allowed call types are limited.
     * MCPTT services that a call can contain are listed in {@link org.mcopenplatform.muoapi.ConstantsMCOP.CallTypeEnum}
     *
     * @return Indicates whether the make call procedure could be started. In case of invalid MCPTT session, FALSE is returned.
     * @see org.mcopenplatform.muoapi.ConstantsMCOP.CallTypeEnum org.mcopenplatform.muoapi.ConstantsMCOP.CallTypeEnum (Definition of the MCPTT Services a call may contain)
     * @see org.mcopenplatform.muoapi.ConstantsMCOP.CallEventExtras org.mcopenplatform.muoapi.ConstantsMCOP.CallEventExtras (Definition of the different asynchronous responses this method can have through the callback)
     * @param userID. String to identify the MCOP system user or group to call. If no identity is indicated, the selected contact will be called.
     * @param callType. Integer that contains a series of FLAGs indicating the services for the new MCPTT session.
     */
    boolean makeCall(String userID, int callType);

    /**
     * Hang up an incoming, established or initiated MCPTT session.
     * @param sessionID. Identifier of the specific MCPTT session where the specific action is carried out.
     * @return Indicates whether the desired operation can be started.
     * @throws android.os.RemoteException
     */
    boolean hangUpCall(String sessionID);

    /**
     * Accept an incoming MCPTT sesion.
     * @param sessionID. Identifier of the specific MCPTT session where the specific action is carried out.
     * @return Indicates whether the desired operation can be started.
     * @throws android.os.RemoteException
     */
    boolean acceptCall(String sessionID);

    /**
     * Update the emergency state of a particular session.
     * @param sessionID. Identifier of the specific MCPTT session to be updated.
     * @param emergencyType. New emergency state of the session. It must be type {@link org.mcopenplatform.muoapi.ConstantsMCOP.EmergencyTypeEnum}
     * @return Indicates whether the desired operation can be started.
     * @see org.mcopenplatform.muoapi.ConstantsMCOP.EmergencyTypeEnum
     * @throws android.os.RemoteException
     */
    boolean updateEmergencyState(String sessionID, int emergencyType);

    /**
     * Perform floor control operations over MCPTT sessions.
     * @param sessionID Identifier of the specific MCPTT session where the specific action is carried out.
     * @param requestType Type of action to be performed. It must be type {@link org.mcopenplatform.muoapi.ConstantsMCOP.FloorControlEventExtras.FloorControlOperationTypeEnum}
     * @param UserID To be used in Video sessions, indicating the specific user to receive video from.
     * @return Indicates whether the desired operation can be started.
     * @throws android.os.RemoteException
     */
    boolean floorControlOperation(String sessionID, int requestType, String UserID);

    /**
     * Requests group information to the SDK,
     * generating a type {@link org.mcopenplatform.muoapi.ConstantsMCOP.ActionsCallBack#groupInfoEvent} event callback.
     * @return Indicates whether the desired operation can be started.
     * @throws android.os.RemoteException
     */
    boolean updateGroupsInfo();

    /**
     * Requests group affiliation information to the SDK,
     * generating a type {@link org.mcopenplatform.muoapi.ConstantsMCOP.ActionsCallBack#groupAffiliationEvent} event callback.
     * @return Indicates whether the desired operation can be started.
     * @throws android.os.RemoteException
     */
    boolean updateGroupsAffiliation();

    /**
     * Perform a group affiliation operation.
     * @param groupMcpttId. Unequivocally identifies the group on which the operation is carried out. The group list will be received in the event {@link ConstantsMCOP.ActionsCallBack#groupAffiliationEvent}
     * @param affiliationOperation. Operation to perform on the indicated group. Allowed actions: {@link org.mcopenplatform.muoapi.ConstantsMCOP.GroupAffiliationEventExtras.AffiliationOperationTypeEnum}.
     * @return Indicates whether the desired operation can be started.
     * @See org.mcopenplatform.muoapi.ConstantsMCOP.GroupAffiliationEventExtras
     * @throws android.os.RemoteException
     */
    boolean groupAffiliationOperation(String groupMcpttId, int affiliationOperation);

    /**
     * Select default contact (user or group).
     * @param groupID. Unequivocal identifier of the MCPTT group defined for default MCPTT sessions.
     * @return Indicates whether the desired operation can be started.
     * @See org.mcopenplatform.muoapi.ConstantsMCOP.SelectedContactChangeEventExtras
     * @throws android.os.RemoteException
     */
    boolean changeSelectedContact(String groupID);

     /**
     * Register the callback defined by AIDL so that the SDK can send asynchronous events to the user.
     * @param mcopCallBack. Callback where the different events are sent.
     * @return Indicates whether the desired operation can be started.
     * @see org.mcopenplatform.muoapi.ConstantsMCOP.ActionsCallBack
     * @throws android.os.RemoteException
     */
    boolean registerCallback(IMCOPCallback mcopCallBack);
}
