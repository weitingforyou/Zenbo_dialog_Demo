package com.asus.zenbodialogsample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class ZenboStartService extends RobotActivity {
    public final static String TAG = "ZenboStartService";
    public final static String DOMAIN = "2C17093E978140CAB8898BD4BDAB9CF5";

    private static TextView mTextView;
    private static RobotAPI mRobotAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zenbo_start_service);

        mTextView = (TextView) findViewById(R.id.textView_question);
        //mTextView.setText(DOMAIN);

        mRobotAPI = robotAPI;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // set beginning expression : default
        robotAPI.robot.setExpression(RobotFace.HIDEFACE);

        // jump dialog domain
        robotAPI.robot.jumpToPlan(DOMAIN, "beforestart");

        // listen user utterance
        robotAPI.robot.speakAndListen("我想為你提出一些理財建議，你願意嗎？", new SpeakConfig().timeout(20));

        // show hint
        mTextView.setText("我想為你提出一些理財建議，你願意嗎？");

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

            if(sIntentionID.equals("ProvideService")) {

                String sSluResultCity = RobotUtil.queryListenResultJson(jsonObject, "ans_AcceptReject", null);
                Log.d(TAG, "Response =" + sSluResultCity);

                if(sSluResultCity.equals("reject")) {
                    Log.d(TAG, "你選擇了不好");
                    mTextView.setText("你選擇了不好");
                }
                else if (sSluResultCity.equals("accept")) {
                    Log.d(TAG, "你選擇了好");
                    mTextView.setText("你選擇了好");
                }
                else{
                    Log.d(TAG, "failed QQ");
                }
            }

        }

        @Override
        public void onRetry(JSONObject jsonObject) {}
    };



    public ZenboStartService() {
        super(robotCallback, robotListenCallback);
    }
}
