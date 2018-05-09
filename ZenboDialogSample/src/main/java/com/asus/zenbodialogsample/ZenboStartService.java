package com.asus.zenbodialogsample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
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
    private static Button bt_accept, bt_reject;
    private static ImageView imageView;

    private static RobotAPI mRobotAPI;
    private static Intent mIntent;
    private static Context mContext;
    private static int CommandSerial_accept, CommandSerial_reject;
    private static Activity mActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_zenbo_start_service);

        mTextView = (TextView) findViewById(R.id.textView_question);
        bt_accept = (Button)findViewById(R.id.button_accept);
        bt_reject = (Button)findViewById(R.id.button_reject);
        imageView = (ImageView)findViewById(R.id.imageView);

        mRobotAPI = robotAPI;
        mIntent = new Intent();
        mContext = this.getApplicationContext();
        mActivity = ZenboStartService.this;
        //imageView.setImageDrawable(R.drawable.illustration);


        bt_accept.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                robotAPI.robot.stopSpeakAndListen();

                mRobotAPI.robot.setExpression(RobotFace.HAPPY);
                String text = "太棒了！那我會請你回答三個問題，讓我更了解你，我們開始吧！";
                CommandSerial_accept = mRobotAPI.robot.speak(text);
                Log.d(TAG, "check :"+ CommandSerial_accept);

            }
        });

        bt_reject.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                robotAPI.robot.stopSpeakAndListen();

                mRobotAPI.robot.setExpression(RobotFace.INNOCENT);
                String text = "太可惜了！那你隨時可以再來找我喔！";
                CommandSerial_reject = mRobotAPI.robot.speak(text);
                Log.d(TAG, "check :"+ CommandSerial_reject);
            }
        });
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

            if (serial == CommandSerial_reject && state != RobotCmdState.ACTIVE){
                Log.d(TAG, "command: "+ CommandSerial_reject + " SUCCEED");
                mActivity.finish();
                System.exit(0);
            }

            if (serial == CommandSerial_accept && state != RobotCmdState.ACTIVE){
                Log.d(TAG, "command: "+ CommandSerial_accept + " SUCCEED");
                mIntent.setClass(mContext, ZenboQuestionOne.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(mIntent);
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

            if(sIntentionID.equals("beforestart")) {

                String sSluResultCity = RobotUtil.queryListenResultJson(jsonObject, "ans_AcceptReject", null);
                Log.d(TAG, "Response =" + sSluResultCity);

                if(sSluResultCity.equals("reject")) {
                    Log.d(TAG, "你選擇了不好");
                    mTextView.setText("你選擇了不好");

                    mRobotAPI.robot.setExpression(RobotFace.INNOCENT);
                    text = "太可惜了！那你隨時可以再來找我喔！";
                    CommandSerial_reject = mRobotAPI.robot.speak(text);
                    Log.d(TAG, "check :"+ CommandSerial_reject);
                }
                else if (sSluResultCity.equals("accept")) {
                    Log.d(TAG, "你選擇了好");

                    mRobotAPI.robot.setExpression(RobotFace.HAPPY);
                    text = "太棒了！那我會請你回答三個問題，讓我更了解你，我們開始吧！";
                    CommandSerial_accept = mRobotAPI.robot.speak(text);
                    Log.d(TAG, "check :"+ CommandSerial_accept);
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
