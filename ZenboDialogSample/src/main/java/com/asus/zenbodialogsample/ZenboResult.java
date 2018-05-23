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

public class ZenboResult extends RobotActivity {
    public final static String TAG = "ZenboMakeSure";
    public final static String DOMAIN = "2C17093E978140CAB8898BD4BDAB9CF5";

    private static TextView mResult, mSlogan, mRecommand;
    private static ImageView mChart;
    private static ImageButton bt_accept, bt_again;

    private static RobotAPI mRobotAPI;
    private static Intent mIntent, mGetIntent;
    private static Context mContext;
    private static Activity mActivity;
    private static Bundle mBundle;
    private static int CommandSerial_again;
    private int score, q1_score, q3_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_zenbo_result);

        mResult = (TextView)findViewById(R.id.textView_result);
        mSlogan = (TextView)findViewById(R.id.textView_slogan);
        mRecommand = (TextView)findViewById(R.id.textView_recommand);
        mChart = (ImageView)findViewById(R.id.imageView_chart);

        mIntent = new Intent();
        mGetIntent = getIntent();

        q1_score = mGetIntent.getExtras().getInt("Q1_score", 0);
        q3_score = mGetIntent.getExtras().getInt("Q3_score", 0);

        score = q1_score * q3_score;

        bt_accept = (ImageButton)findViewById(R.id.button_accept);
        bt_again = (ImageButton)findViewById(R.id.imageButton_again);

        //mRobotAPI = robotAPI;
        mIntent = new Intent();
        mContext = this.getApplicationContext();
        mActivity = new Activity();
        mBundle = new Bundle();

        if(score<20)
        {
            mResult.setText("你適合保守型的投資");
            mSlogan.setText("一步一腳印、追求踏實");
            mRecommand.setText("投資建議 - \n" + "債券39%、股票19％、\n" + "外幣32%、現金10%");
            mChart.setImageDrawable(getResources().getDrawable(R.drawable.result_pie_chart_conservative));
            mRobotAPI.robot.speak("你是一位一步一腳印、追求踏實的人，我想你的投資適合保守一些，才能襯托你的沈穩。這份圖表是你的理財分配建議。");
        }
        else if (score>=20 && score<=40)
        {
            mResult.setText("你適合穩健型的投資");
            mSlogan.setText("理性、凡事安排妥當");
            mRecommand.setText("投資建議 - \n" + "債券45%、股票48％、\n" + "外幣5%、現金2%");
            mChart.setImageDrawable(getResources().getDrawable(R.drawable.result_pie_chart_steady));
            mRobotAPI.robot.speak("你是一位理性、什麼事都安排得很好的人，你的投資可以穩健中帶一點刺激。這份圖表是你的理財分配建議。");
        }
        else
        {
            mResult.setText("你適合積極型的投資");
            mSlogan.setText("行事積極、極具膽識");
            mRecommand.setText("投資建議 - \n" + "債券26%、股票60％、\n" + "外幣12%、現金2%");
            mChart.setImageDrawable(getResources().getDrawable(R.drawable.result_pie_chart_positive));
            mRobotAPI.robot.speak("你是一位很積極、很有膽識的人，你的投資可以大膽積極一些，可能會有更高的報酬。這份圖表是你的理財分配建議。");
        }

        bt_accept.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                robotAPI.robot.setExpression(RobotFace.PLEASED);
                robotAPI.robot.speak("謝謝你！歡迎再來找我玩喔！");
            }
        });

        bt_again.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                robotAPI.robot.setExpression(RobotFace.ACTIVE);
                String text = "謝謝你！歡迎再來找我玩喔！";
                CommandSerial_again = mRobotAPI.robot.speak(text);
                Log.d(TAG, "check :" + CommandSerial_again);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        // set beginning expression : default
        //robotAPI.robot.setExpression(RobotFace.CONFIDENT);
        //robotAPI.robot.speak("現在我來想想最適合你的理財方式喔！");

        // jump dialog domain
        //robotAPI.robot.jumpToPlan(DOMAIN, "beforestart");

        // listen user utterance
        //robotAPI.robot.speakAndListen("我想為你提出一些理財建議，你願意嗎？", new SpeakConfig().timeout(20));

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
            if (serial == CommandSerial_again && state != RobotCmdState.ACTIVE){
                mIntent.setClass(mContext, ZenboQuestionOne.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
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
        public void onResult(JSONObject jsonObject) { }

        @Override
        public void onRetry(JSONObject jsonObject) {}
    };

    public ZenboResult() {
        super(robotCallback, robotListenCallback);
    }
}
