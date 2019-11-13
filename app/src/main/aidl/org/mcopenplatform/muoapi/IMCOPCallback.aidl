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
import android.os.ParcelFileDescriptor;



/**
 * AIDL definition {@link https://developer.android.com/guide/components/aidl.html}
 * Used as a callback for MCOP SDK server-client communication, and for MCPTT (Mission Critical Push to Talk) Services.
 * @version 0.1
 */
interface IMCOPCallback {
    /**
     *
     * Callback to be listened to by the client. It provides active events, responses to methods
     * executed by the client, asynchronous events from the MCPTT system, and errors produced.
     *
     * @return
     * @see org.mcopenplatform.muoapi.ConstantsMCOP.ActionsCallBack org.mcopenplatform.muoapi.ConstantsMCOP.ActionsCallBack (Types of actions that each of the callback events can have)
     * @param actionList Intent list. Each component in the list contains an event.
     */
    void handleOnEvent(in List<Intent> actionList);


}