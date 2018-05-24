package com.asus.zenbodialogsample;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

public class ZenboMakeSure extends RobotActivity {
    public final static String TAG = "ZenboMakeSure";
    public final static String DOMAIN = "2C17093E978140CAB8898BD4BDAB9CF5";

    private static TextView mAns_1, mAns_2, mAns_3;
    private static ImageButton bt_accept, bt_reject, bt_leave;

    private static RobotAPI mRobotAPI;
    private static Intent mIntent, mGetIntent;
    private static Context mContext;
    private static Activity mActivity;
    private static int CommandSerial_accept;

    private static Bundle mBundle;

    private String ans1, ans2, ans3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_zenbo_makesure);

        //mTextView = (TextView) findViewById(R.id.textView_question);
        bt_accept = (ImageButton)findViewById(R.id.imageButton_accept);
        bt_reject = (ImageButton)findViewById(R.id.imageButton_reject);
        bt_leave = (ImageButton)findViewById(R.id.imageButton_leave);
        mAns_1 = (TextView)findViewById(R.id.textView_ans1);
        mAns_2 = (TextView)findViewById(R.id.textView_ans2);
        mAns_3 = (TextView)findViewById(R.id.textView_ans3);


        mRobotAPI = robotAPI;
        mIntent = new Intent();
        mContext = this.getApplicationContext();
        mBundle = new Bundle();
        mActivity = new Activity();

        mGetIntent = getIntent();

        ans1 = mGetIntent.getExtras().getString("Q1_ans", "0");
        ans2 = mGetIntent.getExtras().getString("Q2_ans", "0");
        ans3 = mGetIntent.getExtras().getString("Q3_ans", "0");


        mAns_1.setText(ans1);
        mAns_2.setText(ans2);
        mAns_3.setText(ans3);

        bt_accept.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                robotAPI.robot.stopSpeakAndListen();

                mRobotAPI.robot.setExpression(RobotFace.EXPECTING);
                String text = "太好了！謝謝你回答完我的問題！";
                CommandSerial_accept = mRobotAPI.robot.speak(text);
                Log.d(TAG, "check :"+ CommandSerial_accept);
            }
        });

        bt_reject.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                robotAPI.robot.stopSpeakAndListen();

                mIntent.setClass(mContext, ZenboQuestionOne.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(mIntent);
                mActivity.finish();
            }
        });

        bt_leave.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                robotAPI.robot.stopSpeakAndListen();

                mIntent.setClass(mContext, ZenboStartService.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(mIntent);
                mActivity.finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        robotAPI.robot.setExpression(RobotFace.HIDEFACE);
        robotAPI.robot.jumpToPlan(DOMAIN, "checkquestion");
        robotAPI.robot.speakAndListen("好的，能不能幫我確認剛才三題的回答是不是正確呢？", new SpeakConfig().timeout(20));
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
            if (serial == CommandSerial_accept && state != RobotCmdState.ACTIVE){
                mIntent.setClass(mContext, ZenboThinking.class);
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
            String sIntentionID = RobotUtil.queryListenResultJson(jsonObject, "IntentionId");
            Log.d(TAG, "Intention Id = " + sIntentionID);

            if(sIntentionID.equals("checkquestion")) {

                String sSluResultCity = RobotUtil.queryListenResultJson(jsonObject, "check", null);
                Log.d(TAG, "Response =" + sSluResultCity);

                if (sSluResultCity.equals("yes")) {
                    mRobotAPI.robot.setExpression(RobotFace.EXPECTING);
                    String text = "太好了！謝謝你回答完我的問題！";
                    CommandSerial_accept = mRobotAPI.robot.speak(text);
                    Log.d(TAG, "check :" + CommandSerial_accept);
                } else if (sSluResultCity.equals("no")) {
                    mIntent.setClass(mContext, ZenboQuestionOne.class);
                    mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(mIntent);
                    mActivity.finish();
                }
            }

        }


        @Override
        public void onRetry(JSONObject jsonObject) {}
    };

    public ZenboMakeSure() {
        super(robotCallback, robotListenCallback);
    }
}
