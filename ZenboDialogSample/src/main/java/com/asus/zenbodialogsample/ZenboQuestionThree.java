package com.asus.zenbodialogsample;

import android.app.Activity;
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

public class ZenboQuestionThree extends RobotActivity {
    public final static String TAG = "ZenboQuestionThree";
    public final static String DOMAIN = "2C17093E978140CAB8898BD4BDAB9CF5";

    private static TextView mTextView;
    private static Button bt_A, bt_B, bt_C, bt_D, bt_E, bt_F, bt_leave;

    private static RobotAPI mRobotAPI;
    private static Intent mIntent, mGetIntent;
    private static Context mContext;

    private static Bundle mBundle;
    private static String ans_1 = "1% ~ 2%", ans_2 = "2% ~ 4%", ans_3 = "4% ~ 6%",
                          ans_4 = "6% ~ 8%", ans_5 = "8% ~ 10%", ans_6 = "10%以上";

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

        bt_A.setText(ans_1);
        bt_B.setText(ans_2);
        bt_C.setText(ans_3);
        bt_D.setText(ans_4);
        bt_E.setText(ans_5);
        bt_F.setText(ans_6);

        mRobotAPI = robotAPI;
        mIntent = new Intent();
        mContext = this.getApplicationContext();
        mBundle = new Bundle();
        mGetIntent = getIntent();

        bt_A.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                robotAPI.robot.stopSpeakAndListen();

                mIntent.setClass(mContext, ZenboMakeSure.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mBundle.putString("Q3_ans", ans_1);
                mBundle.putString("Q1_ans",mGetIntent.getExtras().getString("Q1_ans", "0"));
                mBundle.putString("Q2_ans",mGetIntent.getExtras().getString("Q2_ans", "0"));
                mIntent.putExtras(mBundle);
                mContext.startActivity(mIntent);
            }
        });

        bt_B.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                robotAPI.robot.stopSpeakAndListen();

                mIntent.setClass(mContext, ZenboMakeSure.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mBundle.putString("Q3_ans", ans_2);
                mBundle.putString("Q1_ans",mGetIntent.getExtras().getString("Q1_ans", "0"));
                mBundle.putString("Q2_ans",mGetIntent.getExtras().getString("Q2_ans", "0"));
                mIntent.putExtras(mBundle);
                mContext.startActivity(mIntent);
            }
        });

        bt_C.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                robotAPI.robot.stopSpeakAndListen();

                mIntent.setClass(mContext, ZenboMakeSure.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mBundle.putString("Q3_ans", ans_3);
                mBundle.putString("Q1_ans",mGetIntent.getExtras().getString("Q1_ans", "0"));
                mBundle.putString("Q2_ans",mGetIntent.getExtras().getString("Q2_ans", "0"));
                mIntent.putExtras(mBundle);
                mContext.startActivity(mIntent);
            }
        });

        bt_D.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                robotAPI.robot.stopSpeakAndListen();

                mIntent.setClass(mContext, ZenboMakeSure.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mBundle.putString("Q3_ans", ans_4);
                mBundle.putString("Q1_ans",mGetIntent.getExtras().getString("Q1_ans", "0"));
                mBundle.putString("Q2_ans",mGetIntent.getExtras().getString("Q2_ans", "0"));
                mIntent.putExtras(mBundle);
                mContext.startActivity(mIntent);
            }
        });

        bt_E.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                robotAPI.robot.stopSpeakAndListen();

                mIntent.setClass(mContext, ZenboMakeSure.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mBundle.putString("Q3_ans", ans_5);
                mBundle.putString("Q1_ans",mGetIntent.getExtras().getString("Q1_ans", "0"));
                mBundle.putString("Q2_ans",mGetIntent.getExtras().getString("Q2_ans", "0"));
                mIntent.putExtras(mBundle);
                mContext.startActivity(mIntent);
            }
        });

        bt_F.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                robotAPI.robot.stopSpeakAndListen();

                mIntent.setClass(mContext, ZenboMakeSure.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mBundle.putString("Q3_ans", ans_6);
                mBundle.putString("Q1_ans",mGetIntent.getExtras().getString("Q1_ans", "0"));
                mBundle.putString("Q2_ans",mGetIntent.getExtras().getString("Q2_ans", "0"));
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
        robotAPI.robot.jumpToPlan(DOMAIN, "Q3");

        // listen user utterance
        robotAPI.robot.speakAndListen("你覺得你大約虧損多少時會想要停止投資？ ", new SpeakConfig().timeout(20));

        // show hint
        mTextView.setText("你覺得你大約虧損多少時會想要停止投資？ ");

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

            if(sIntentionID.equals("Q3")) {

                String sSluResultCity = RobotUtil.queryListenResultJson(jsonObject, "stoptime", null);
                Log.d(TAG, "Response =" + sSluResultCity);

                String ans = "";

                if(sSluResultCity.equals("a1")) {
                    Log.d(TAG, "A");
                    ans = ans_1;
                }
                else if (sSluResultCity.equals("b2")) {
                    Log.d(TAG, "B");
                    ans = ans_2;
                }
                else if (sSluResultCity.equals("c4")) {
                    Log.d(TAG, "C");
                    ans = ans_3;
                }
                else if (sSluResultCity.equals("d6")) {
                    Log.d(TAG, "D");
                    ans = ans_4;
                }
                else if (sSluResultCity.equals("e8")) {
                    Log.d(TAG, "E");
                    ans = ans_5;
                }
                else if (sSluResultCity.equals("f10")) {
                    Log.d(TAG, "F");
                    ans = ans_6;
                }
                else{
                    Log.d(TAG, "failed QQ");
                }

                mIntent.setClass(mContext, ZenboMakeSure.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mBundle.putString("Q3_ans", ans);
                mBundle.putString("Q1_ans",mGetIntent.getExtras().getString("Q1_ans", "0"));
                mBundle.putString("Q2_ans",mGetIntent.getExtras().getString("Q2_ans", "0"));
                mIntent.putExtras(mBundle);
                mContext.startActivity(mIntent);
            }

        }

        @Override
        public void onRetry(JSONObject jsonObject) {}
    };





    public ZenboQuestionThree() {
        super(robotCallback, robotListenCallback);
    }
}
