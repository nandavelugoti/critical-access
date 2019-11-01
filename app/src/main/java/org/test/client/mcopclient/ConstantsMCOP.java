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

package org.test.client.mcopclient;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MCOP MCPTT SDK
 * Constants used on AIDLs
 * @version 0.1
 */
public class ConstantsMCOP {

    public static final int NO_ERROR=0;



    /**
     * Key Access to the values of the packet names of Iapi plugins
     * <p>This values are passed as Strings in SDK binding intent</p>
     */
    public static final String CONNECTIVITY_PLUGIN_PACKAGE_ID = "org.mcopenplatform.muoapi.ConstantsMCOP"+".CONNECTIVITY_PLUGIN_PACKAGE_ID";
    public static final String CONNECTIVITY_PLUGIN_SERVICE_ID = "org.mcopenplatform.muoapi.ConstantsMCOP"+".CONNECTIVITY_PLUGIN_SERVICE_ID";
    public static final String SIM_PLUGIN_PACKAGE_ID = "org.mcopenplatform.muoapi.ConstantsMCOP"+".SIM_PLUGIN_PACKAGE_ID";
    public static final String SIM_PLUGIN_SERVICE_ID = "org.mcopenplatform.muoapi.ConstantsMCOP"+".SIM_PLUGIN_SERVICE_ID";
    public static final String CONFIGURATION_PLUGIN_PACKAGE_ID = "org.mcopenplatform.muoapi.ConstantsMCOP"+".CONFIGURATION_PLUGIN_PACKAGE_ID";
    public static final String CONFIGURATION_PLUGIN_SERVICE_ID = "org.mcopenplatform.muoapi.ConstantsMCOP"+".CONFIGURATION_PLUGIN_SERVICE_ID";
    public static final String MBMS_PLUGIN_PACKAGE_ID = "org.mcopenplatform.muoapi.ConstantsMCOP"+".MBMS_PLUGIN_PACKAGE_ID";
    public static final String MBMS_PLUGIN_SERVICE_ID = "org.mcopenplatform.muoapi.ConstantsMCOP"+".MBMS_PLUGIN_SERVICE_ID";


    /**
     *
     * Key Access to the values of event types {@link ConstantsMCOP.ActionsCallBack#unLoginEvent}
     * <p>This class contains all the answers to the actions of methods {@link org.mcopenplatform.muoapi.IMCOPsdk#unLoginMCOP()} </p>
     *
     */
    public static class UnLoginEventExtras{
        /**
         * <h2>Key Access to the response to method {@link org.mcopenplatform.muoapi.IMCOPsdk#unLoginMCOP()}}:</h2>
         * <p>Response: boolean, indicates whether the unlogin procedure was successful or not.</p>
         */
        public static final String SUCCESS="org.mcopenplatform.muoapi.ConstantsMCOP.UnLoginEventExtras"+".SUCCESS";

        /**
         * <h2>Key Access to the error codes of the login and authentication methods.</h2>
         * <p>Response: Integer indicating the error code.</p>
         * <p>Values:</p>
         * <p>  205 It is not possible to unregister because the customer is not registered right now</p>
         * <p>  207 There was an error in the unregistration process</p>
         * <p>0 means no errors.</p>
         * <p></p>
         */
        public static final String ERROR_CODE="org.mcopenplatform.muoapi.ConstantsMCOP.UnLoginEventExtras"+".ERROR_CODE";
        /**
         * <h2>Key Access to error string for the login and authentication methods.</h2>
         * <p>Response: String describing the error.</p>
         */
        public static final String ERROR_STRING="org.mcopenplatform.muoapi.ConstantsMCOP.UnLoginEventExtras"+".ERROR_STRING";
    }

    /**
     *
     * Key Access to the values of event types {@link ConstantsMCOP.ActionsCallBack#loginEvent}
     * <p>This class contains all the answers to the actions of methods {@link org.mcopenplatform.muoapi.IMCOPsdk#loginMCOP()} and {@link org.mcopenplatform.muoapi.IMCOPsdk#authorizeUser(String)} ()}</p>
     *
     */
    public static class LoginEventExtras{
        /**
         * <h2>Key Access to the response to method {@link org.mcopenplatform.muoapi.IMCOPsdk#authorizeUser(String)}:</h2>
         * <p>Response: boolean, indicates whether the login procedure was successful or not.</p>
         */
        public static final String SUCCESS="org.mcopenplatform.muoapi.ConstantsMCOP.LoginEventExtras"+".SUCCESS";
        /**
         * <h2>Key Access to the MCPTT ID of the client once authenticated.</h2>
         * <p>Response: String with URI format that unequivocally identifies the user in the MCPTT system.</p>
         */
        public static final String MCPTT_ID="org.mcopenplatform.muoapi.ConstantsMCOP.LoginEventExtras"+".MCPTT_ID";
        /**
         * <h2>Key Access to the DISPLAY NAME of the client once authenticated.</h2>
         * <p>Response: String that identifies the user with a human-readable alias, more pleasant than a MCPTT identifier.</p>
         */
        public static final String DISPLAY_NAME="org.mcopenplatform.muoapi.ConstantsMCOP.LoginEventExtras"+".DISPLAY_NAME";
        /**
         * <h2>Key Access to the error codes of the login and authentication methods.</h2>
         * <p>Response: Integer indicating the error code.</p>
         * <p>Values:</p>
         * <p>0 means no errors.</p>
         * <p>1XX: xMS Errors:</p>
         * <p>  101 Unknown GMS address</p>
         * <p>  102 Unknown CMS address</p>
         * <p>  103 Unknown KMS address</p>
         * <p>  104 Incorrect answer from GMS</p>
         * <p>  105 Incorrect answer from CMS</p>
         * <p>  106 Incorrect answer from KMS</p>
         * <p>  107 There was error with CMS</p>
         * <p>2XX: IMS or SIM Authentication Errors:</p>
         * <p>  201 IMS registration error</p>
         * <p>  202 IMS authentication error</p>
         * <p>  203 Synchronization error between SIM and IMS</p>
         * <p>  204 SIM access error</p>
         * <p>  205 It is not possible to register because the customer is registered right now</p>
         * <p>  206 the URL for the authentication is not valid</p>
         * <p>  207 There was an error in the registration process</p>
         * <p>  208 Error in the authentication process</p>
         * <p>3XX: IDMS Errors:</p>
         */
        public static final String ERROR_CODE="org.mcopenplatform.muoapi.ConstantsMCOP.LoginEventExtras"+".ERROR_CODE";
        /**
         * <h2>Key Access to error string for the login and authentication methods.</h2>
         * <p>Resputa: String describing the error.</p>
         */
        public static final String ERROR_STRING="org.mcopenplatform.muoapi.ConstantsMCOP.LoginEventExtras"+".ERROR_STRING";
    }

    /**
     *
     * <h1>Keys to access the values of authentication events by methods:</h1>
     * <h2>     {@link org.mcopenplatform.muoapi.IMCOPsdk#loginMCOP()}</h2>
     * <h2>     {@link org.mcopenplatform.muoapi.IMCOPsdk#authorizeUser(String)}</h2>
     */
    public static class AuthorizationRequestExtras{
        /**
         * <h2>Key Access to the uri to request the user authentication to a third party.</h2>
         * <p>This data is received once the call to the method {@link org.mcopenplatform.muoapi.IMCOPsdk#loginMCOP()} is made.</p>
         * <p>Response: URI format string</p>
         */
        public static final String REQUEST_URI="org.mcopenplatform.muoapi.ConstantsMCOP.AuthorizationRequestActions"+".REQUEST_URI";
        /**
         * <h2>Key Access to the uri answered by the third party authentication agent.</h2>
         * <h3>Indicates when the third party authentication ends, and the method {@link org.mcopenplatform.muoapi.IMCOPsdk#authorizeUser(String)} can be called with the authentication response.</h3>
         * <p>This data is received once the call to the method {@link org.mcopenplatform.muoapi.IMCOPsdk#loginMCOP()} is made.</p>
         * <p>Response: URI format string</p>
         */
        public static final String REDIRECT_URI="org.mcopenplatform.muoapi.ConstantsMCOP.AuthorizationRequestActions"+".REDIRECT_URI";
        /**
         * <h2>Key Access to authentication error codes.</h2>
         * <p>Response: Integer indicating the error code.</p>
         * <p>Values:</p>
         * <p></p>
         * <p>0 indicates no errors</p>
         */
        public static final String ERROR_CODE="org.mcopenplatform.muoapi.ConstantsMCOP.AuthorizationRequestActions"+".ERROR_CODE";
        /**
         * <h2>Key Access to error codes string.</h2>
         */
        public static final String ERROR_STRING="org.mcopenplatform.muoapi.ConstantsMCOP.AuthorizationRequestActions"+".ERROR_STRING";
    }


    /**
     *
     * <h1>Key Access to the values of all the configuration events of all the MCPTT services.</h1>
     *
     */
    public static class ConfigurationUpdateEventExtras{

        /**
         * <h2>Key Access to the organization that the user belongs to.</h2>
         * <p>This data will be received once logged into the system.</p>
         * <p>Response: String.</p>
         */
        public static final String ORGANIZATION="org.mcopenplatform.muoapi.ConstantsMCOP.LoginSuccessExtras"+".ORGANIZATION";

        /**
         * <h2>Key Access to default emergency contact identifier that must be called in case of an emergency call with no destination specified.</h2>
         * <p>This data will be received once logged in and each time the system changes the value or the user manually changes it with the method {@link org.mcopenplatform.muoapi.IMCOPsdk#changeSelectedContact}</p>
         * <p>Response: String with UserID format</p>
         */
        public static final String DEFAULT_EMERGERCY_CONTACT="org.mcopenplatform.muoapi.ConstantsMCOP.LoginSuccessExtras"+".DEFAULT_EMERGERCY_CONTACT";

        /**
         * <h2>Key Access to ParticipantType, indicating the position of the user in the organization. Not defined in the 3GPP.</h2>
         * <p>This data will be received once logged into the system or whenever the user receives a new configuration.</p>
         * <p>Response: String.</p>
         */
        public static final String PARTICIPANT_TYPE="org.mcopenplatform.muoapi.ConstantsMCOP.LoginSuccessExtras"+".PARTICIPANT_TYPE";

        /**
         * <h2>Key Access to the list of user granted permissions.</h2>
         * <p>This data will be received once logged into the system or whenever the user receives a new configuration.</p>
         * <p>Response: Integer with values of all the FLAGs that identify user allowed services</p>
         * <p>Values: Sum of {@link AllowTypeEnum}</p>
         * <p>  private_call (Private calls allowed) {@link AllowTypeEnum#private_call}</p>
         * <p>  emergency_group_call (Emergency group calls allowed) {@link AllowTypeEnum#emergency_group_call}</p>
         * <p>  emergency_private_call (Private emergency calls allowed){@link AllowTypeEnum#emergency_private_call}</p>
         * <p>  imminent_peril_call (Imminent peril calls allowed){@link AllowTypeEnum#imminent_peril_call}</p>
         * <p>  activate_emergency_alert (Alert calls allowed){@link AllowTypeEnum#activate_emergency_alert}</p>
         * <p>NOTE: The absence of permissions in this list indicates that it is not allowed to make such type of calls, and any attempt will give an error in {@link CallEventExtras#ERROR_CODE}.</p>
         */

        public static final String ALLOWS_LIST="org.mcopenplatform.muoapi.ConstantsMCOP.ConfigurationUpdateEventExtras"+".ALLOWS_LIST";


        /**
         * <h2>Access Key to the Private contacts available for the user</h2>
         * * <p>This data will be received once logged into the system or whenever the user receives a new configuration.</p>
         * <p>Response: Array of strings with Sips URI  of the available contacts</p>
         * <p>Values: List<String> </p>
         */

        public static final String PRIVATE_CONTACT_LIST="org.mcopenplatform.muoapi.ConstantsMCOP.ConfigurationUpdateEventExtras"+".PRIVATE_CONTACT_LIST";

        /**
         * <h2>Access Key to the display name of private contacts available for the user</h2>
         * * <p>This data will be received once logged into the system or whenever the user receives a new configuration.</p>
         * <p>Response: Array of strings with the display name of the available contacts</p>
         * <p>Values: List<String> </p>
         */

        public static final String PRIVATE_CONTACT_DISPLAY_NAME_LIST="org.mcopenplatform.muoapi.ConstantsMCOP.ConfigurationUpdateEventExtras"+".PRIVATE_CONTACT_DISPLAY_NAME_LIST";


        /**
         * <h2>Key Access to configuration error codes.</h2>
         * <p>Response: Integer indicating the error code.</p>
         * <p>Values:</p>
         *<p>Code	Explanatory text	Description</p>
         *<p>101	It has been impossible to obtain authentication data</p>
         * <p>0 means no errors</p>
         */
        public static final String ERROR_CODE="org.mcopenplatform.muoapi.ConstantsMCOP.ConfigurationUpdateEventExtras"+".ERROR_CODE";
        /**
         * <h2>Key Access to the string of error codes</h2>
         */
        public static final String ERROR_STRING="org.mcopenplatform.muoapi.ConstantsMCOP.ConfigurationUpdateEventExtras"+".ERROR_STRING";

        /**
         * <h2>Type of permissions that the user obtains when receiving the data from key {@link ConfigurationUpdateEventExtras#ALLOWS_LIST}</h2>
         */
        public enum AllowTypeEnum {
            none(0x00),
            private_call (0x01),
            emergency_group_call (0x01 << 1),
            emergency_private_call (0x01 << 2),
            imminent_peril_call (0x01 << 3),
            activate_emergency_alert (0x01 << 4);
            private int code;

            AllowTypeEnum(int code) {
                this.code = code;
            }


            public int getValue() {
                return code;
            }

            public static List<AllowTypeEnum> getListAllowType(int num){
                ArrayList<AllowTypeEnum> allowTypeEnums=new ArrayList<>();
                for (ConstantsMCOP.ConfigurationUpdateEventExtras.AllowTypeEnum allowTypeEnum : ConstantsMCOP.ConfigurationUpdateEventExtras.AllowTypeEnum.values())
                    if((num & allowTypeEnum.getValue())==allowTypeEnum.getValue())
                        allowTypeEnums.add(allowTypeEnum);

                return allowTypeEnums;
            }

            public static int getValue(List<AllowTypeEnum> allowTypeEnums){
                int value=none.getValue();
                for (ConstantsMCOP.ConfigurationUpdateEventExtras.AllowTypeEnum allowTypeEnum : allowTypeEnums)
                    if(allowTypeEnum!=null)
                        value+=allowTypeEnum.getValue();

                return value;
            }


        }

    }

    /**
     *
     * Key Access to the values of type {@link ConstantsMCOP.ActionsCallBack#callEvent} events.
     *
     */
    public static class CallEventExtras{

        /**
         * <h2>Key Access to event type:</h2>
         * <p>Integer type values</p>
         * <p>Possible event types:</p>
         * <p>     INCOMING    (Incoming call of any MCPTT type) {@link ConstantsMCOP.CallEventExtras.CallEventEventTypeEnum#INCOMING}</p>
         * <p>     RINGING     (Destination of the call is ringing) {@link ConstantsMCOP.CallEventExtras.CallEventEventTypeEnum#RINGING}</p>
         * <p>     INPROGRESS  (MCPTT call in progress) {@link ConstantsMCOP.CallEventExtras.CallEventEventTypeEnum#INPROGRESS}</p>
         * <p>     CONNECTED   (Established call) {@link ConstantsMCOP.CallEventExtras.CallEventEventTypeEnum#CONNECTED}</p>
         * <p>     ERROR       (Any error in MCPTT calls) {@link ConstantsMCOP.CallEventExtras.CallEventEventTypeEnum#ERROR}</p>
         * <p>     UPDATE       (Any Change in Call) {@link ConstantsMCOP.CallEventExtras.CallEventEventTypeEnum#UPDATE}</p>
         */
        public static final String EVENT_TYPE="org.mcopenplatform.muoapi.ConstantsMCOP.CallEventExtras"+".EVENT_TYPE";


        /**
         *  <h2>Key Access to MCPTT call session ID. This identifier is unique for each call.</h2>
         *  <p>Present in all types of events {@link ConstantsMCOP.CallEventExtras.CallEventEventTypeEnum}</p>
         */
        public static final String SESSION_ID="org.mcopenplatform.muoapi.ConstantsMCOP.CallEventExtras"+".SESSION_ID";

        /**
         *  <h2>Key Access to MCPTT call type.</h2>
         *  <p>Present in event type {@link ConstantsMCOP.CallEventExtras.CallEventEventTypeEnum#INCOMING} and {@link ConstantsMCOP.CallEventExtras.CallEventEventTypeEnum#UPDATE}</p>
         */
        public static final String CALL_TYPE="org.mcopenplatform.muoapi.ConstantsMCOP.CallEventExtras"+".CALL_TYPE";


        /**
         *  Key Access to the caller UserID
         *  Present in event type {@link ConstantsMCOP.CallEventExtras.CallEventEventTypeEnum#INCOMING}
         */
        public static final String CALLER_USERID="org.mcopenplatform.muoapi.ConstantsMCOP.CallEventExtras"+".CALLER_USERID";


        /**
         *  Key Access to the caller GroupID
         *  Present in event type {@link ConstantsMCOP.CallEventExtras.CallEventEventTypeEnum#INCOMING}
         */
        public static final String CALLER_GROUPID="org.mcopenplatform.muoapi.ConstantsMCOP.CallEventExtras"+".CALLER_GROUPID";


        /**
         *<p> Key Access to Error Codes</p>
         *<p> All error codes are integers:</p>
         *<p>Code	Explanatory text	Description</p>
         *<p>103	maximum simultaneous MCPTT group calls reached	The number of maximum simultaneous MCPTT group calls supported for the MCPTT user has been exceeded.</p>
         *<p>104	isfocus not assigned	A controlling MCPTT function has not been assigned to the MCPTT session.</p>
         *<p>105	subscription not allowed in a broadcast group call	Subscription to the conference event package rejected during a group call initiated as a broadcast group call.</p>
         *<p>106	user not authorised to join chat group	The MCPTT user is not authorised to join this chat group.</p>
         *<p>107	user not authorised to make private calls	The MCPTT user is not authorised to make private calls.</p>
         *<p>108	user not authorised to make chat group calls	The MCPTT user is not authorised to make chat group calls.</p>
         *<p>109	user not authorised to make prearranged group calls	The MCPTT user is not authorised to make group calls to a prearranged group.</p>
         *<p>110	user declined the call invitation	The MCPTT user declined to accept the call.</p>
         *<p>111	group call proceeded without all required group members	The required members of the group did not respond within the acknowledged call time, but the call still went ahead.</p>
         *<p>112	group call abandoned due to required group members not part of the group session	The group call was abandoned, as the required members of the group did not respond within the acknowledged call time.</p>
         *<p>116	user is not part of the MCPTT group	The group exists on the group management server but the requesting user is not part of this group.</p>
         *<p>117	the group identity indicated in the request is a prearranged group	The group id that is indicated in the request is for a prearranged group, but did not match the request from the MCPTT user.</p>
         *<p>118	the group identity indicated in the request is a chat group	The group id that is indicated in the request is for a chat group, but did not match the request from the MCPTT user.</p>
         *<p>119	user is not authorised to initiate the group call	The MCPTT user identified by the MCPTT ID is not authorised to initiate the group call.</p>
         *<p>120	user is not affiliated to this group	The MCPTT user is not affiliated to the group.</p>
         *<p>121	user is not authorised to join the group call	The MCPTT user identified by the MCPTT ID is not authorised to join the group call.</p>
         *<p>122	too many participants	The group call has reached its maximum number of participants.</p>
         *<p>123(INFO)	MCPTT session already exists	Inform the MCPTT user that the group call is currently ongoing.</p>
         *<p>124	maximum number of private calls reached	The maximum number of private calls allowed at the MCPTT server for the MCPTT user has been reached.</p>
         *<p>127	user not authorised to be called in private call	The called MCPTT user is not allowed to be part of a private call.</p>
         *<p>144	user not authorised to call this particular user	The calling user is not authorised to call this particular called user.</p>
         *<p>145	unable to determine called party	The participating function was unable to determine the called party from the information received in the SIP request.</p>
         *<p>147(INFO)	user is authorized to initiate a temporary group call	The non-controlling MCPTT function has authorized a request from the controlling MCPTT function to authorize a user to initiate an temporary group session.</p>
         *<p>148(INFO)	MCPTT group is regrouped	The MCPTT group hosted by a non-controlling MCPTT function is part of a temporary group session as the result of the group regroup function.</p>
         *<p>151	user not authorised to make a private call call-back request	The MCPTT user is not authorised to make a private call call-back request.</p>
         *<p>152	user not authorised to make a private call call-back cancel request	The MCPTT user is not authorised to make a private call call-back cancel request.</p>
         *<p>153	user not authorised to call any of the users requested in the first-to-answer call	All users that were invited in the first-to-answer call cannot be involved in a private call with the inviting user.</p>
         *<p>154	user not authorised to make ambient listening call	The MCPTT user is not authorised to make an ambient listening call.</p>
         *<p>156	user not authorised to originate a first-to-answer call	The MCPTT user is not authorised to make a first-to-answer call.</p>
         *
         *<p>401   Invalid call type.</p>
         *<p>402   Invalid action.</p>
         *<p>403   in MakeCall the UserID is not valid. the UserID is empty, or don´t have URI format</p>
         *<p>404   The call can not be hung because is not made</p>
         *<p>405   The call cannot be accepted because it has not received any call for that sessionID</p>
         *<p>406   The sessionID could not be created</p>
         *<p>407   The call cannot be made because this unregistered</p>
         *<p>408   User destination is not available at the moment</p>
         *<p>409   Undefined signal error</p>
         *<p>410   The destination is busy</p>
         */
        public static final String ERROR_CODE="org.mcopenplatform.muoapi.ConstantsMCOP.CallEventExtras"+".ERROR_CODE";
        /**
         * Key Access to Error Codes string.
         */
        public static final String ERROR_STRING="org.mcopenplatform.muoapi.ConstantsMCOP.CallEventExtras"+".ERROR_STRING";


        /**
         * Types of CallEvent events.
         */
        public enum CallEventEventTypeEnum{
            NONE (0x00),
            INCOMING(0x01),
            RINGING(0x02),
            INPROGRESS(0x03),
            CONNECTED(0x04),
            TERMINATED(0x05),
            ERROR(0x06),
            UPDATE(0x07);

            private int code;
            CallEventEventTypeEnum(int code) {
                this.code = code;
            }
            public int getValue() {
                return code;
            }

            private static Map map = new HashMap<>();


            static {
                for (CallEventEventTypeEnum pageType : CallEventEventTypeEnum.values()) {
                    map.put(pageType.code, pageType);
                }
            }

            public static CallEventEventTypeEnum fromInt(int pageType) {
                return (CallEventEventTypeEnum) map.get(pageType);
            }

        }
        /**
         * <h2>Individual call types than can be combined and used in {@link org.mcopenplatform.muoapi.IMCOPsdk#makeCall(String, int)} or received in type {@link ConstantsMCOP.CallEventExtras.CallEventEventTypeEnum#INCOMING} events</h2>
         */
        public enum CallTypeEnum {

            None(0x00),
            Audio (0x01),
            Video (0x01 << 1),
            Data (0x01 << 2),
            WithFloorCtrl (0x01 << 3),
            WithoutFloorCtrl (0x01 << 4),
            Private (0x01 << 5),
            Broadcast (0x01 << 6),
            PrearrangedGroup (0x01 << 7),
            ChatGroup (0x01 << 8),
            FirstToAnswer (0x01 << 9), //REL 14
            PrivateCallCallback (0x01 << 10), //REL 14
            RemoteAmbientListening (0x01 << 11), //REL 14
            LocalAmbientListening (0x01 << 12), //REL 14
            Emergency (0x01 << 13),
            ImminentPeril (0x01 << 14),
            EmergencyAlert (0x01 << 15);

            private int code;

            CallTypeEnum(int code) {
                this.code = code;
            }


            public int getValue() {
                return code;
            }

            public static List<CallTypeEnum> getListCallType(int num){
                ArrayList<CallTypeEnum> callTypesEnum=new ArrayList<>();
                for (CallTypeEnum callTypeEnum : CallTypeEnum.values())
                    if((num & callTypeEnum.getValue())==callTypeEnum.getValue())
                        callTypesEnum.add(callTypeEnum);

                return callTypesEnum;
            }

            public static Map<CallTypeEnum,Integer> getMapCallType(int num){
                Map<CallTypeEnum,Integer> callTypesEnum=new HashMap<>();
                for (CallTypeEnum callTypeEnum : CallTypeEnum.values())
                    if((num & callTypeEnum.getValue())==callTypeEnum.getValue())
                        callTypesEnum.put(callTypeEnum,callTypeEnum.getValue());
                return callTypesEnum;
            }

            public static int getValue(List<CallTypeEnum> callTypeEnums){
                int value=None.getValue();
                for (CallTypeEnum callTypeEnum : callTypeEnums)
                    if(callTypeEnum!=null)
                        value+=callTypeEnum.getValue();

                return value;
            }


        }
    }



    //Pending more definitions
    public static class ConfigurationUpdateExtras{
        //GROUPS_LIST-> returns list String (URI String)
        public static final String GROUPS_LIST="org.mcopenplatform.muoapi.ConstantsMCOP.ConfigurationUpdateExtras"+".SUCCESS";
        //ERROR_CODE-> returns int!=0 if the configurationUpdate fails. 0 if Successful
        public static final String ERROR_CODE="org.mcopenplatform.muoapi.ConstantsMCOP.ConfigurationUpdateExtras"+".ERROR_CODE";
        //ERROR_STRING-> returns String (define the error) if the configurationUpdate fails.
        public static final String ERROR_STRING="org.mcopenplatform.muoapi.ConstantsMCOP.ConfigurationUpdateExtras"+".ERROR_STRING";
    }


    /**
     *
     * <h1>Key Access to the values of the floor control events in any MCPTT session.</h1>
     *
     */
    public static class FloorControlEventExtras{

        /**
         * <h2>Key Access to the list of floor control events.</h2>
         * <p>This event is received every time an action is generated on any floor control of any MCPTT session.</p>
         * <p>Response: String</p>
         * <p>Values:
         * <p>     granted      (Session control granted) {@link ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum#granted}</p>
         * <p>     idle         (Nobody has control of the session) {@link ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum#idle}</p>
         * <p>     taken         (Other session participant has control) {@link ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum#taken}</p>
         * <p>     request_sent  (Session control request sent) {@link ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum#request_sent}</p>
         * <p>     release_sent   (Session control release sent) {@link ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum#idle}</p>
         * <p>     denied         (Session control denied) {@link ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum#denied}</p>
         * <p>     revoked         (Session control revoked) {@link ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum#revoked}</p>
         * <p>     queued         (Session control request queued) {@link ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum#queued}</p>
         * <p>     queued_timeout         (Queued request timed out) {@link ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum#queued_timeout}</p>
         * <p>     transmission_granted         (Transmission granted) {@link ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum#transmission_granted}</p>
         * <p>     reception_granted         (Reception granted) {@link ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum#reception_granted}</p>
         * <p>     transmission_rejection         (Transmission rejected) {@link ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum#transmission_rejection}</p>
         * <p>     reception_rejection         (Reception rejected) {@link ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum#reception_rejection}</p>
         * <p>     transmission_revoke         (Transmission revoked) {@link ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum#transmission_revoke}</p>
         * <p>     reception_revoke         (Reception revoked) {@link ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum#reception_revoke}</p>
         * <p>     transmission_notification         (Transmission notification) {@link ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum#transmission_notification}</p>
         * <p>     transmission_end_notification         (Transmission notification end) {@link ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum#transmission_end_notification}</p>
         * <p>     transmission_end_response         (Transmission end response) {@link ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum#transmission_end_response}</p>
         * <p>     reception_end_response         (Reception end response) {@link ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum#reception_end_response}</p>
         */
        public static final String FLOOR_CONTROL_EVENT="org.mcopenplatform.muoapi.ConstantsMCOP.FloorControlEventExtras"+".FLOOR_CONTROL_EVENT";


        /**
         * <h2>Key Access to DISPLAY NAME of the client linked to one of the events in {@link ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum}</h2>
         * <p>It is usually used with the event {@link ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum#taken} to know who is talking at each moment.</p>
         * <p>Response: String that identifies the user with a human-readable alias, more pleasant than a MCPTT identifier.</p>
         */
        public static final String DISPLAY_NAME="org.mcopenplatform.muoapi.ConstantsMCOP.FloorControlEventExtras"+".DISPLAY_NAME";

        /**
         * <h2>Key Access to DURATION TOKEN of Number of seconds that a client can speak {@link ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum}</h2>
         * <p>It is usually used with the event {@link ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum#granted} to know who is talking at each moment.</p>
         * <p>Response: Number of seconds that a client can speak.</p>
         */
        public static final String DURATION_TOKEN="org.mcopenplatform.muoapi.ConstantsMCOP.FloorControlEventExtras"+".DURATION_TOKEN";

        /**
         * <h2>Key Access to User ID that univocally distinguishes the MCPTT user causing the event {@link ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum}</h2>
         * <p>Usually used with events:</p>
         * <p>      {@link ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum#taken} to know who is talking at each moment.</p>
         * <p>      {@link ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum#reception_granted}</p>
         * <p>      {@link ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum#transmission_end_response}</p>
         * <p>      {@link ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum#reception_end_response}</p>
         * <p>Response: String with URI format that identifies the MCPTT user.</p>
         */
        public static final String USER_ID="org.mcopenplatform.muoapi.ConstantsMCOP.FloorControlEventExtras"+".USER_ID";




        /**
         * <h2>Key Access to ALLOW REQUEST that indicates whether a call can be made to {@link org.mcopenplatform.muoapi.IMCOPsdk#floorControlOperation(String, int, String)} to request the token despite being in a {@link ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum#taken} state</h2>
         * <p>This data will be obtained every time a {@link ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum#taken} event is received.</p>
         * <p>Response: boolean</p>
         */
        public static final String ALLOW_REQUEST="org.mcopenplatform.muoapi.ConstantsMCOP.FloorControlEventExtras"+".ALLOW_REQUEST";

        /**
         *  Key Access to MCPTT calls Session ID. This identifier is unique for each call.
         *  It occurs in all types of {@link ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum} events.
         */
        public static final String SESSION_ID="org.mcopenplatform.muoapi.ConstantsMCOP.FloorControlEventExtras"+".SESSION_ID";


        /**
         * <h2>Key Access to the numbering of a specific MCPTT session control denial or revocation event.</h2>
         * <p>Response: Integer indicating the code of the cause.</p>
         * <p>Values:</p>
         * <p>  Event  {@link ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum#denied}:</p>
         * <p> 101     Cause #1 - Another MCPTT client has permission The <Reject cause> value set to '1' indicates that another MCPTT user has permission to send a media.</p>
         * <p> 102     Cause #2 - Internal floor control server error The <Reject cause> value set to '2' indicates that the floor control server cannot grant the floor request due to an internal error.</p>
         * <p> 103     Cause #3 - Only one participant The <Reject cause> value set to '3' indicates that the floor control server cannot grant the floor request, because the requesting party is the only participant in the MCPTT session.</p>
         * <p> 104     Cause #4 - Retry-after timer has not expired The <Reject cause> value set to '4' indicates that the floor control server cannot grant the floor request, because timer T9 (Retry-after) has not expired after permission to send media has been revoked.</p>
         * <p> 105     Cause #5 - Receive only The <Reject cause> value set to '5' indicates that the floor control server cannot grant the floor request, because the requesting party only has receive privilege.</p>
         * <p> 106     Cause #6 - No resources available The <Reject cause> value set to '6' indicates that the floor control server cannot grant the floor request due to congestion.</p>
         * <p> 107     Cause #7 – Queue full The <Reject cause> value set to 7 indicates that the floor control server cannot queue the floor request, because the queue is full.</p>
         * <p> 108     Cause #255 - Other reason The <Reject cause> value set to '255' indicates that the floor control server does not grant the floor request due to the floor control server local policy.</p>
         * <p>  Event  {@link ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum#revoked}:</p>
         * <p> 201     Cause #1 – Only one MCPTT client The <Reject Cause> value set to '1' indicates that the MCPTT client is the only MCPTT client in the MCPTT session or the only participant connected to a floor control server. No additional information included.</p>
         * <p> 202     Cause #2 – Media burst too long The <Reject Cause> value set to '2' indicates that the MCPTT User has talked too long (e.g., the stop-talking timer has expired). No additional information included.</p>
         * <p> 203     Cause #3 - No permission to send a Media Burst The <Reject Cause> value set to '3' indicates that the MCPTT client does not have permission to send media. No additional information is included.</p>
         * <p> 204     Cause #4 - Media Burst pre-empted The <Reject Cause> value set to '4' indicates that the MCPTT client 's permission to send a media is being pre-empted. No additional information is included.</p>
         * <p> 205     Cause #6 - No resources available The <Reject Cause> value set to '6' indicates that the floor control server can no longer grant MCPTT client to send media due to congestion. No additional information is included.</p>
         * <p> 206     Cause #255 – Other reason The <Reject Cause> value set to '255' indicates that the floor control server can no longer grant MCPTT client to send media due to the floor control server local policy. No additional information is included.</p>
         * <p>  Event  {@link ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum#transmission_rejection}:</p>
         * <p> 301     Cause #1 - Transmission limit reached The <Reject cause> value set to '1' indicates that the number of transmitters have reached maximum.</p>
         * <p> 302     Cause #2 - Internal transmission control server error The <Reject cause> value set to '2' indicates that the transmission control server cannot grant the transmission request due to an internal error.</p>
         * <p> 303     Cause #3 - Only one participant The <Reject cause> value set to '3' indicates that the transmission control server cannot grant the transmission request, because the requesting party is the only participant in the MCVideo session.</p>
         * <p> 304     Cause #4 - Retry-after timer has not expired The <Reject cause> value set to '4' indicates that the transmission control server cannot grant the transmission request, because timer T9 (Retry-after) has not expired after permission to send media has been revoked.</p>
         * <p> 305     Cause #5 - Receive only The <Reject cause> value set to '5' indicates that the transmission control server cannot grant the transmission request, because the requesting party only has receive privilege.</p>
         * <p> 306     Cause #6 - No resources available The <Reject cause> value set to '6' indicates that the transmission control server cannot grant the transmission request due to congestion.</p>
         * <p> 307     Cause #255 - Other reason The <Reject cause> value set to '255' indicates that the transmission control server does not grant the transmission request due to the transmission control server local policy.</p>
         * <p>  Event  {@link ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum#transmission_revoke}:</p>
         * <p> 401     Cause #1 – Only one MCVideo client The <Reject Cause> value set to '1' indicates that the MCVideo client is the only MCVideo client in the MCVideo session or the only participant connected to a transmission control server. No additional information included.</p>
         * <p> 402     Cause #2 – Media burst too long The <Reject Cause> value set to '2' indicates that the MCVideo User has transmitted too long (e.g., the stop-transmission timer has expired). No additional information included.</p>
         * <p> 403     Cause #3 - No permission to send a Media Burst The <Reject Cause> value set to '3' indicates that the MCVideo client does not have permission to send media. No additional information is included.</p>
         * <p> 404     Cause #4 - Media Burst pre-empted The <Reject Cause> value set to '4' indicates that the MCVideo client's permission to send a media is being pre-empted. No additional information is included.</p>
         * <p> 405     Cause #6 - No resources available The <Reject Cause> value set to '6' indicates that the transmission control server can no longer grant MCVideo client to send media due to congestion. No additional information is included.</p>
         * <p> 406     Cause #255 – Other reason The <Reject Cause> value set to '255' indicates that the transmission control server can no longer grant MCVideo client to send media due to the transmission control server local policy. No additional information is included.</p>
         * <p>  Event  {@link ConstantsMCOP.FloorControlEventExtras.FloorControlEventTypeEnum#reception_rejection}:</p>
         * <p> 501     Cause #2 - Internal transmission control server error The <Reject cause> value set to '2' indicates that the transmission control server cannot grant the receive media request due to an internal error.</p>
         * <p> 502     Cause #4 - Retry-after timer has not expired The <Reject cause> value set to '4' indicates that the transmission control server cannot grant the receive media request, because timer T9 (Retry-after) has not expired after permission to send media has been revoked.</p>
         * <p> 503     Cause #5 - Send only The <Reject cause> value set to '5' indicates that the transmission control server cannot grant the receive media request, because the requesting party only has send privilege.</p>
         * <p> 504     Cause #6 - No resources available The <Reject cause> value set to '6' indicates that the transmission control server cannot grant the receive media request due to congestion.</p>
         * <p> 505     Cause #255 - Other reason The <Reject cause> value set to '255' indicates that the transmission control server does not grant the receive media request due to the transmission control server local policy.</p>
         */
        public static final String CAUSE_CODE="org.mcopenplatform.muoapi.ConstantsMCOP.FloorControlEventExtras"+".CAUSE_CODE";
        /**
         * <h2>Key Access to the string of the cause codes.</h2>
         */
        public static final String CAUSE_STRING="org.mcopenplatform.muoapi.ConstantsMCOP.FloorControlEventExtras"+".CAUSE_STRING";
        /**
         *<p> Key Access to Error Codes</p>
         *<p> All error codes are integers:</p>
         *<p>Code	Explanatory text	Description</p>
         *<p>103	maximum simultaneous MCPTT group calls reached	The number of maximum simultaneous MCPTT group calls supported for the MCPTT user has been exceeded.</p>
         *<p>104	isfocus not assigned	A controlling MCPTT function has not been assigned to the MCPTT session.</p>


         /**
         * <h2>Key Access to the floor control error codes.</h2>
         * <p>Response: Integer indicating the error code.</p>
         * <p>Values:</p>
         * <p>0 means no error.</p>
         * <p>Code	Description</p>
         * <p>101	The session cannot be accepted because it has not received any session for that sessionID</p>
         * <p>102	Floor control invalid operation</p>
         */
        public static final String ERROR_CODE="org.mcopenplatform.muoapi.ConstantsMCOP.FloorControlEventExtras"+".ERROR_CODE";
        /**
         * <h2>Key Access to the Error codes string.</h2>
         */
        public static final String ERROR_STRING="org.mcopenplatform.muoapi.ConstantsMCOP.FloorControlEventExtras"+".ERROR_STRING";


        /**
         * <h2>Operation types performed in method {@link org.mcopenplatform.muoapi.IMCOPsdk#floorControlOperation(String, int, String)} for MCPTT session control management.</h2>
         */
        public enum FloorControlOperationTypeEnum{
            none(0x00),
            MCPTT_Request(0x01),
            MCPTT_Release(0x02),
            TRANSMISION_Request(0x03),
            TRANSMISION_End_Request(0x04),
            RECEPTION_Request(0x05),
            RECEPTION_End_Request(0x06);
            private int code;
            FloorControlOperationTypeEnum(int code) {
                this.code = code;
            }
            public int getValue() {
                return code;
            }

            private static Map map = new HashMap<>();


            static {
                for (FloorControlOperationTypeEnum pageType : FloorControlOperationTypeEnum.values()) {
                    map.put(pageType.code, pageType);
                }
            }

            public static FloorControlOperationTypeEnum fromInt(int pageType) {
                return (FloorControlOperationTypeEnum) map.get(pageType);
            }
        }

        /**
         * <h2>Types of event referred to the floor control of an MCPTT session and received on key {@link ConstantsMCOP.FloorControlEventExtras#FLOOR_CONTROL_EVENT} events.</h2>
         */
        public enum FloorControlEventTypeEnum{
            none (""),
            granted("org.mcopenplatform.muoapi.ConstantsMCOP.FloorControlEventExtras.FLOOR_CONTROL_EVENT"+".granted"),
            idle("org.mcopenplatform.muoapi.ConstantsMCOP.FloorControlEventExtras.FLOOR_CONTROL_EVENT"+".idle"),
            taken("org.mcopenplatform.muoapi.ConstantsMCOP.FloorControlEventExtras.FLOOR_CONTROL_EVENT"+".taken"),
            request_sent("org.mcopenplatform.muoapi.ConstantsMCOP.FloorControlEventExtras.FLOOR_CONTROL_EVENT"+".request_sent"),
            release_sent("org.mcopenplatform.muoapi.ConstantsMCOP.FloorControlEventExtras.FLOOR_CONTROL_EVENT"+".release_sent"),
            denied("org.mcopenplatform.muoapi.ConstantsMCOP.FloorControlEventExtras.FLOOR_CONTROL_EVENT"+".denied"),
            revoked("org.mcopenplatform.muoapi.ConstantsMCOP.FloorControlEventExtras.FLOOR_CONTROL_EVENT"+".revoked"),
            queued("org.mcopenplatform.muoapi.ConstantsMCOP.FloorControlEventExtras.FLOOR_CONTROL_EVENT"+".queued"),
            queued_timeout("org.mcopenplatform.muoapi.ConstantsMCOP.FloorControlEventExtras.FLOOR_CONTROL_EVENT"+".queued_timeout"),
            transmission_granted("org.mcopenplatform.muoapi.ConstantsMCOP.FloorControlEventExtras.FLOOR_CONTROL_EVENT"+".transmission_granted"),
            reception_granted("org.mcopenplatform.muoapi.ConstantsMCOP.FloorControlEventExtras.FLOOR_CONTROL_EVENT"+".reception_granted"),
            transmission_rejection("org.mcopenplatform.muoapi.ConstantsMCOP.FloorControlEventExtras.FLOOR_CONTROL_EVENT"+".transmission_rejection"),
            reception_rejection("org.mcopenplatform.muoapi.ConstantsMCOP.FloorControlEventExtras.FLOOR_CONTROL_EVENT"+".reception_rejection"),
            transmission_revoke("org.mcopenplatform.muoapi.ConstantsMCOP.FloorControlEventExtras.FLOOR_CONTROL_EVENT"+".transmission_revoke"),
            reception_revoke("org.mcopenplatform.muoapi.ConstantsMCOP.FloorControlEventExtras.FLOOR_CONTROL_EVENT"+".reception_revoke"),
            transmission_notification("org.mcopenplatform.muoapi.ConstantsMCOP.FloorControlEventExtras.FLOOR_CONTROL_EVENT"+".transmission_notification"),
            transmission_end_notification("org.mcopenplatform.muoapi.ConstantsMCOP.FloorControlEventExtras.FLOOR_CONTROL_EVENT"+".transmission_end_notification"),
            transmission_end_response("org.mcopenplatform.muoapi.ConstantsMCOP.FloorControlEventExtras.FLOOR_CONTROL_EVENT"+".transmission_end_response"),
            reception_end_response("org.mcopenplatform.muoapi.ConstantsMCOP.FloorControlEventExtras.FLOOR_CONTROL_EVENT"+".reception_end_response");

            private final String text;

            /**
             * @param text
             */
            FloorControlEventTypeEnum(final String text) {
                this.text = text;
            }

            /* (non-Javadoc)
             * @see java.lang.Enum#toString()
             */
            @Override
            public String toString() {
                return text;
            }

            public static FloorControlEventTypeEnum fromString(String text) {
                for (FloorControlEventTypeEnum value : FloorControlEventTypeEnum.values()) {
                    if (value.text.equalsIgnoreCase(text)) {
                        return value;
                    }
                }
                return null;
            }

        }


    }

    /**
     *
     * <h1>Key Access to event {@link ActionsCallBack#groupInfoEvent} values.</h1>
     * <h2>For each group the user belongs to, an event with the different data that defines the group will be sent.</h2>
     *
     */
    public static class GroupInfoEventExtras{



        /**
         * <h2>Key Access to the Group ID that allows to univocally distinguish the MCPTT group causing the event.</h2>
         * <p>Response: String with URI format that identifies the MCPTT group.</p>
         */
        public static final String GROUP_ID="org.mcopenplatform.muoapi.ConstantsMCOP.GroupInfoEventExtras"+".GROUP_ID";


        /**
         * <h2>Key Access to the Display Name that allows to distinguish the MCPTT group causing the event.</h2>
         * <p>Response: String that identifies the MCPTT group in a human-readable format.</p>
         */
        public static final String DISPLAY_NAME="org.mcopenplatform.muoapi.ConstantsMCOP.GroupInfoEventExtras"+".DISPLAY_NAME";

        /**
         * <h2>Key Access to the permissions of the MCPTT group.</h2>
         * <p>Response: Integer containing the different FLAGs of the user's permissions on the group.</p>
         * <p>Values:</p>
         * <p>0 means no permission.</p>
         * <p>      invite_members                          ("exist" represents the pre-arranged group in on-network MCPTT procedures; "not exist" represents the chat group in on-network MCPTT procedures. This value is used when the element is not present.)   {@link AllowTypeEnum#invite_members}</p>
         * <p>      recvonly                                (Receiving calls allowed, but not making them)   {@link AllowTypeEnum#recvonly}</p>
         * <p>      emergency_call                          (Emergency group calls allowed)   {@link AllowTypeEnum#emergency_call}</p>
         * <p>      imminent_peril_call                     (Imminent peril group calls allowed)   {@link AllowTypeEnum#imminent_peril_call}</p>
         * <p>      emergency_alert_call                    (Emergency alert group calls allowed)   {@link AllowTypeEnum#emergency_alert_call}</p>
         * <p>      video_invite_members                    ("exist" represents the pre-arranged group in on-network MCVideo procedures; "not exist" represents the chat group in on-network MCVideo procedures. This value is used when the element is not present)   {@link AllowTypeEnum#video_invite_members}</p>
         * <p>      non_real_time_video_mode                ("exist" indicates that non real-time video mode is allowed for the MCVideo group. "not exist" indicates that non real-time video mode is not allowed for the MCVideo group. This value is used when the element is not present)   {@link AllowTypeEnum#non_real_time_video_mode}</p>
         * <p>      non_urgent_real_time_video_mode         ("exist" indicates that non urgent real-time video mode is allowed for the MCVideo group. "not exist" indicates that non urgent real-time video mode is not allowed for the MCVideo group. This value is used when the element is not present)   {@link AllowTypeEnum#non_urgent_real_time_video_mode}</p>
         * <p>      urgent_real_time_video_mode             ("exist" indicates that urgent real-time video mode is allowed for the MCVideo group. "not exist" indicates that urgent real-time video mode is not allowed for the MCVideo group. This value is used when the element is not present;)   {@link AllowTypeEnum#urgent_real_time_video_mode}</p>
         * <p>      short_data_service                      (Short data allowed)   {@link AllowTypeEnum#short_data_service}</p>
         * <p>      file_distribution                       (Distributed file sharing allowed)   {@link AllowTypeEnum#file_distribution}</p>
         * <p>      conversation_management                 ("exist" indicates that conversation management is enabled for the MCData group. This value is used when the element is not present; "not exist" indicates that conversation management is disabled for the MCData group.)   {@link AllowTypeEnum#conversation_management}</p>
         * <p>      tx_control                              ("exist" indicates that transmission control is enabled for the MCData group. This value is used when the element is not present; "not exist" indicates that transmission control is disabled for the MCData group.)   {@link AllowTypeEnum#tx_control}</p>
         * <p>      rx_control                              ("exist" indicates that reception control is enabled for the MCData group. This value is used when the element is not present; "not exist" indicates that reception control is disabled for the MCData group.)   {@link AllowTypeEnum#rx_control}</p>
         * <p>      enhanced_status                         ("exist" indicates that enhanced status is enabled for the MCData group. This value is used when the element is not present; "not exist" indicates that enhanced status is disabled for the MCData group.)   {@link AllowTypeEnum#enhanced_status}</p>
         */
        public static final String ALLOWS_GROUP="org.mcopenplatform.muoapi.ConstantsMCOP.GroupInfoEventExtras"+".ALLOWS_GROUP";


        /**
         * <h2>Key Access to maximum allowed size for SDS data transmission.</h2>
         * <p>Indicates the maximum size of data (in bytes) that the originating MCData client is allowed to send to the MCData server for on-network SDS communications.</p>
         * <p>Response: Integer (in bytes)</p>
         */
        public static final String MAX_DATA_SIZE_FOR_SDS="org.mcopenplatform.muoapi.ConstantsMCOP.GroupInfoEventExtras"+".MAX_DATA_SIZE_FOR_SDS";

        /**
         * <h2>Key Access to maximum allowed file size for FD data transmission.</h2>
         * <p>Indicates the maximum size of data (in bytes) that the originating MCData client is allowed to send to the MCData server for on-network FD communications.</p>
         * <p>Response: Integer (in bytes)</p>
         */
        public static final String MAX_DATA_SIZE_FOR_FD="org.mcopenplatform.muoapi.ConstantsMCOP.GroupInfoEventExtras"+".MAX_DATA_SIZE_FOR_FD";


        /**
         * <h2>Key Access to maximum allowed file size for FD data transmission using HTTP.</h2>
         * <p>Indicates the maximum size of data (in bytes) which the MCData server always requests the terminating MCData client to automatically download for on-network FD communications using HTTP.</p>
         * <p>Response: Integer (in bytes)</p>
         */
        public static final String MAX_DATA_SIZE_AUTO_RECV="org.mcopenplatform.muoapi.ConstantsMCOP.GroupInfoEventExtras"+".MAX_DATA_SIZE_AUTO_RECV";

        /**
         * <h2>Key Access to real-time video mode.</h2>
         * <p>Response: String</p>
         * <p>Values {@link ActionRealTimeVideoType}:</p>
         * <p>      urgent_real_time                                ()   {@link ActionRealTimeVideoType#urgent_real_time}</p>
         * <p>      non_urgent_real_time                            ()   {@link ActionRealTimeVideoType#non_urgent_real_time}</p>
         * <p>      non_real_time                                   ()   {@link ActionRealTimeVideoType#non_real_time}</p>
         *
         */
        public static final String ACTIVE_REAL_TIME_VIDEO_MODE="org.mcopenplatform.muoapi.ConstantsMCOP.GroupInfoEventExtras"+".ACTIVE_REAL_TIME_VIDEO_MODE";


        /**
         * <h2>Types of real-time video modes.</h2>
         */
        public enum ActionRealTimeVideoType{
            none (""),
            urgent_real_time("urgent-real-time"),
            non_urgent_real_time("non-urgent-real-time"),
            non_real_time("non-real-time");


            private final String text;

            /**
             * @param text
             */
            ActionRealTimeVideoType(final String text) {
                this.text = text;
            }

            /* (non-Javadoc)
             * @see java.lang.Enum#toString()
             */
            @Override
            public String toString() {
                return text;
            }
        }

        /**
         * <h2>Key Access to the group participants data</h2>
         * <p>      UserID unequivocally identifies the user.</p>
         */
        public static final String PARTICIPANTS_LIST="org.mcopenplatform.muoapi.ConstantsMCOP.GroupInfoEventExtras"+".PARTICIPANTS_LIST";


        /**
         * <h2>Key Access to the group participants data.</h2>
         * <p>Response: Array with 3 Strings:.</p>
         * <p>      DisplayName identifies the user.</p>
         */
        public static final String PARTICIPANTS_LIST_DISPLAY_NAME="org.mcopenplatform.muoapi.ConstantsMCOP.GroupInfoEventExtras"+".PARTICIPANTS_LIST_DISPLAY_NAME";

        /**
         * <h2>Key Access to the group participants data.</h2>
         * <p>      ParticipantType indicates the user position in the organization. Not defined by 3GPP.</p>
         */
        public static final String PARTICIPANTS_LIST_TYPE="org.mcopenplatform.muoapi.ConstantsMCOP.GroupInfoEventExtras"+".PARTICIPANTS_LIST_TYPE";



        /**
         * <h2>Key Access to group info error codes.</h2>
         * <p>Response: Integer indicating error codes.</p>
         * <p>Values:</p>
         * <p>0 means no errors.</p>
         * <p> 101   Received data groups not valid</p>
         */
        public static final String ERROR_CODE="org.mcopenplatform.muoapi.ConstantsMCOP.GroupInfoEventExtras"+".ERROR_CODE";
        /**
         * <h2>Key Access to the Error codes string.</h2>
         */
        public static final String ERROR_STRING="org.mcopenplatform.muoapi.ConstantsMCOP.GroupInfoEventExtras"+".ERROR_STRING";


        /**
         * <h2>Type of permission in a group.</h2>
         *
         */
        public enum AllowTypeEnum{
            none(0x00),
            //MCPTT
            invite_members(0x01),
            recvonly(0x01 << 1),
            emergency_call(0x01 << 2),
            imminent_peril_call(0x01 << 3),
            emergency_alert_call(0x01 << 4),
            //MCVIDEO
            video_invite_members(0x01 << 5),
            non_real_time_video_mode(0x01 << 6),
            non_urgent_real_time_video_mode(0x01 << 7),
            urgent_real_time_video_mode(0x01 << 8),
            //MCDATA
            short_data_service(0x01 << 11),
            file_distribution(0x01 << 12),
            conversation_management(0x01 << 13),
            tx_control(0x01 << 14),
            rx_control(0x01 << 15),
            enhanced_status(0x01 << 16);

            private int code;

            AllowTypeEnum(int code) {
                this.code = code;
            }


            public int getValue() {
                return code;
            }

            public static List<AllowTypeEnum> getListAllowType(int num){
                ArrayList<AllowTypeEnum> allowTypeEnums=new ArrayList<>();
                for (AllowTypeEnum allowTypeEnum : AllowTypeEnum.values())
                    if((num & allowTypeEnum.getValue())==allowTypeEnum.getValue())
                        allowTypeEnums.add(allowTypeEnum);

                return allowTypeEnums;
            }

            public static int getValue(List<AllowTypeEnum> callTypeEnums){
                int value=none.getValue();
                for (AllowTypeEnum callTypeEnum : callTypeEnums)
                    if(callTypeEnum!=null)
                        value+=callTypeEnum.getValue();

                return value;
            }
        }




    }

    /**
     *
     * <h1>Key Access to values of {@link ActionsCallBack#groupAffiliationEvent} event.</h1>
     *
     */
    public static class GroupAffiliationEventExtras{

        /**
         * <h2>Key Access to MAP<String, Integer> of groups and the current group state.</h2>
         * <p>This data is given in state {@link ConstantsMCOP.GroupAffiliationEventExtras.GroupAffiliationEventTypeEnum#GROUP_AFFILIATION_UPDATE}</p>
         * <p>Response: MAP<String,Integer> being Group ID the key, and the type of affiliation status the value.</p>
         * <p>      notaffiliated                                (Not affiliated, and therefore no action that requires being affiliated can be taken.)   {@link GroupAffiliationEventExtras.GroupAffiliationStateEnum#notaffiliated}</p>
         * <p>      affiliating                                  (Affiliation procedure initiated.)   {@link GroupAffiliationEventExtras.GroupAffiliationStateEnum#affiliating}</p>
         * <p>      affiliated                                   (Affiliated. All MCPTT procedures that require affiliation can be performed.)   {@link GroupAffiliationEventExtras.GroupAffiliationStateEnum#affiliated}</p>
         * <p>      deaffiliating                                (Deaffiliation procedure started.)   {@link GroupAffiliationEventExtras.GroupAffiliationStateEnum#deaffiliating}</p>
         */
        public static final String GROUPS_LIST="org.mcopenplatform.muoapi.ConstantsMCOP.GroupAffiliationEventExtras"+".GROUPS_LIST";

        /**
         * <h2>Key Access to event type:</h2>
         * <p>Value: Integer.</p>
         * <p>Possible event types:</p>
         * <p>     GROUP_AFFILIATION_UPDATE    (Event to update the status of the groups and user's affiliation to them.) {@link ConstantsMCOP.GroupAffiliationEventExtras.GroupAffiliationEventTypeEnum#GROUP_AFFILIATION_UPDATE}</p>
         * <p>     GROUP_AFFILIATION_ERROR     (Error event in some affiliation procedure.) {@link ConstantsMCOP.GroupAffiliationEventExtras.GroupAffiliationEventTypeEnum#GROUP_AFFILIATION_ERROR}</p>
         * <p>     REMOTE_AFFILIATION          (Remote affiliation request by another privileged user. It is mandatory to present the user the option to join or not. In case the user wants to be affiliated, the operation {@link org.mcopenplatform.muoapi.IMCOPsdk#groupAffiliationOperation(String, String, int)} must be executed to affiliate the user to the group.) {@link org.mcopenplatform.muoapi.ConstantsMCOP.GroupAffiliationEventExtras.GroupAffiliationEventTypeEnum#REMOTE_AFFILIATION}</p>
         */
        public static final String EVENT_TYPE="org.mcopenplatform.muoapi.ConstantsMCOP.GroupAffiliationEventExtras"+".EVENT_TYPE";

        /**
         * <h2>Key Access to the Group ID that univocally distinguishes the MCPTT group causing the event.</h2>
         * <p>Response: String with URI format that identifies the MCPTT group.</p>
         * <p>Event {@link ConstantsMCOP.GroupAffiliationEventExtras.GroupAffiliationEventTypeEnum#REMOTE_AFFILIATION} is used to request the group.</p>
         */
        public static final String REMOTE_GROUP_ID="org.mcopenplatform.muoapi.ConstantsMCOP.GroupAffiliationEventExtras"+".REMOTE_GROUP_ID";


        /**
         * <h2>Key Access to Group ID to know the group where the error occurred.</h2>
         * <p>Response: String with URI format that identifies the MCPTT group.</p>
         * <p>Event {@link ConstantsMCOP.GroupAffiliationEventExtras.GroupAffiliationEventTypeEnum#GROUP_AFFILIATION_ERROR} is used.</p>
         */
        public static final String GROUP_ID="org.mcopenplatform.muoapi.ConstantsMCOP.GroupAffiliationEventExtras"+".GROUP_ID";

        /**
         * <h2>Key Access to the User ID that univocally distinguishes the privileged MCPTT user making the explicit affiliation.</h2>
         * <p>Response: String with URI format that identifies the MCPTT user.</p>
         * <p>Event {@link ConstantsMCOP.GroupAffiliationEventExtras.GroupAffiliationEventTypeEnum#REMOTE_AFFILIATION} is used to know the remote user requesting the explicit affiliation.</p>
         *
         */
        public static final String REMOTE_USER_ID="org.mcopenplatform.muoapi.ConstantsMCOP.GroupAffiliationEventExtras"+".REMOTE_USER_ID";

        /**
         * <h2>Key Access to the type of action that is remotely requested to be performed.</h2>
         * <p>Response: {@link GroupAffiliationStateEnum}.</p>
         * <p>Event {@link ConstantsMCOP.GroupAffiliationEventExtras.GroupAffiliationEventTypeEnum#REMOTE_AFFILIATION} is used to know the remote user requesting the explicit affiliation.</p>
         * <p>      affiliating                                  (It must be asked whether the user accepts the affiliation or not.)   {@link GroupAffiliationEventExtras.GroupAffiliationStateEnum#affiliating}</p>
         * <p>      deaffiliating                                (It must be asked whether the user accepts the de-affiliation or not.)   {@link GroupAffiliationEventExtras.GroupAffiliationStateEnum#deaffiliating}</p>
         */
        public static final String REMOTE_ACTION_TYPE="org.mcopenplatform.muoapi.ConstantsMCOP.GroupAffiliationEventExtras"+".REMOTE_ACTION_TYPE";


        /**
         * <h2>Key Access to the affiliation error codes.</h2>
         * <p>Response: Integer indicating the error code.</p>
         * <p>Values:</p>
         * <p>0 means no errors.</p>
         * <p>  101 Non-existent group</p>
         * <p>  102 Action not allowed</p>
         * <p>  103 The user is not a member of the group</p>
         * <p>  104 The group is not currently affiliate</p>
         * <p>  105 The group already is affiliated</p>
         * <p>  106 The group already is not affiliated</p>
         * <p>  107 The group does not belong to the list of existing groups</p>
         * <p>  108 We don't have information of affiliation</p>
         * <p>  109 The group already is affiliating or deaffiliating</p>
         * @See IMCOPsdk#groupAffiliationOperation
         */
        public static final String ERROR_CODE="org.mcopenplatform.muoapi.ConstantsMCOP.GroupAffiliationEventExtras"+".ERROR_CODE";
        /**
         * <h2>Key Access to the error string of the affiliation methods.</h2>
         * <p>Response: String describing the error.</p>
         */
        public static final String ERROR_STRING="org.mcopenplatform.muoapi.ConstantsMCOP.GroupAffiliationEventExtras"+".ERROR_STRING";

        /**
         * <h2>Status types of the referenced group.</h2>
         */
        public enum GroupAffiliationStateEnum{
            none(0x00),
            notaffiliated(0x01),
            affiliating(0x02),
            affiliated(0x03),
            deaffiliating(0x04);
            private int code;
            GroupAffiliationStateEnum(int code) {
                this.code = code;
            }
            public int getValue() {
                return code;
            }

            private static Map map = new HashMap<>();


            static {
                for (GroupAffiliationStateEnum pageType : GroupAffiliationStateEnum.values()) {
                    map.put(pageType.code, pageType);
                }
            }

            public static GroupAffiliationStateEnum fromInt(int pageType) {
                return (GroupAffiliationStateEnum) map.get(pageType);
            }

        }

        /**
         * <h2>Event types of GroupAffiliationEventExtras</h2>
         */
        public enum GroupAffiliationEventTypeEnum{
            NONE (0x00),
            GROUP_AFFILIATION_UPDATE(0x01),
            GROUP_AFFILIATION_ERROR(0x02),
            REMOTE_AFFILIATION(0x03);


            private int code;
            GroupAffiliationEventTypeEnum(int code) {
                this.code = code;
            }
            public int getValue() {
                return code;
            }

            private static Map map = new HashMap<>();


            static {
                for (GroupAffiliationEventTypeEnum pageType : GroupAffiliationEventTypeEnum.values()) {
                    map.put(pageType.code, pageType);
                }
            }

            public static GroupAffiliationEventTypeEnum fromInt(int pageType) {
                return (GroupAffiliationEventTypeEnum) map.get(pageType);
            }
        }

        /**
         *
         * <h1>Event used to indicate the affiliation action that must be used in method {@link org.mcopenplatform.muoapi.IMCOPsdk#groupAffiliationOperation(String, String, int)}.</h1>
         * <p>Values:</p>
         * <p>Error Code 0 is not correct, and it will answer with an error.</p>
         * <p>     Affiliate            (Affiliation action over a groupID) {@link AffiliationOperationTypeEnum#Affiliate}</p>
         * <p>     Deaffiliate          (De-affiliation action over a groupID) {@link AffiliationOperationTypeEnum#Deaffiliate}</p>
         */
        public enum AffiliationOperationTypeEnum{
            none(0x00),
            Affiliate (0x01),
            Deaffiliate (0x02);
            private int code;
            AffiliationOperationTypeEnum(int code) {
                this.code = code;
            }
            public int getValue() {
                return code;
            }
            private static Map map = new HashMap<>();


            static {
                for (AffiliationOperationTypeEnum pageType : AffiliationOperationTypeEnum.values()) {
                    map.put(pageType.code, pageType);
                }
            }

            public static AffiliationOperationTypeEnum fromInt(int pageType) {
                return (AffiliationOperationTypeEnum) map.get(pageType);
            }
        }

    }

    /**
     *
     * <h1>Key Access to the values of event {@link ActionsCallBack#selectedContactChangeEvent}.</h1>
     *
     */
    public static class SelectedContactChangeEventExtras{

        /**
         * <h2>Key Access to Group ID to define the default call group.</h2>
         * <p>Response: String with URI format that identifies the MCPTT group.</p>
         * @See IMCOPsdk#makeCall
         */
        public static final String GROUP_ID="org.mcopenplatform.muoapi.ConstantsMCOP.SelectedContactChangeEventExtras"+".GROUP_ID";

        /**
         * <h2>Key Access to the error codes of default group selection.</h2>
         * <p>Response: Integer indicating the error code.</p>
         * <p>Values:</p>
         * <p>0 means no errors.</p>
         * <p>  101 Non-existent group</p>
         * <p>  102 Action not allowed</p>
         * <p>  103 The user is not a member of that group</p>
         * @See IMCOPsdk#changeSelectedContact
         */
        public static final String ERROR_CODE="org.mcopenplatform.muoapi.ConstantsMCOP.SelectedContactChangeEventExtras"+".ERROR_CODE";
        /**
         * <h2>Key Access to the error strings.</h2>
         * <p>Response: String describing the error.</p>
         */
        public static final String ERROR_STRING="org.mcopenplatform.muoapi.ConstantsMCOP.SelectedContactChangeEventExtras"+".ERROR_STRING";

    }


    /**
     *
     * <h1>Key Access to the values of event {@link ActionsCallBack#eMBMSNotificationEvent}.</h1>
     *
     */
    public static class EMBMSNotificationEventExtras{

        /**
         * <h2>Key Access to the Temporary Mobile Group Identity (TMGI) identifying the eMBMS bearer.</h2>
         * <p>Response: String</p>
         * <p>Used in events:</p>
         * <p>      {@link EMBMSNotificationEventEventTypeEnum#UndereMBMSCoverage}</p>
         * <p>      {@link EMBMSNotificationEventEventTypeEnum#eMBMSBearerInUse}</p>
         * <p>      {@link EMBMSNotificationEventEventTypeEnum#eMBMSBearerNotInUse}</p>
         * <p>      {@link EMBMSNotificationEventEventTypeEnum#NoeMBMSCoverage}</p>
         */
        public static final String TMGI="org.mcopenplatform.muoapi.ConstantsMCOP.EMBMSNotificationEventExtras"+".TMGI";


        /**
         *  <h2>Key Access to the MCPTT session ID. This identifier is unique for each call.</h2>
         *  <p>Response: String</p>
         * <p>Used in events:</p>
         * <p>      {@link EMBMSNotificationEventEventTypeEnum#UndereMBMSCoverage}</p>
         * <p>      {@link EMBMSNotificationEventEventTypeEnum#eMBMSBearerInUse}</p>
         * <p>      {@link EMBMSNotificationEventEventTypeEnum#eMBMSBearerNotInUse}</p>
         * <p>      {@link EMBMSNotificationEventEventTypeEnum#NoeMBMSCoverage}</p>
         */
        public static final String SESSION_ID="org.mcopenplatform.muoapi.ConstantsMCOP.EMBMSNotificationEventExtras"+".SESSION_ID";

        /**
         *  <h2>Key Access to the list of eMBMS service areas.</h2>
         *  <p>Response: String containing all the eMBMS service area identifiers consecutively.</p>
         * <p>Used in events:</p>
         * <p>      {@link EMBMSNotificationEventEventTypeEnum#UndereMBMSCoverage}</p>
         * <p>      {@link EMBMSNotificationEventEventTypeEnum#eMBMSBearerInUse}</p>
         * <p>      {@link EMBMSNotificationEventEventTypeEnum#eMBMSBearerNotInUse}</p>
         * <p>      {@link EMBMSNotificationEventEventTypeEnum#NoeMBMSCoverage}</p>
         */
        public static final String AREA_LIST="org.mcopenplatform.muoapi.ConstantsMCOP.EMBMSNotificationEventExtras"+".AREA_LIST";


        /**
         * Key Access to event type:
         * Value: Integer.
         * <p>Possible event types:</p>
         * <p>     eMBMSAvailable           (eMBMS available) {@link ConstantsMCOP.EMBMSNotificationEventExtras.EMBMSNotificationEventEventTypeEnum#eMBMSAvailable}</p>
         * <p>     UndereMBMSCoverage       (Under eMBMS coverage for a specific TMGI) {@link ConstantsMCOP.EMBMSNotificationEventExtras.EMBMSNotificationEventEventTypeEnum#UndereMBMSCoverage}</p>
         * <p>     eMBMSBearerInUse         (Particular eMBMS bearer in use) {@link ConstantsMCOP.EMBMSNotificationEventExtras.EMBMSNotificationEventEventTypeEnum#eMBMSBearerInUse}</p>
         * <p>     eMBMSBearerNotInUse      (Particular eMBMS bearer not in use) {@link ConstantsMCOP.EMBMSNotificationEventExtras.EMBMSNotificationEventEventTypeEnum#eMBMSBearerNotInUse}</p>
         * <p>     NoeMBMSCoverage          (Not under eMBMS coverage for a specific TMGI) {@link ConstantsMCOP.EMBMSNotificationEventExtras.EMBMSNotificationEventEventTypeEnum#NoeMBMSCoverage}</p>
         * <p>     eMBMSNotAvailable        (eMBMS not available) {@link ConstantsMCOP.EMBMSNotificationEventExtras.EMBMSNotificationEventEventTypeEnum#eMBMSNotAvailable}</p>
         */
        public static final String EVENT_TYPE="org.mcopenplatform.muoapi.ConstantsMCOP.EMBMSNotificationEventExtras"+".EVENT_TYPE";


        /**
         * <h2>Key Access to eMBMS error codes.</h2>
         * <p>Response: Integer indicating the error code.</p>
         * <p>Values:</p>
         * <p>0 means no errors.</p>
         */
        public static final String ERROR_CODE="ConstantsMCOP.EMBMSNotificationEventExtras"+".ERROR_CODE";
        /**
         * <h2>Key Access to error string.</h2>
         * <p>Response: String describing the error.</p>
         */
        public static final String ERROR_STRING="org.mcopenplatform.muoapi.ConstantsMCOP.EMBMSNotificationEventExtras"+".ERROR_STRING";

        /**
         * CallEvent event types.
         */
        public enum EMBMSNotificationEventEventTypeEnum{
            none (0x00),
            eMBMSAvailable(0x01),
            UndereMBMSCoverage(0x02),
            eMBMSBearerInUse(0x03),
            eMBMSBearerNotInUse(0x04),
            NoeMBMSCoverage(0x05),
            eMBMSNotAvailable(0x06);
            private int code;
            EMBMSNotificationEventEventTypeEnum(int code) {
                this.code = code;
            }
            public int getValue() {
                return code;
            }


            private static Map map = new HashMap<>();


            static {
                for (EMBMSNotificationEventEventTypeEnum pageType : EMBMSNotificationEventEventTypeEnum.values()) {
                    map.put(pageType.code, pageType);
                }
            }

            public static EMBMSNotificationEventEventTypeEnum fromInt(int pageType) {
                return (EMBMSNotificationEventEventTypeEnum) map.get(pageType);
            }
        }
    }



    /**
     * <h2>Different MCOP SDK event types for MCPTT services.</h2>
     */
    public enum ActionsCallBack{
        none(""),
        authorizationRequestEvent("org.mcopenplatform.muoapi.ConstantsMCOP.ActionsCallBack.authorizationRequestEvent"),
        unLoginEvent("org.mcopenplatform.muoapi.ConstantsMCOP.ActionsCallBack.unLoginEvent"),
        loginEvent("org.mcopenplatform.muoapi.ConstantsMCOP.ActionsCallBack.loginEvent"),
        configurationUpdateEvent("org.mcopenplatform.muoapi.ConstantsMCOP.ActionsCallBack.configurationUpdateEvent"),
        callEvent("org.mcopenplatform.muoapi.ConstantsMCOP.ActionsCallBack.callEvent"),
        floorControlEvent("org.mcopenplatform.muoapi.ConstantsMCOP.ActionsCallBack.floorControlEvent"),
        groupInfoEvent("org.mcopenplatform.muoapi.ConstantsMCOP.ActionsCallBack.groupInfoEvent"),
        groupAffiliationEvent("org.mcopenplatform.muoapi.ConstantsMCOP.ActionsCallBack.groupAffiliationEvent"),
        selectedContactChangeEvent("org.mcopenplatform.muoapi.ConstantsMCOP.ActionsCallBack.selectedContactChangeEvent"),
        eMBMSNotificationEvent("org.mcopenplatform.muoapi.ConstantsMCOP.ActionsCallBack.eMBMSNotificationEvent");
        private final String text;

        /**
         * @param text
         */
        ActionsCallBack(final String text) {
            this.text = text;
        }

        /* (non-Javadoc)
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return text;
        }


        /**
         * @return the Enum representation for the given string.
         */
        public static ActionsCallBack fromString(String s) {
            for(ActionsCallBack actionsCallBack: ActionsCallBack.values()){
                if(actionsCallBack.toString().compareTo(s)==0)return actionsCallBack;
            }
            return null;
        }

    }

    /**
     * <h1>Event types for method {@link org.mcopenplatform.muoapi.IMCOPsdk#updateEmergencyState(String, int)}.</h1>
     */
    public enum EmergencyTypeEnum{
        NONE (0x00),
        EMERGENCY(0x01),
        NO_EMERGENCY(0x02);

        private int code;
        EmergencyTypeEnum(int code) {
            this.code = code;
        }
        public int getValue() {
            return code;
        }
    }


}