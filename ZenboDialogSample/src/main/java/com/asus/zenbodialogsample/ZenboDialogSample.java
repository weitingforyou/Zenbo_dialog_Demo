package com.asus.zenbodialogsample;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.asus.robotframework.API.DialogSystem;
import com.asus.robotframework.API.RobotAPI;
import com.asus.robotframework.API.RobotCallback;
import com.asus.robotframework.API.RobotCmdState;
import com.asus.robotframework.API.RobotErrorCode;
import com.asus.robotframework.API.RobotFace;
import com.asus.robotframework.API.RobotUtil;
import com.asus.robotframework.API.SpeakConfig;
import com.robot.asus.robotactivity.RobotActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class ZenboDialogSample extends RobotActivity {
    public final static String TAG = "ZenboDialogSample";
    public final static String DOMAIN = "DD1E4C84279C4598BD71DE6DFD0BA6BB";

    private static TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zenbo_dialog_sample);

        mTextView = (TextView) findViewById(R.id.textview_info);
        mTextView.setText(DOMAIN);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // set beginning expression : default
        robotAPI.robot.setExpression(RobotFace.HIDEFACE);

        // jump dialog domain
        robotAPI.robot.jumpToPlan(DOMAIN, "ThisPlanLaunchingThisApp");

        // listen user utterance
        robotAPI.robot.speakAndListen("你好，我是理財助理Juicy！請站在我的前方並看著我的眼睛，讓我認識你。", new SpeakConfig().timeout(20));

        // show hint
        //mTextView.setText(getResources().getString(R.string.dialog_example));

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
            //mTextView.setText(text);
            Log.d(TAG, text);
        }

        @Override
        public void onResult(JSONObject jsonObject) {
            String text;
            text = "onResult: " + jsonObject.toString();
            Log.d(TAG, text);


            String sIntentionID = RobotUtil.queryListenResultJson(jsonObject, "IntentionId");
            Log.d(TAG, "Intention Id = " + sIntentionID);

            if(sIntentionID.equals("ThisPlanLaunchingThisApp")){
                text = "你好，我是理財助理Zenbo！請站在我的前方並看著我的眼睛，讓我認識你。";
                mTextView.setText(text);
                new RobotAPI(new ZenboDialogSample()).robot.speak(text);
                //new Intent().setClass(new ZenboDialogSample(), changePage.class);
            }

            if(sIntentionID.equals("ProvideService")) {

                String sSluResultCity = RobotUtil.queryListenResultJson(jsonObject, "ans_AcceptReject", null);
                Log.d(TAG, "Result City =" + sSluResultCity + "end");

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
        public void onRetry(JSONObject jsonObject) {

        }
    };



    public ZenboDialogSample() {
        super(robotCallback, robotListenCallback);
    }


}

