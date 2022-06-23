package com.example.zoommeetingsdk;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import us.zoom.sdk.ChatMessageDeleteType;
import us.zoom.sdk.FreeMeetingNeedUpgradeType;
import us.zoom.sdk.InMeetingAudioController;
import us.zoom.sdk.InMeetingChatMessage;
import us.zoom.sdk.InMeetingCloudRecordController;
import us.zoom.sdk.InMeetingEventHandler;
import us.zoom.sdk.InMeetingService;
import us.zoom.sdk.InMeetingServiceListener;
import us.zoom.sdk.JoinMeetingOptions;
import us.zoom.sdk.JoinMeetingParam4WithoutLogin;
import us.zoom.sdk.JoinMeetingParams;
import us.zoom.sdk.MeetingOptions;
import us.zoom.sdk.MeetingService;
import us.zoom.sdk.MeetingStatus;
import us.zoom.sdk.MobileRTCSDKError;
import us.zoom.sdk.StartMeetingOptions;
import us.zoom.sdk.StartMeetingParamsWithoutLogin;
import us.zoom.sdk.VideoQuality;
import us.zoom.sdk.ZoomApiError;
import us.zoom.sdk.ZoomAuthenticationError;
import us.zoom.sdk.ZoomError;
import us.zoom.sdk.ZoomSDK;
import us.zoom.sdk.ZoomSDKAuthenticationListener;
import us.zoom.sdk.ZoomSDKInitParams;
import us.zoom.sdk.ZoomSDKInitializeListener;

public class MainActivity extends AppCompatActivity implements InMeetingServiceListener, InMeetingCloudRecordController {

    String meetingID = "9992743515";
    String meetingPassword = "8jDsH3";
    String meetingDisplayName = "Pritee LRM";
    ZoomSDK sdk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);

        initializeSdk(this);
        //initViews();

        if (sdk.isInitialized())
        {
            //Log.d("TAG", "sdk.isInitialized(): " + sdk.isInitialized());
            registerForListener();
        }

    }

    private void registerForListener() {
        InMeetingService inMeetingService = sdk.getInMeetingService();
        inMeetingService.addListener(this);

        InMeetingCloudRecordController inMeetingCloudRecordController = sdk.getInMeetingService().getInMeetingCloudRecordController();
    }

    /*private ZoomSDKAuthenticationListener authListener = new ZoomSDKAuthenticationListener() {

        @Override
        public void onZoomSDKLoginResult(long result) {
            if (result == ZoomAuthenticationError.ZOOM_AUTH_ERROR_SUCCESS) {
                startMeeting(MainActivity.this);
            }
        }
    };*/

    /*public void startMeeting(Context context) {
        ZoomSDK sdk = ZoomSDK.getInstance();
        if (sdk.isLoggedIn()) {
            MeetingService meetingService = sdk.getMeetingService();
            StartMeetingOptions options = new StartMeetingOptions();
            meetingService.startInstantMeeting(context, options);
        }
    }*/

    public void initializeSdk(Context context) {
        sdk = ZoomSDK.getInstance();
        ZoomSDKInitParams params = new ZoomSDKInitParams();
        //params.appKey = "Gx5K4iOWepKWB6pc7ImIHVvUUdMLkqZv4xJb";
        params.appKey = "OcGYWDjNVm1gEdE1BVDG8m8Tzkof6hDhSKiO";
        //params.appSecret = "hxRJeb6jf3VebFDczEBhYE23edvubn5sY7w8";
        params.appSecret = "acdGfr2XCtIzCj1NVySIy5SzhGOnVvmJLASo";
        params.domain = "zoom.us";
        params.enableLog = true;
        //Log.d("", "initializeSdk ");
        ZoomSDKInitializeListener listener = new ZoomSDKInitializeListener() {
            @Override
            public void onZoomSDKInitializeResult(int errorCode, int internalErrorCode) {
                //Log.d("", "onZoomSDKInitializeResulttttt ");
                //Log.d("", "onZoomSDKInitializeResult errorCode" + errorCode);
                if(errorCode == ZoomError.ZOOM_ERROR_SUCCESS){
                    MeetingService meetingService = ZoomSDK.getInstance().getMeetingService();
                    JoinMeetingOptions options = new JoinMeetingOptions();

                    JoinMeetingParam4WithoutLogin joinMeetingParam4WithoutLogin = new JoinMeetingParam4WithoutLogin();
                    joinMeetingParam4WithoutLogin.meetingNo = meetingID;
                    joinMeetingParam4WithoutLogin.password = meetingPassword;
                    joinMeetingParam4WithoutLogin.displayName = meetingDisplayName;
                    //Log.d("glass", "joinMeetingWithParams");
                    meetingService.joinMeetingWithParams(MainActivity.this, joinMeetingParam4WithoutLogin, options);
                }
            }

            @Override
            public void onZoomAuthIdentityExpired() { }
        };
        sdk.initialize(context, listener, params);
        //Log.d("TAG", "sdk.isInitialized(): " + sdk.isInitialized());
    }

    private void initViews() {
        findViewById(R.id.join_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //createJoinMeetingDialog();
                joinMeeting();

            }
        });

        /*findViewById(R.id.login_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ZoomSDK.getInstance().isLoggedIn()) {
                    //startMeeting(MainActivity.this);
                    startMeetingZak(MainActivity.this);
                } else {
                    //createLoginDialog();
                    createJoinMeetingDialog();

                }
            }
        });*/
    }

    private void joinMeeting() {
        MeetingService meetingService = ZoomSDK.getInstance().getMeetingService();
        JoinMeetingOptions options = new JoinMeetingOptions();

        JoinMeetingParam4WithoutLogin joinMeetingParam4WithoutLogin = new JoinMeetingParam4WithoutLogin();
        joinMeetingParam4WithoutLogin.meetingNo = "9992743515";
        joinMeetingParam4WithoutLogin.password = "8jDsH3";
        joinMeetingParam4WithoutLogin.displayName = "Pritee";

        //Log.d("TAG", "Attr: " + joinMeetingParam4WithoutLogin.meetingNo);
        meetingService.joinMeetingWithParams(this, joinMeetingParam4WithoutLogin, options);

    }


    @Override
    public void onMeetingNeedPasswordOrDisplayName(boolean b, boolean b1, InMeetingEventHandler inMeetingEventHandler) {

    }

    @Override
    public void onWebinarNeedRegister(String s) {

    }

    @Override
    public void onJoinWebinarNeedUserNameAndEmail(InMeetingEventHandler inMeetingEventHandler) {

    }

    @Override
    public void onMeetingNeedCloseOtherMeeting(InMeetingEventHandler inMeetingEventHandler) {

    }

    @Override
    public void onMeetingFail(int i, int i1) {

    }

    @Override
    public void onMeetingLeaveComplete(long l) {

    }

    @Override
    public void onMeetingUserJoin(List<Long> list) {

    }

    @Override
    public void onMeetingUserLeave(List<Long> list) {

    }

    @Override
    public void onMeetingUserUpdated(long l) {

    }

    @Override
    public void onMeetingHostChanged(long l) {

    }

    @Override
    public void onMeetingCoHostChanged(long l) {

    }

    @Override
    public void onMeetingCoHostChange(long l, boolean b) {

    }

    @Override
    public void onActiveVideoUserChanged(long l) {

    }

    @Override
    public void onActiveSpeakerVideoUserChanged(long l) {

    }

    @Override
    public void onHostVideoOrderUpdated(List<Long> list) {

    }

    @Override
    public void onFollowHostVideoOrderChanged(boolean b) {

    }

    @Override
    public void onSpotlightVideoChanged(boolean b) {

    }

    @Override
    public void onUserVideoStatusChanged(long l, VideoStatus videoStatus) {

    }

    @Override
    public void onUserNetworkQualityChanged(long l) {

    }

    @Override
    public void onSinkMeetingVideoQualityChanged(VideoQuality videoQuality, long l) {

    }

    @Override
    public void onMicrophoneStatusError(InMeetingAudioController.MobileRTCMicrophoneError mobileRTCMicrophoneError) {

    }

    @Override
    public void onUserAudioStatusChanged(long l, AudioStatus audioStatus) {

    }

    @Override
    public void onHostAskUnMute(long l) {

    }

    @Override
    public void onHostAskStartVideo(long l) {

    }

    @Override
    public void onUserAudioTypeChanged(long l) {

    }

    @Override
    public void onMyAudioSourceTypeChanged(int i) {

    }

    @Override
    public void onLowOrRaiseHandStatusChanged(long l, boolean b) {

    }

    @Override
    public void onChatMessageReceived(InMeetingChatMessage inMeetingChatMessage) {

    }

    @Override
    public void onChatMsgDeleteNotification(String s, ChatMessageDeleteType chatMessageDeleteType) {

    }

    @Override
    public void onSilentModeChanged(boolean b) {

    }

    @Override
    public void onFreeMeetingReminder(boolean b, boolean b1, boolean b2) {

    }

    @Override
    public void onMeetingActiveVideo(long l) {

    }

    @Override
    public void onSinkAttendeeChatPriviledgeChanged(int i) {

    }

    @Override
    public void onSinkAllowAttendeeChatNotification(int i) {

    }

    @Override
    public void onUserNameChanged(long l, String s) {

    }

    @Override
    public void onUserNamesChanged(List<Long> list) {

    }

    @Override
    public void onFreeMeetingNeedToUpgrade(FreeMeetingNeedUpgradeType freeMeetingNeedUpgradeType, String s) {

    }

    @Override
    public void onFreeMeetingUpgradeToGiftFreeTrialStart() {

    }

    @Override
    public void onFreeMeetingUpgradeToGiftFreeTrialStop() {

    }

    @Override
    public void onFreeMeetingUpgradeToProMeeting() {

    }

    @Override
    public void onClosedCaptionReceived(String s, long l) {

    }

    @Override
    public void onRecordingStatus(RecordingStatus recordingStatus) {

    }

    @Override
    public void onLocalRecordingStatus(long l, RecordingStatus recordingStatus) {

    }

    @Override
    public void onInvalidReclaimHostkey() {

    }

    @Override
    public void onPermissionRequested(String[] strings) {

    }

    @Override
    public void onAllHandsLowered() {

    }

    @Override
    public void onLocalVideoOrderUpdated(List<Long> list) {

    }

    @Override
    public boolean isCloudRecordEnabled() {
        //Log.d("TAG", "isCloudRecordEnabled(): " + isCloudRecordEnabled());
        return true;
    }

    @Override
    public MobileRTCSDKError startCloudRecord() {
        return null;
    }

    @Override
    public MobileRTCSDKError stopCloudRecord() {
        return null;
    }

    @Override
    public MobileRTCSDKError pauseCloudRecord() {
        return null;
    }

    @Override
    public MobileRTCSDKError resumeCloudRecord() {
        return null;
    }

    @Override
    public boolean isCloudRecordInProgress() {
        //Log.d("TAG", "isCloudRecordInProgress(): " + isCloudRecordInProgress());
        return true;
    }

    @Override
    public boolean isCloudRecordPaused() {
        //Log.d("TAG", "isCloudRecordPaused(): " + isCloudRecordPaused());
        return false;
    }

    @Override
    public boolean isMeetingBeingRecording() {
        //Log.d("TAG", "isMeetingBeingRecording(): " + isMeetingBeingRecording());
        return true;
    }

    @Override
    public boolean isRecordingMeetingOnCloud() {
        //Log.d("TAG", "isRecordingMeetingOnCloud(): " + isRecordingMeetingOnCloud());
        return true;
    }

    /*public void login(String username, String password) {
        int result = ZoomSDK.getInstance().loginWithZoom(username, password);
        if (result == ZoomApiError.ZOOM_API_ERROR_SUCCESS) {
            // Request executed, listen for result to start meeting
            ZoomSDK.getInstance().addAuthenticationListener(authListener);
        }
    }

    public void startMeetingZak(Context context) {
        MeetingService meetingService = ZoomSDK.getInstance().getMeetingService();
        JoinMeetingOptions options = new JoinMeetingOptions();
        StartMeetingParamsWithoutLogin params = new StartMeetingParamsWithoutLogin();
            params.displayName = "";
            params.userId = "";
            params.zoomAccessToken = "";

        meetingService.startMeetingWithParams(context, params, options);
    }*/

    /*private void createJoinMeetingDialog() {
        new AlertDialog.Builder(this)
                .setView(R.layout.dialog_join_meeting)
                .setPositiveButton("Join", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlertDialog dialog = (AlertDialog) dialogInterface;
                        TextInputEditText numberInput = dialog.findViewById(R.id.meeting_no_input);
                        TextInputEditText passwordInput = dialog.findViewById(R.id.password_input);
                        if (numberInput != null && numberInput.getText() != null && passwordInput != null && passwordInput.getText() != null) {
                            String meetingNumber = numberInput.getText().toString();
                            String password = passwordInput.getText().toString();
                            if (meetingNumber.trim().length() > 0 && password.trim().length() > 0) {
                                joinMeeting(MainActivity.this, meetingNumber, password);
                            }
                        }
                        dialog.dismiss();
                    }
                })
                .show();
    }*/

    /*private void createLoginDialog() {
        new AlertDialog.Builder(this)
                .setView(R.layout.dialog_login)
                .setPositiveButton("Log in", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlertDialog dialog = (AlertDialog) dialogInterface;
                        TextInputEditText emailInput = dialog.findViewById(R.id.email_input);
                        TextInputEditText passwordInput = dialog.findViewById(R.id.pw_input);
                        if (emailInput != null && emailInput.getText() != null && passwordInput != null && passwordInput.getText() != null) {
                            String email = emailInput.getText().toString();
                            String password = passwordInput.getText().toString();
                            if (email.trim().length() > 0 && password.trim().length() > 0) {
                                login(email, password);
                            }
                        }
                        dialog.dismiss();
                    }
                })
                .show();
    }*/

    /*public void joinMeeting(Context context, String meetingNumber, String password) {
        MeetingService meetingService = ZoomSDK.getInstance().getMeetingService();
        JoinMeetingOptions options = new JoinMeetingOptions();
        JoinMeetingParams params = new JoinMeetingParams();
        params.displayName = "";
        params.meetingNo = meetingNumber;
        params.password = password;
        meetingService.joinMeetingWithParams(context, params, options);
    }*/
}
