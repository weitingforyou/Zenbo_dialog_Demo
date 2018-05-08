package com.asus.zenbodialogsample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
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

    private static TextView mTextView;
    private static Button bt_accept, bt_reject, bt_leave;

    private static RobotAPI mRobotAPI;
    private static Intent mIntent;
    private static Context mContext;
    private static int CommandSerial_accept, CommandSerial_reject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_zenbo_makesure);

        //mTextView = (TextView) findViewById(R.id.textView_question);
        bt_accept = (Button)findViewById(R.id.button_accept);
        bt_reject = (Button)findViewById(R.id.button_reject);
        bt_leave = (Button)findViewById(R.id.button_leave);

        mRobotAPI = robotAPI;
        mIntent = new Intent();
        mContext = this.getApplicationContext();

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
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // set beginning expression : default
        robotAPI.robot.setExpression(RobotFace.HIDEFACE);

        // jump dialog domain
        //robotAPI.robot.jumpToPlan(DOMAIN, "beforestart");

        // listen user utterance
        robotAPI.robot.speakAndListen("好的，能不能幫我確認剛才三題的回答是不是正確呢？", new SpeakConfig().timeout(20));

        // show hint
        //mTextView.setText("我想為你提出一些理財建議，你願意嗎？");

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
            if (serial == CommandSerial_accept && state != RobotCmdState.ACTIVE){
                Log.d(TAG, "command: "+ CommandSerial_accept + " SUCCEED");
                mIntent.setClass(mContext, ZenboResult.class);
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
        public void onEventUserUtterance(JSONObject jsonObject) { }

        @Override
        public void onResult(JSONObject jsonObject) { }

        @Override
        public void onRetry(JSONObject jsonObject) {}
    };

    public ZenboMakeSure() {
        super(robotCallback, robotListenCallback);
    }
}
