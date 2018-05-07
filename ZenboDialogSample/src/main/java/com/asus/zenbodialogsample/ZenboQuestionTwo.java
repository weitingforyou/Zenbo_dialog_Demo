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

public class ZenboQuestionTwo extends RobotActivity {
    public final static String TAG = "ZenboQuestionTwo";
    public final static String DOMAIN = "2C17093E978140CAB8898BD4BDAB9CF5";

    private static TextView mTextView;
    private static Button bt_A, bt_B, bt_C, bt_D, bt_E, bt_F, bt_leave;

    private static RobotAPI mRobotAPI;
    private static Intent mIntent;
    private static Context mContext;
    private static int CommandSerial_AB, CommandSerial_CD, CommandSerial_EF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_zenbo_question);

        mTextView = (TextView) findViewById(R.id.textView_question);
        bt_A = (Button)findViewById(R.id.buttonA);
        bt_B = (Button)findViewById(R.id.buttonB);
        bt_C = (Button)findViewById(R.id.buttonC);
        bt_D = (Button)findViewById(R.id.buttonD);
        bt_E = (Button)findViewById(R.id.buttonE);
        bt_F = (Button)findViewById(R.id.buttonF);
        bt_leave = (Button)findViewById(R.id.button_leave);

        bt_A.setText("5,000以內");
        bt_A.setText("5,000 ~ 10,000");
        bt_A.setText("10,000 ~ 30,000");
        bt_A.setText("30,001 ~ 50,000");
        bt_A.setText("50,001 ~ 80,000");
        bt_A.setText("80,001以上");

        mRobotAPI = robotAPI;
        mIntent = new Intent();
        mContext = this.getApplicationContext();

        bt_A.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                robotAPI.robot.stopSpeakAndListen();

                String text = "好的，跟我一樣是小資族呢";
                CommandSerial_AB = mRobotAPI.robot.speak(text);
                Log.d(TAG, "check :"+ CommandSerial_AB);
            }
        });

        bt_B.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                robotAPI.robot.stopSpeakAndListen();

                String text = "好的，跟我一樣是小資族呢";
                CommandSerial_AB = mRobotAPI.robot.speak(text);
                Log.d(TAG, "check :"+ CommandSerial_AB);
            }
        });

        bt_C.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                robotAPI.robot.stopSpeakAndListen();

                String text = "好的，有點厲害耶，好羨慕你";
                CommandSerial_CD = mRobotAPI.robot.speak(text);
                Log.d(TAG, "check :"+ CommandSerial_CD);
            }
        });

        bt_D.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                robotAPI.robot.stopSpeakAndListen();

                String text = "好的，有點厲害耶，好羨慕你";
                CommandSerial_CD = mRobotAPI.robot.speak(text);
                Log.d(TAG, "check :"+ CommandSerial_CD);
            }
        });

        bt_E.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                robotAPI.robot.stopSpeakAndListen();

                String text = "好的，你財力好雄厚、人生勝利組耶";
                CommandSerial_EF = mRobotAPI.robot.speak(text);
                Log.d(TAG, "check :"+ CommandSerial_EF);
            }
        });

        bt_F.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                robotAPI.robot.stopSpeakAndListen();

                String text = "好的，你財力好雄厚、人生勝利組耶";
                CommandSerial_EF = mRobotAPI.robot.speak(text);
                Log.d(TAG, "check :"+ CommandSerial_EF);
            }
        });

        bt_leave.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                robotAPI.robot.stopSpeakAndListen();
                //[to do] leave app
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // set beginning expression : default
        robotAPI.robot.setExpression(RobotFace.HIDEFACE);

        // jump dialog domain
        robotAPI.robot.jumpToPlan(DOMAIN, "Q2");

        // listen user utterance
        robotAPI.robot.speakAndListen("每個月想要投資多少錢？", new SpeakConfig().timeout(20));

        // show hint
        mTextView.setText("每個月想要投資多少錢？");

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
            if ((serial == CommandSerial_AB || serial == CommandSerial_CD || serial == CommandSerial_EF) && state != RobotCmdState.ACTIVE){
                Log.d(TAG, "command: "+ "CommandSerial choice" + " SUCCEED");
                mIntent.setClass(mContext, ZenboQuestionThree.class);
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

            if(sIntentionID.equals("Q2")) {

                String sSluResultCity = RobotUtil.queryListenResultJson(jsonObject, "choicemoney", null);
                Log.d(TAG, "Response =" + sSluResultCity);

                if(sSluResultCity.equals("a5000")) {
                    Log.d(TAG, "A");

                    text = "好的，跟我一樣是小資族呢";
                    CommandSerial_AB = mRobotAPI.robot.speak(text);
                    Log.d(TAG, "check :"+ CommandSerial_AB);
                }
                else if (sSluResultCity.equals("b5001_10000")) {
                    Log.d(TAG, "B");

                    text = "好的，跟我一樣是小資族呢";
                    CommandSerial_AB = mRobotAPI.robot.speak(text);
                    Log.d(TAG, "check :"+ CommandSerial_AB);
                }
                else if (sSluResultCity.equals("c10001_30000")) {
                    Log.d(TAG, "C");

                    text = "好的，有點厲害耶，好羨慕你";
                    CommandSerial_CD = mRobotAPI.robot.speak(text);
                    Log.d(TAG, "check :"+ CommandSerial_CD);
                }
                else if (sSluResultCity.equals("d30001_50000")) {
                    Log.d(TAG, "D");

                    text = "好的，有點厲害耶，好羨慕你";
                    CommandSerial_CD = mRobotAPI.robot.speak(text);
                    Log.d(TAG, "check :"+ CommandSerial_CD);
                }
                else if (sSluResultCity.equals("e50001_80000")) {
                    Log.d(TAG, "E");

                    text = "好的，你財力好雄厚、人生勝利組耶";
                    CommandSerial_EF = mRobotAPI.robot.speak(text);
                    Log.d(TAG, "check :"+ CommandSerial_EF);

                }
                else if (sSluResultCity.equals("f80001")) {
                    Log.d(TAG, "F");

                    text = "好的，你財力好雄厚、人生勝利組耶";
                    CommandSerial_EF = mRobotAPI.robot.speak(text);
                    Log.d(TAG, "check :"+ CommandSerial_EF);

                }
                else{
                    Log.d(TAG, "failed QQ");
                }

                mIntent.setClass(mContext, ZenboQuestionThree.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(mIntent);
            }

        }

        @Override
        public void onRetry(JSONObject jsonObject) {}
    };





    public ZenboQuestionTwo() {
        super(robotCallback, robotListenCallback);
    }
}
