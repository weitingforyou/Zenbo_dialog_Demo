package com.asus.zenbodialogsample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.test.ActivityTestCase;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.asus.robotframework.API.RobotAPI;
import com.asus.robotframework.API.RobotCallback;
import com.asus.robotframework.API.RobotCmdState;
import com.asus.robotframework.API.RobotErrorCode;
import com.asus.robotframework.API.RobotFace;
import com.asus.robotframework.API.RobotUtil;
import com.asus.robotframework.API.SpeakConfig;
import com.robot.asus.robotactivity.RobotActivity;

import org.json.JSONObject;

public class ZenboThinking extends RobotActivity {
    public final static String TAG = "ZenboMakeSure";
    public final static String DOMAIN = "2C17093E978140CAB8898BD4BDAB9CF5";

    private static TextView mTextView, mAns_1, mAns_2, mAns_3;
    private static ImageButton bt_accept, bt_reject, bt_leave;

    private static RobotAPI mRobotAPI;
    private static Intent mIntent, mGetIntent;
    private static Context mContext;
    private static Activity mActivity;
    private static int CommandSerial_accept, CommandSerial_thinking;

    private static Bundle mBundle;

    private String ans1, ans2, ans3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        mRobotAPI = robotAPI;
        mIntent = new Intent();
        mContext = this.getApplicationContext();
        mBundle = new Bundle();
        mActivity = new Activity();

        mGetIntent = getIntent();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mRobotAPI.robot.setExpression(RobotFace.CONFIDENT);
        String text = "現在我來想想最適合你的理財方式喔！";
        CommandSerial_thinking = mRobotAPI.robot.speak(text);
        Log.d(TAG, "check :"+ CommandSerial_thinking);
    }


    @Override
    protected void onPause() {
        super.onPause();

        //stop listen user utterance
        robotAPI.robot.stopSpeakAndListen();
        //mTextView.setText();
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

            if(serial == CommandSerial_thinking && state != RobotCmdState.ACTIVE){
                mIntent.setClass(mContext, ZenboResult.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mBundle.putInt("Q1_score",mGetIntent.getExtras().getInt("Q1_score", 0));
                mBundle.putInt("Q3_score",mGetIntent.getExtras().getInt("Q3_score", 0));
                mIntent.putExtras(mBundle);
                mContext.startActivity(mIntent);
                mActivity.finish();
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
        public void onEventUserUtterance(JSONObject jsonObject) { }

        @Override
        public void onResult(JSONObject jsonObject) {

        }


        @Override
        public void onRetry(JSONObject jsonObject) {}
    };

    public ZenboThinking() {
        super(robotCallback, robotListenCallback);
    }
}
