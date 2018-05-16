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

public class ZenboQuestionOne extends RobotActivity {
    public final static String TAG = "ZenboQuestionOne";
    public final static String DOMAIN = "2C17093E978140CAB8898BD4BDAB9CF5";

    private static TextView mTextView;
    private static ImageButton bt_A, bt_B, bt_C, bt_D, bt_E, bt_F, bt_leave;
    private static TextView tv_A, tv_B, tv_C, tv_D, tv_E, tv_F;

    private static RobotAPI mRobotAPI;
    private static Intent mIntent;
    private static Context mContext;
    private static Bundle mBundle;

    private static String ans_1 = "1到2週一次", ans_2 = "每月一次", ans_3 = "每季一次",
                          ans_4 = "半年一次", ans_5 = "一年一次", ans_6 = "一年以上一次";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_zenbo_question);

        mTextView = (TextView) findViewById(R.id.textView_question);
        bt_A = (ImageButton)findViewById(R.id.buttonA);
        bt_B = (ImageButton)findViewById(R.id.buttonB);
        bt_C = (ImageButton)findViewById(R.id.buttonC);
        bt_D = (ImageButton)findViewById(R.id.buttonD);
        bt_E = (ImageButton)findViewById(R.id.buttonE);
        bt_F = (ImageButton)findViewById(R.id.buttonF);
        bt_leave = (ImageButton)findViewById(R.id.imageButton_leave);

        tv_A = (TextView)findViewById(R.id.textView_A);
        tv_B = (TextView)findViewById(R.id.textView_B);
        tv_C = (TextView)findViewById(R.id.textView_C);
        tv_D = (TextView)findViewById(R.id.textView_D);
        tv_E = (TextView)findViewById(R.id.textView_E);
        tv_F = (TextView)findViewById(R.id.textView_F);

        tv_A.setText(ans_1);
        tv_B.setText(ans_2);
        tv_C.setText(ans_3);
        tv_D.setText(ans_4);
        tv_E.setText(ans_5);
        tv_F.setText(ans_6);

        mRobotAPI = robotAPI;
        mIntent = new Intent();
        mContext = this.getApplicationContext();
        mBundle = new Bundle();

        bt_A.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                robotAPI.robot.stopSpeakAndListen();

                mIntent.setClass(mContext, ZenboQuestionTwo.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mBundle.putString("Q1_ans", ans_1);
                mIntent.putExtras(mBundle);
                mContext.startActivity(mIntent);
            }
        });

        bt_B.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                robotAPI.robot.stopSpeakAndListen();

                mIntent.setClass(mContext, ZenboQuestionTwo.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mBundle.putString("Q1_ans", ans_2);
                mIntent.putExtras(mBundle);
                mContext.startActivity(mIntent);
            }
        });

        bt_C.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                robotAPI.robot.stopSpeakAndListen();

                mIntent.setClass(mContext, ZenboQuestionTwo.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mBundle.putString("Q1_ans", ans_3);
                mIntent.putExtras(mBundle);
                mContext.startActivity(mIntent);
            }
        });

        bt_D.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                robotAPI.robot.stopSpeakAndListen();

                mIntent.setClass(mContext, ZenboQuestionTwo.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mBundle.putString("Q1_ans", ans_4);
                mIntent.putExtras(mBundle);
                mContext.startActivity(mIntent);
            }
        });

        bt_E.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                robotAPI.robot.stopSpeakAndListen();

                mIntent.setClass(mContext, ZenboQuestionTwo.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mBundle.putString("Q1_ans", ans_5);
                mIntent.putExtras(mBundle);
                mContext.startActivity(mIntent);
            }
        });

        bt_F.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                robotAPI.robot.stopSpeakAndListen();

                mIntent.setClass(mContext, ZenboQuestionTwo.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mBundle.putString("Q1_ans", ans_6);
                mIntent.putExtras(mBundle);
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
        robotAPI.robot.jumpToPlan(DOMAIN, "Q1");

        // listen user utterance
        robotAPI.robot.speakAndListen("你覺得你大約多久會做一次投資交易？", new SpeakConfig().timeout(20));

        // show hint
        mTextView.setText("你覺得你大約多久會做一次投資交易？");

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

            if(sIntentionID.equals("Q1")) {

                String sSluResultCity = RobotUtil.queryListenResultJson(jsonObject, "time", null);
                Log.d(TAG, "Response =" + sSluResultCity);

                String ans = "";
                if(sSluResultCity.equals("week")) {
                    Log.d(TAG, "A");
                    ans = ans_1;
                }
                else if (sSluResultCity.equals("month")) {
                    Log.d(TAG, "B");
                    ans = ans_2;
                }
                else if (sSluResultCity.equals("Each_quarter")) {
                    Log.d(TAG, "C");
                    ans = ans_3;
                }
                else if (sSluResultCity.equals("Six_months")) {
                    Log.d(TAG, "D");
                    ans = ans_4;
                }
                else if (sSluResultCity.equals("year")) {
                    Log.d(TAG, "E");
                    ans = ans_5;
                }
                else if (sSluResultCity.equals("more_than_a_year")) {
                    Log.d(TAG, "F");
                    ans = ans_6;
                }
                else{
                    Log.d(TAG, "failed QQ");
                }

                mIntent.setClass(mContext, ZenboQuestionTwo.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mBundle.putString("Q1_ans", ans);
                mIntent.putExtras(mBundle);
                mContext.startActivity(mIntent);
            }

        }

        @Override
        public void onRetry(JSONObject jsonObject) {}
    };





    public ZenboQuestionOne() {
        super(robotCallback, robotListenCallback);
    }
}
