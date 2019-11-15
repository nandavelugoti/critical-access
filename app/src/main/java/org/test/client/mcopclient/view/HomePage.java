package org.test.client.mcopclient.view;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import org.test.client.mcopclient.R;
import org.test.client.mcopclient.controller.MCOPAudioManager;
import org.test.client.mcopclient.controller.MCOPCallManager;
import org.test.client.mcopclient.controller.MCOPServiceManager;
import org.test.client.mcopclient.model.Group;
import org.test.client.mcopclient.model.User;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class HomePage extends AppCompatActivity {
    private static final String TAG = HomePage.class.getCanonicalName();
    private static final int AUTHETICATION_RESULT = 101;
    private static final int GET_PERMISSION = 102;
    private static final String PARAMETER_PROFILE = "parameters";
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    private Button btnPTT;
    private View bottomSheet;
    private static GradientDrawable gradientDrawableBottomSheet;
    private MediaRecorder audioRecorder;

    private static Context ctx;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx=this;
        initializeAddressBook();
        setContentView(R.layout.activity_home_page);
        Log.d(TAG, "onCreate: Starting.");
        setPermissions();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            ArrayList<String> strings = getIntent().getStringArrayListExtra(PARAMETER_PROFILE);
            MCOPServiceManager.initialize(strings);
        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        btnPTT = (Button) findViewById(R.id.button_ptt);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        bottomSheet = findViewById(R.id.design_bottom_sheet);
        BottomSheetBehavior behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setHideable(false);
        behavior.setPeekHeight(200);
        behavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        Drawable background = bottomSheet.getBackground();
        gradientDrawableBottomSheet = (GradientDrawable) background;

        getAudioRecorderReady();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MCOPServiceManager.AddressBook.clearAll();
    }

    private void initializeAddressBook() {
        // Users
        MCOPServiceManager.AddressBook.addUser(new User("sip:mcptt_id_iit2_A@organization.org", "User A"));
        MCOPServiceManager.AddressBook.addUser(new User("sip:mcptt_id_iit2_B@organization.org", "User B"));
        MCOPServiceManager.AddressBook.addUser(new User("sip:mcptt_id_iit2_C@organization.org", "User C"));
        MCOPServiceManager.AddressBook.addUser(new User("sip:mcptt_id_iit2_D@organization.org", "User D"));
        MCOPServiceManager.AddressBook.addUser(new User("sip:mcptt_id_iit2_E@organization.org", "User E"));

        // Groups
        MCOPServiceManager.AddressBook.addGroup(new Group("sip:iit2_group@organization.org", "Group 2"));
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new GroupFragment(), "Groups");
        adapter.addFragment(new ContactFragment(), "Contacts");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_emergency, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.button_emergency:
                MCOPCallManager.toggleERState();
                break;
            case R.id.button_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void updateERUI() {
        ((HomePage)ctx).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (MCOPCallManager.getIsERState()){
                    gradientDrawableBottomSheet.setColor(Color.RED);
                } else {
                    gradientDrawableBottomSheet.setColor(ContextCompat.getColor(ctx,R.color.colorAccent));
                }
            }
        });

    }

    public void updateBtnPTT() {
        Drawable background = btnPTT.getBackground();
        GradientDrawable gradientDrawablePTT = (GradientDrawable) background;
        switch (MCOPCallManager.getCurrentStatusToken()) {
            case IDLE:
            case NONE:
                gradientDrawablePTT.setColor(Color.DKGRAY);
            case GRANTED:
                gradientDrawablePTT.setColor(Color.GREEN);
                break;
            case TAKEN:
                gradientDrawablePTT.setColor(Color.GRAY);
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case AUTHETICATION_RESULT:
                if (resultCode == ScreenAuthenticationWebView.RETURN_ON_AUTHENTICATION_LISTENER_FAILURE) {
                    String dataError;
                    if (data != null &&
                            (dataError = data.getStringExtra(ScreenAuthenticationWebView.RETURN_ON_AUTHENTICATION_ERROR)) != null &&
                            dataError instanceof String) {
                        Log.e(TAG, "Authentication Error: " + dataError);
                    } else {
                        Log.e(TAG, "Error processing authentication.");
                    }
                } else if (resultCode == ScreenAuthenticationWebView.RETURN_ON_AUTHENTICATION_LISTENER_OK) {
                    String dataUri;
                    if (data != null &&
                            (dataUri = data.getStringExtra(ScreenAuthenticationWebView.RETURN_ON_AUTHENTICATION_RESPONSE)) != null &&
                            dataUri instanceof String) {
                        URI uri = null;
                        try {
                            uri = new URI(dataUri);
                            Log.i(TAG, "Uri: " + uri.toString());
                        } catch (URISyntaxException e) {
                            Log.e(TAG, "Authentication Error: " + e.getMessage());
                            e.printStackTrace();
                        }
                    } else {
                        Log.e(TAG, "Error processing file to import profiles.");
                    }
                }
                break;
        }
    }

    /**
     * Set permissions for Android 6.0 or above
     */
    protected void setPermissions() {
        //Set permissions
        //READ_PHONE_STATE
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO)
                != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.RECORD_AUDIO)) {
                //Show an explanation to the user *asynchronously* -- don't block
                //this thread waiting for the user's response! After the user
                //sees the explanation, request the permission again.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE},
                        GET_PERMISSION);
            } else {
                //No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE},
                        GET_PERMISSION);

                //MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                //app-defined int constant. The callback method gets the
                //result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case GET_PERMISSION: {
                //If request is cancelled, result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Permission was granted, yay! Do the
                    //contacts-related task you need to do.
                    //API>22
                    setPermissionsWriteSetting();
                    MCOPServiceManager.connectService(null);
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
    protected void setPermissionsWriteSetting() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (!Settings.System.canWrite(this)) {
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + this.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
    }
    public static void startLogin(String requestUri, String redirect) {
        Intent intent2 = new Intent(ctx, ScreenAuthenticationWebView.class);
        intent2.putExtra(ScreenAuthenticationWebView.DATA_URI_INTENT, requestUri.trim());
        intent2.putExtra(ScreenAuthenticationWebView.DATA_REDIRECTION_URI, redirect.trim());
        ((HomePage) ctx).startActivityForResult(intent2, AUTHETICATION_RESULT);
    }

    public void btnPTTOnClick(View view) {
        updateBtnPTT();
    }

    public void btnVideoOnClick(View view) {
        ImageButton btnVideo = (ImageButton) findViewById(R.id.button_video);
        MCOPCallManager.toggleVideoCall();
        if (MCOPCallManager.getIsVideoCall()){
            btnVideo.setImageResource(R.drawable.ic_mic_red);
            try {
                audioRecorder.prepare();
                audioRecorder.start();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
        } else {
            btnVideo.setImageResource(R.drawable.ic_mic_black);
            audioRecorder.stop();
            audioRecorder.release();
            getAudioRecorderReady();
            Toast.makeText(getApplicationContext(), "Recording stopped", Toast.LENGTH_LONG).show();
        }
    }

    private void getAudioRecorderReady() {
        audioRecorder= new MediaRecorder();
        audioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        audioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        audioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        String outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";
        audioRecorder.setOutputFile(outputFile);
    }

    public void btnPerilOnClick(View view) {
        MCOPCallManager.togglePerilCall();
        if (MCOPCallManager.getIsIPState()){
            gradientDrawableBottomSheet.setColor(Color.BLUE);
        } else {
            gradientDrawableBottomSheet.setColor(ContextCompat.getColor(this,R.color.colorAccent));
        }
    }

    public void btnSpeakerOnClick(View view) {
        ImageButton btnSpeaker = (ImageButton) findViewById(R.id.button_mute);
        MCOPCallManager.toggleSpeaker();
        if (MCOPCallManager.getIsSpeakerphoneOn()){
            btnSpeaker.setImageResource(R.drawable.volume_up);
            MCOPAudioManager.setSpeakerphoneOn();
        } else {
            btnSpeaker.setImageResource(R.drawable.volume_off);
            MCOPAudioManager.setSpeakerphoneOff();
        }
    }

    public void btnAmbientOnClick(View view) {
        ImageButton btnAmbient = (ImageButton) findViewById(R.id.button_ambient);
        MCOPCallManager.toggleAmbientCall();
        if (MCOPCallManager.getIsAmbientOn()){
            btnAmbient.setImageResource(R.drawable.ic_speaker_phone_black);
        } else {
            btnAmbient.setImageResource(R.drawable.ic_speaker_phone_grey);
        }
    }
}
