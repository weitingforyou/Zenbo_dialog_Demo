package com.asus.zenbodialogsample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.asus.robotframework.API.RobotAPI;
import com.asus.robotframework.API.RobotCallback;
import com.asus.robotframework.API.RobotCmdState;
import com.asus.robotframework.API.RobotErrorCode;
import com.asus.robotframework.API.RobotFace;
import com.asus.robotframework.API.RobotUtil;
import com.robot.asus.robotactivity.RobotActivity;

import org.json.JSONObject;

public class ZenboDialogSample extends RobotActivity {
    public final static String TAG = "ZenboDialogSample";
    public final static String DOMAIN = "2C17093E978140CAB8898BD4BDAB9CF5";

    //private static TextView mTextView;
    private static RobotAPI mRobotAPI;
    private static Intent mIntent;
    private static Context mContext_1;
    private static int iCurrentCommandSerial;
    private static Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_zenbo_dialog_sample);

        mRobotAPI = robotAPI;
        mIntent = new Intent();
        mContext_1 = this.getApplicationContext();
        mActivity = ZenboDialogSample.this;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // set beginning expression : default
        robotAPI.robot.setExpression(RobotFace.INTERESTED);

        // jump dialog domain
        robotAPI.robot.jumpToPlan(DOMAIN, "ThisPlanLaunchingThisApp");

        // listen user utterance
        //robotAPI.robot.speakAndListen("你好，我是理財助理Juicy！請站在我的前方並看著我的眼睛，讓我認識你。", new SpeakConfig().timeout(20));

        // show hint
        //mTextView.setText(getResources().getString(R.string.dialog_example));

    }


    @Override
    protected void onPause() {
        super.onPause();

        //stop listen user utterance
        robotAPI.robot.stopSpeakAndListen();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public static RobotCallback robotCallback = new RobotCallback() {
            @Override
            public void onResult(int cmd, int serial, RobotErrorCode err_code, Bundle result) {
                super.onResult(cmd, serial, err_code, result);
            }

            @Override
        public void onStateChange(int cmd, int serial, RobotErrorCode err_code, RobotCmdState state) {
            super.onStateChange(cmd, serial, err_code, state);
                if (serial == iCurrentCommandSerial && state != RobotCmdState.ACTIVE){
                    Log.d(TAG, "command: "+ iCurrentCommandSerial + " SUCCEED");
                    mIntent.setClass(mContext_1, ZenboStartService.class);
                    mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext_1.startActivity(mIntent);
                    mActivity.finish();
                    System.exit(0);
                }
        }
    };


    public static RobotCallback.Listen robotListenCallback = new RobotCallback.Listen() {
        @Override
        public void onFinishRegister() {

        }

        @Override
        public void onVoiceDetect(JSONObject jsonObject) {

        }

        @Override
        public void onSpeakComplete(String s, String s1) {

        }

        @Override
        public void onEventUserUtterance(JSONObject jsonObject) {
            String text;
            text = "onEventUserUtterance: " + jsonObject.toString();
            Log.d(TAG, text);
        }

        @Override
        public void onResult(JSONObject jsonObject) {
            String text;
            text = "onResult: " + jsonObject.toString();
            Log.d(TAG, text);


            String sIntentionID = RobotUtil.queryListenResultJson(jsonObject, "IntentionId");
            Log.d(TAG, "Intention Id = " + sIntentionID);

            if(sIntentionID.equals("ThisPlanLaunchingThisApp")){
                text = "你好，我是理財助理Juicy！ 我想要認識你，請站在我的前方並看著我，我五秒後會幫你拍張照喔。";
                iCurrentCommandSerial = mRobotAPI.robot.speak(text);
                Log.d(TAG, "check :"+ iCurrentCommandSerial);
            }
        }

        @Override
        public void onRetry(JSONObject jsonObject) {}
    };



    public ZenboDialogSample() {
        super(robotCallback, robotListenCallback);
    }


}

