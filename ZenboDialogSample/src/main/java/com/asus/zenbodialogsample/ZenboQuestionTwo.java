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

public class ZenboQuestionTwo extends RobotActivity {
    public final static String TAG = "ZenboQuestionTwo";
    public final static String DOMAIN = "2C17093E978140CAB8898BD4BDAB9CF5";

    private static TextView mTextView;
    private static Button bt_A, bt_B, bt_C, bt_D, bt_E, bt_F, bt_leave;

    private static RobotAPI mRobotAPI;
    private static Intent mIntent, mGetIntent;
    private static Context mContext;
    private static int  CommandSerial_A, CommandSerial_B, CommandSerial_C, CommandSerial_D, CommandSerial_E, CommandSerial_F;

    private static Bundle mBundle;
    private static String ans_1 = "5,000以內", ans_2 = "5,000 ~ 10,000", ans_3 = "10,000 ~ 30,000",
                          ans_4 = "30,001 ~ 50,000", ans_5 = "50,001 ~ 80,000", ans_6 = "80,001以上";

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

                String text = "好的，跟我一樣是小資族呢";
                CommandSerial_A = mRobotAPI.robot.speak(text);
                Log.d(TAG, "check :"+ CommandSerial_A);
            }
        });

        bt_B.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                robotAPI.robot.stopSpeakAndListen();

                String text = "好的，跟我一樣是小資族呢";
                CommandSerial_B = mRobotAPI.robot.speak(text);
                Log.d(TAG, "check :"+ CommandSerial_B);
            }
        });

        bt_C.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                robotAPI.robot.stopSpeakAndListen();

                String text = "好的，有點厲害耶，好羨慕你";
                CommandSerial_C= mRobotAPI.robot.speak(text);
                Log.d(TAG, "check :"+ CommandSerial_C);
            }
        });

        bt_D.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                robotAPI.robot.stopSpeakAndListen();

                String text = "好的，有點厲害耶，好羨慕你";
                CommandSerial_D = mRobotAPI.robot.speak(text);
                Log.d(TAG, "check :"+ CommandSerial_D);
            }
        });

        bt_E.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                robotAPI.robot.stopSpeakAndListen();

                String text = "好的，你財力好雄厚、人生勝利組耶";
                CommandSerial_E = mRobotAPI.robot.speak(text);
                Log.d(TAG, "check :"+ CommandSerial_E);
            }
        });

        bt_F.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                robotAPI.robot.stopSpeakAndListen();

                String text = "好的，你財力好雄厚、人生勝利組耶";
                CommandSerial_F = mRobotAPI.robot.speak(text);
                Log.d(TAG, "check :"+ CommandSerial_F);
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
            if (serial == CommandSerial_A && state != RobotCmdState.ACTIVE){
                Log.d(TAG, "command: "+ "CommandSerial" + " SUCCEED");
                mIntent.setClass(mContext, ZenboQuestionThree.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mBundle.putString("Q2_ans", ans_1);
                mBundle.putString("Q1_ans",mGetIntent.getExtras().getString("Q1_ans", "0"));
                mIntent.putExtras(mBundle);
                mContext.startActivity(mIntent);
            } else if (serial == CommandSerial_B && state != RobotCmdState.ACTIVE){
                Log.d(TAG, "command: "+ "CommandSerial" + " SUCCEED");
                mIntent.setClass(mContext, ZenboQuestionThree.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mBundle.putString("Q2_ans", ans_2);
                mBundle.putString("Q1_ans",mGetIntent.getExtras().getString("Q1_ans", "0"));
                mIntent.putExtras(mBundle);
                mContext.startActivity(mIntent);
            } else if (serial == CommandSerial_C && state != RobotCmdState.ACTIVE){
                Log.d(TAG, "command: "+ "CommandSerial" + " SUCCEED");
                mIntent.setClass(mContext, ZenboQuestionThree.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mBundle.putString("Q2_ans", ans_3);
                mBundle.putString("Q1_ans",mGetIntent.getExtras().getString("Q1_ans", "0"));
                mIntent.putExtras(mBundle);
                mContext.startActivity(mIntent);
            } else if (serial == CommandSerial_D && state != RobotCmdState.ACTIVE){
                Log.d(TAG, "command: "+ "CommandSerial" + " SUCCEED");
                mIntent.setClass(mContext, ZenboQuestionThree.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mBundle.putString("Q2_ans", ans_4);
                mBundle.putString("Q1_ans",mGetIntent.getExtras().getString("Q1_ans", "0"));
                mIntent.putExtras(mBundle);
                mContext.startActivity(mIntent);
            } else if (serial == CommandSerial_E && state != RobotCmdState.ACTIVE){
                Log.d(TAG, "command: "+ "CommandSerial" + " SUCCEED");
                mIntent.setClass(mContext, ZenboQuestionThree.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mBundle.putString("Q2_ans", ans_5);
                mBundle.putString("Q1_ans",mGetIntent.getExtras().getString("Q1_ans", "0"));
                mIntent.putExtras(mBundle);
                mContext.startActivity(mIntent);
            } else if (serial == CommandSerial_F && state != RobotCmdState.ACTIVE){
                Log.d(TAG, "command: "+ "CommandSerial" + " SUCCEED");
                mIntent.setClass(mContext, ZenboQuestionThree.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mBundle.putString("Q2_ans", ans_6);
                mBundle.putString("Q1_ans",mGetIntent.getExtras().getString("Q1_ans", "0"));
                mIntent.putExtras(mBundle);
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

                String sSluResultCity = RobotUtil.queryListenResultJson(jsonObject, "money", null);
                Log.d(TAG, "Response =" + sSluResultCity);

                if(sSluResultCity.equals("a5000")) {
                    Log.d(TAG, "A");

                    text = "好的，跟我一樣是小資族呢";
                    CommandSerial_A = mRobotAPI.robot.speak(text);
                    Log.d(TAG, "check :"+ CommandSerial_A);
                }
                else if (sSluResultCity.equals("b5001_10000")) {
                    Log.d(TAG, "B");

                    text = "好的，跟我一樣是小資族呢";
                    CommandSerial_B = mRobotAPI.robot.speak(text);
                    Log.d(TAG, "check :"+ CommandSerial_B);
                }
                else if (sSluResultCity.equals("c10001_30000")) {
                    Log.d(TAG, "C");

                    text = "好的，有點厲害耶，好羨慕你";
                    CommandSerial_C = mRobotAPI.robot.speak(text);
                    Log.d(TAG, "check :"+ CommandSerial_C);
                }
                else if (sSluResultCity.equals("d30001_50000")) {
                    Log.d(TAG, "D");

                    text = "好的，有點厲害耶，好羨慕你";
                    CommandSerial_D = mRobotAPI.robot.speak(text);
                    Log.d(TAG, "check :"+ CommandSerial_D);
                }
                else if (sSluResultCity.equals("e50001_80000")) {
                    Log.d(TAG, "E");

                    text = "好的，你財力好雄厚、人生勝利組耶";
                    CommandSerial_E = mRobotAPI.robot.speak(text);
                    Log.d(TAG, "check :"+ CommandSerial_E);

                }
                else if (sSluResultCity.equals("f80001")) {
                    Log.d(TAG, "F");

                    text = "好的，你財力好雄厚、人生勝利組耶";
                    CommandSerial_F = mRobotAPI.robot.speak(text);
                    Log.d(TAG, "check :"+ CommandSerial_F);

                }
                else{
                    Log.d(TAG, "failed QQ");
                }
            }

        }

        @Override
        public void onRetry(JSONObject jsonObject) {}
    };





    public ZenboQuestionTwo() {
        super(robotCallback, robotListenCallback);
    }
}
