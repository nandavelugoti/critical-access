> *Refer to* [*README*](../README.md) *for main instruction file*

# MCOP - MCPTT App Development

## What is MCOP?

**MCOP** (Mission Critical Open Platform) is a project that defines the first prototype of open source **MCPTT** (Mission Critical Push-To-Talk) client.

This client allows the interoperability among different vendors thanks to a multilevel API.

## Architecture

Three main modules define the architecture: 

* **MCOP SDK**:

	This module contains all the MCPTT logic, and allows the use of other additional modules that increase its functionality. 

* **MCOP Integration Plugins**:

	Additional modules or Android services with their own API, known by the MCOP SDK module, offering device specific or MCPTT network required functionalities. 

* **End User Interface**:

	GUI (Graphical User Interface) that uses the MCOP SDK services without having to develop the whole MCPTT logic. *(Min. level of Android SDK: API 17)*.

![ThreMainModules](../images/threemainmodules.png)

## App Developing Steps

* **0. MCOP SDK and AIDLs**: The SDK defines two AIDLs and one Constant class:

	* **IMCOPsdk**: From Client to SDK.

	* **IMCOPCallBack**: From SDK to Client.

	* **ConstantsMCOP**: defines the constants to use in the AIDLs.

* **1. Configure Manifest**: Indicate the necessary permissions for the application.

		<uses-permission android:name="android.permission.INTERNET" />
		<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
		<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
		<uses-permission android:name="android.permission.CHANGE_WIFI_STATE" />
		<uses-permission android:name="android.permission.CHANGE_NETWORK_STATE" />
		<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
		<uses-permission android:name="android.permission.CAMERA" />
		<uses-permission android:name="android.permission.WAKE_LOCK" />
		<uses-permission android:name="android.permission.RECORD_AUDIO" />
		<uses-permission android:name="android.permission.MODIFY_AUDIO_SETTINGS" />
		<uses-permission android:name="android.permission.VIBRATE" />
		<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED" />
		<uses-permission android:name="android.permission.WRITE_SETTINGS" />
		<uses-permission android:name="android.permission.DISABLE_KEYGUARD" />
		<uses-permission android:name="android.permission.READ_CONTACTS" />
		<uses-permission android:name="android.permission.WRITE_CONTACTS" />
		<uses-permission android:name="android.permission.READ_PHONE_STATE" />
		<uses-permission android:name="android.permission.PROCESS_OUTGOING_CALLS" />
		<uses-permission android:name="android.permission.CALL_PHONE" />
		<uses-permission android:name="android.permission.RAISED_THREAD_PRIORITY" />
		<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
		<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />

	Define the activities to be used:

		<application
			android:allowBackup="true"
			android:icon="@mipmap/ic_launcher"
			android:label="@string/app_name"
			android:roundIcon="@mipmap/ic_launcher_round"
			android:supportsRtl="true"
			android:theme="@style/AppTheme" >
			<activity android:name=".MainActivity"
				android:screenOrientation="portrait">
				<intent-filter>
					<action android:name="android.intent.action.MAIN" />
					<category android:name="android.intent.category.LAUNCHER" />
				</intent-filter>
			</activity>
		</application>

* **2. Require the explicit acceptance of some of the permissions.**

	The client should explicitly accept some permissions:

	* Manifest.permission.ACCESS\_FINE\_LOCATION

	* Manifest.permission.READ\_PHONE\_STATE

	* Manifest.permission.ACCESS\_COARSE\_LOCATION

	* Manifest.permission.CAMERA

	* Manifest.permission.RECORD\_AUDIO

		![Allow audio](../images/allow_audio.png)

* **3. Bind to MCOP SDK**:

	In this version of MCOP SDK, it is necessary to send **“currentProfile”** to the service:

		Intent serviceIntent = new Intent()
			.setComponent(new ComponentName(
				"org.mcopenplatform.muoapi",
				"org.mcopenplatform.muoapi.MCOPsdk")); 
		//This is only for tresting
		serviceIntent.putExtra("PROFILE_SELECT",currentProfile);
		
		ComponentName componentName=startService(serviceIntent);
		bindService(serviceIntent, mConnection, BIND_AUTO_CREATE);

	Wait for the service to bind:

	* Save the return value from the Service (this is the AIDL Interface to be used by the Client to communicate with the Service).

	* Register the callback for asynchronous communication from the Service to the Client:

			mConnection = new ServiceConnection() {
			
			@Override
			public void onServiceConnected(ComponentName className, IBinder service) {			
				mService = IMCOPsdk.Stub.asInterface(service);
				try {
					mService.registerCallback(mMCOPCallback);
				} catch (RemoteException e) {
					e.printStackTrace();
				}
				isConnect=true;
				
				//AUTO Register
				final Handler handler = new Handler();
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						try {
							if(mService!=null)
						mService.authorizeUser(null);
						} catch (RemoteException e) {
							e.printStackTrace();
						}
					}
				}, DEFAULT_REGISTER_DELAY);
			}

	* Definition of the registered callback:

		* This is the point where communications from the Server to the Client will be received:

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
									if(action!=null &&
										action.getAction()!=null &&
										!action.getAction().trim().isEmpty())
											try {
												switch (ConstantsMCOP.ActionsCallBack.fromString(action.getAction())){
												...
												}
											}catch (Exception ex){
												Log.e(TAG,"Event Action Error: "+action.getAction()+" error:"+ex.getMessage());
											}
									}
								}
							});
						}
					};


* **4. Perform any action in the MCOP SDK and receive the result**.

	Example:

		if(mService!=null)
			mService.makeCall(
				selGroup, //DEFAULT_GROUP,
				ConstantsMCOP.CallEventExtras.CallTypeEnum.Audio.getValue()|
				ConstantsMCOP.CallEventExtras.CallTypeEnum.WithFloorCtrl.getValue()| 
				ConstantsMCOP.CallEventExtras.CallTypeEnum.PrearrangedGroup.getValue()
			);
			





