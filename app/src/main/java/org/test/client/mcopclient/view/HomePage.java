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
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.test.client.mcopclient.R;
import org.test.client.mcopclient.controller.MCOPAudioManager;
import org.test.client.mcopclient.controller.MCOPCallManager;
import org.test.client.mcopclient.controller.MCOPServiceManager;
import org.test.client.mcopclient.model.Group;
import org.test.client.mcopclient.model.RecordList;
import org.test.client.mcopclient.model.User;
import org.test.client.mcopclient.model.calls.Call;
import org.test.client.mcopclient.model.calls.StatusTokenType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import static org.test.client.mcopclient.model.calls.StatusTokenType.GRANTED;
import static org.test.client.mcopclient.model.calls.StatusTokenType.IDLE;
import static org.test.client.mcopclient.model.calls.StatusTokenType.NONE;

public class HomePage extends AppCompatActivity {
    private static final String TAG = HomePage.class.getCanonicalName();
    private static final int GET_PERMISSION = 102;
    private static final String PARAMETER_PROFILE = "parameters";
    private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    private static Button btnPTT;
    private View bottomSheet;
    private static TextView tvCallStatus;
    private static TextView tvCaller;
    private static TextView tvGroup;
    private static TextView tvCallerId;
    private static TextView tvGroupId;

    private static GradientDrawable gradientDrawableBottomSheet;
    private MediaRecorder audioRecorder;
    public static RecordList recordedFiles;

    static {
        try {
            recordedFiles = new RecordList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        try {
            initializeAddressBook();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_home_page);
        Log.d(TAG, "onCreate: Starting.");
        setPermissions();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
           // ArrayList<String> strings = getIntent().getStringArrayListExtra(PARAMETER_PROFILE);
            //MCOPServiceManager.initialize(strings);
        }

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        btnPTT = (Button) findViewById(R.id.button_ptt);
        btnPTT.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                StatusTokenType currentToken = MCOPCallManager.getCurrentStatusToken();
                if (currentToken != NONE && event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (currentToken == IDLE) {
                        //Request token
                        Log.d(TAG, "TOKEN REQUEST");
                        MCOPCallManager.floorControlOperation(true);
                    }
                } else if (currentToken != NONE && event.getAction() == MotionEvent.ACTION_UP) {
                    if (currentToken == GRANTED) {
                        //Release token
                        Log.d(TAG, "TOKEN RELEASE");
                        MCOPCallManager.floorControlOperation(false);

                    }
                }
                updateBtnPTT();
                return true;
            }
        });

        tvCallStatus = findViewById(R.id.call_status);
        tvCaller = findViewById(R.id.caller);
        tvGroup = findViewById(R.id.caller_group);
        tvCallerId = findViewById(R.id.caller_id);
        tvGroupId = findViewById(R.id.caller_group_id);

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

        //getAudioRecorderReady();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            MCOPServiceManager.AddressBook.clearAll();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeAddressBook() throws IOException {
        // load from local data
         MCOPServiceManager.AddressBook.load_contacts();
         MCOPServiceManager.AddressBook.load_groups();

        // Users (optional)
//        MCOPServiceManager.AddressBook.addUser(new User("sip:mcptt_id_iit2_A@organization.org", "User A"));
//        MCOPServiceManager.AddressBook.addUser(new User("sip:mcptt_id_iit2_B@organization.org", "User B"));
//        MCOPServiceManager.AddressBook.addUser(new User("sip:mcptt_id_iit2_C@organization.org", "User C"));
//        MCOPServiceManager.AddressBook.addUser(new User("sip:mcptt_id_iit2_D@organization.org", "User D"));
//        MCOPServiceManager.AddressBook.addUser(new User("sip:mcptt_id_iit2_E@organization.org", "User E"));

        // Groups
//        MCOPServiceManager.AddressBook.addGroup(new Group("sip:iit2_group@organization.org", "Group 2"));
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new GroupFragment(), "Groups");
        adapter.addFragment(new ContactFragment(), "Contacts");
        adapter.addFragment(new RecordFragment(), "Recordings");
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
        ((HomePage) ctx).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (MCOPCallManager.getIsERState()) {
                    gradientDrawableBottomSheet.setColor(Color.RED);
                } else {
                    gradientDrawableBottomSheet.setColor(ContextCompat.getColor(ctx, R.color.colorAccent));
                }
            }
        });

    }

    public static void updateCallStatus() {
        ((HomePage) ctx).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                StatusTokenType status = MCOPCallManager.getCurrentStatusToken();
                tvCallStatus.setText(status.toString());
            }
        });
    }

    public static void updateCaller() {
        ((HomePage) ctx).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                User tokenHolder = MCOPCallManager.getTokenHolder();
                if(tokenHolder != null)
                    tvCaller.setText(tokenHolder.getDisplayName());
            }
        });
    }

    public static void updateGroup() {
        ((HomePage) ctx).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Group group = MCOPCallManager.getCurrentGroup();
                if(group != null)
                    tvGroup.setText(group.getDisplayName());
            }
        });
    }

    public static void updateCallerId() {
        ((HomePage) ctx).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                User tokenHolder = MCOPCallManager.getTokenHolder();
                if(tokenHolder != null)
                    tvCallerId.setText(tokenHolder.getMcpttID());
            }
        });
    }

    public static void updateGroupId() {
        ((HomePage) ctx).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Group group = MCOPCallManager.getCurrentGroup();
                if(group != null)
                    tvGroupId.setText(group.getMcpttID());
            }
        });
    }

    public static void updateBtnPTT() {
        ((HomePage) ctx).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Drawable background = btnPTT.getBackground();
                GradientDrawable gradientDrawablePTT = (GradientDrawable) background;
                switch (MCOPCallManager.getCurrentStatusToken()) {
                    case IDLE:
                    case NONE:
                        gradientDrawablePTT.setColor(Color.DKGRAY);
                        break;
                    case GRANTED:
                        gradientDrawablePTT.setColor(Color.GREEN);
                        break;
                    case TAKEN:
                        gradientDrawablePTT.setColor(Color.RED);
                        break;
                }
            }
        });

    }

    // END GUI

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
            //No explanation needed, we can request the permission.
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE},
                    GET_PERMISSION);

            //MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            //app-defined int constant. The callback method gets the
            //result of the request.
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

    public void btnRecordOnClick(View view) {
        ImageButton btnRecord = (ImageButton) findViewById(R.id.button_record);
        MCOPCallManager.toggleRecordCall();
        if (MCOPCallManager.getIsRecordingCall()) {
            getAudioRecorderReady();
            btnRecord.setImageResource(R.drawable.ic_mic_red);
            try {
                audioRecorder.prepare();
                audioRecorder.start();
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
            Toast.makeText(getApplicationContext(), "Recording started", Toast.LENGTH_LONG).show();
        } else {
            btnRecord.setImageResource(R.drawable.ic_mic_black);
            audioRecorder.stop();
            audioRecorder.release();
            Toast.makeText(getApplicationContext(), "Recording stopped", Toast.LENGTH_LONG).show();
        }
    }

    private void getAudioRecorderReady() {
        audioRecorder = new MediaRecorder();
        audioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        audioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        audioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        String recordName = Calendar.getInstance().getTime().toString();
        String outputFile = recordedFiles.getStorageLocation() + recordName +
                recordedFiles.getExtension();
        recordedFiles.addRecord(recordName);
        RecordFragment.updateRecordList();
        audioRecorder.setOutputFile(outputFile);
    }

    public void btnPerilOnClick(View view) {
        MCOPCallManager.togglePerilCall();
        if (MCOPCallManager.getIsIPState()) {
            gradientDrawableBottomSheet.setColor(Color.BLUE);
        } else {
            gradientDrawableBottomSheet.setColor(ContextCompat.getColor(this, R.color.colorAccent));
        }
    }

    public void btnSpeakerOnClick(View view) {
        ImageButton btnSpeaker = (ImageButton) findViewById(R.id.button_mute);
        MCOPCallManager.toggleSpeaker();
        if (MCOPCallManager.getIsSpeakerphoneOn()) {
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
        if (MCOPCallManager.getIsAmbientOn()) {
            btnAmbient.setImageResource(R.drawable.ic_speaker_phone_black);
        } else {
            btnAmbient.setImageResource(R.drawable.ic_speaker_phone_grey);
        }
    }
}
