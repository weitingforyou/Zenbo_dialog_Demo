package com.asus.zenbodialogsample;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.asus.robotframework.API.RobotCallback;
import com.asus.robotframework.API.RobotCmdState;
import com.asus.robotframework.API.RobotErrorCode;
import com.asus.robotframework.API.RobotFace;
import com.asus.robotframework.API.RobotUtil;
import com.asus.robotframework.API.SpeakConfig;
import com.robot.asus.robotactivity.RobotActivity;

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
        robotAPI.robot.speakAndListen("Which city do you like?", new SpeakConfig().timeout(20));

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
            mTextView.setText(text);
            Log.d(TAG, text);
        }

        @Override
        public void onResult(JSONObject jsonObject) {
            String text;
            text = "onResult: " + jsonObject.toString();
            Log.d(TAG, text);

            String sIntentionID = RobotUtil.queryListenResultJson(jsonObject, "IntentionId");
            //mTextView.setText(sIntentionID);
            Log.d(TAG, "Intention Id = " + sIntentionID);

            if(sIntentionID.equals("ThisPlanLaunchingThisApp")){
                robotAPI.robot.speak("你好，我是理財助理Juicy！請站在我的前方並看著我的眼睛，讓我認識你。");
            }

            if(sIntentionID.equals("ProvideService")) {
                String sSluResultCity = RobotUtil.queryListenResultJson(jsonObject, "AcceptReject", null);
                //mTextView.setText("Result: " + sSluResultCity);
                Log.d(TAG, "Result City = " + sSluResultCity);

                if(sSluResultCity!= null) {
                    mTextView.setText("You are now at " + sSluResultCity);
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

